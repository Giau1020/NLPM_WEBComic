    
    //JS cho khiếu nại

    let allComplaints = [];
    async function fetchComplaints() {
        try {
            const response = await fetch('http://localhost:8080/api/complaint/list'); // Đường dẫn đến API
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
           allComplaints = data;
            renderComplaints(data);
        } catch (error) {
            console.error('Lỗi khi lấy dữ liệu:', error);
        }
    }
    
    function renderComplaints(complaints) {
        const complaintTable = document.getElementById('complaint-table');
        complaintTable.innerHTML = ''; // Xóa nội dung trước đó
        
        complaints.forEach(complaint => {
            const formattedTime = complaint.complaintTime.replace('T', ' ').slice(0, 16); 
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${complaint.id}</td>
                <td>${complaint.fullName}</td>
                <td>${formattedTime}</td>
                <td>${complaint.statusComplaint}</td>
                <td><button onclick="viewComplaint('${complaint.id}')">Xem</button>
                <button onclick="adminresponses('${complaint.id}')">Phản hồi</button>
                </td>
            `;
            complaintTable.appendChild(row);
        });
    }
    
    // // Gọi hàm fetchComplaints khi trang được tải
    // fetchComplaints();
    function showAllComplaints() {
        renderComplaints(allComplaints); // Hiển thị lại tất cả các khiếu nại
    }

    function filterPending() {
        const pendingComplaints = allComplaints.filter(complaint => complaint.statusComplaint === 'Chưa xử lý'); // Lọc khiếu nại "Chưa xử lý"
        renderComplaints(pendingComplaints); // Hiển thị các khiếu nại đã lọc
    }
// Khởi động với việc lấy dữ liệu khiếu nại
fetchComplaints();

//button xem chi tiết khiếu nại
function viewComplaint(id) {
    const modal = document.getElementById('complaint-modal');
    modal.style.display = "block";
    
    const modalBody = document.getElementById('modal-body');
    modalBody.innerHTML = 'Đang tải chi tiết khiếu nại...';

    // Gọi API để lấy chi tiết khiếu nại
// Gọi API để lấy chi tiết khiếu nại
fetch(`http://localhost:8080/api/complaint/${id}`)
    .then(response => response.json())
    .then(data => {
        let imageHTML = '';
        if (data.image) {
            // Lấy tệp hình ảnh từ thư mục tĩnh
            imageHTML = `<img src="/images/${data.image}" alt="Hình ảnh khiếu nại" style="max-width: 40%; height: auto;" />`;
        } else {
            imageHTML = '<p>Không có hình ảnh đính kèm.</p>';
        }
        const formattedComplaintTime = data.complaintTime.replace('T', ' ').slice(0, 16);

        modalBody.innerHTML = `
            <p><strong>ID:</strong> ${data.id}</p>
            <p><strong>Mã Đơn hàng:</strong> ${data.orderId}</p>
            <p><strong>Người gửi:</strong> ${data.fullName}</p>
            <p><strong>Ngày gửi:</strong> ${formattedComplaintTime}</p>
            <p><strong>Mô tả khiếu nại:</strong> ${data.descriptionComplaint}</p>
            <p><strong>Hình ảnh đính kèm:</strong></p>
            ${imageHTML}
            <p><strong>Trả Lời khiếu nại:</strong> ${data.response}</p>
            <p><strong>Trạng thái:</strong> ${data.statusComplaint}</p>
        `;
    })
    .catch(error => {
        console.error('Lỗi khi lấy chi tiết khiếu nại:', error);
        modalBody.innerHTML = 'Lỗi khi tải chi tiết khiếu nại.';
    });
}

function closeModalComplaint() {
    const modal = document.getElementById('complaint-modal');
    modal.style.display = "none";
}
function closeModals() {
    const modal = document.getElementById('responseModal');
    modal.style.display = "none";
}

/*hiển thị phải hồi khiếu nại */
// Hàm để hiển thị form phản hồi

function adminresponse() {
    document.getElementById("responseModal").style.display = "block";
}

// lays id cho responseModal
function adminresponses(id) {
    // Mở modal
    const modal = document.getElementById("responseModal");
    modal.style.display = "block";

    // Gọi API để lấy chi tiết khiếu nại
    fetch(`http://localhost:8080/api/complaint/${id}`)
        .then(response => response.json())
        .then(data => {
            // Gán giá trị ID khiếu nại vào input
            document.getElementById('complaintId').value = data.id;
        })
        .catch(error => {
            console.error('Lỗi khi lấy id khiếu nại:', error);
        });
}


// gửi phản hồi

//
//function updateComplaint() {
//    // Lấy giá trị của các trường trong form
//    const status = document.getElementById("status").value;
//    const response = document.getElementById("responseComplaint").value;
//
//    // Lấy giá trị ID từ input ẩn
//    const id = document.getElementById("complaintId").value;
//
//    // Gọi Fetch API để gửi yêu cầu cập nhật
//    fetch(`http://localhost:8080/api/complaint/${id}/status-and-response`, {
//        method: 'PUT',
//        headers: {
//            'Content-Type': 'application/x-www-form-urlencoded'  // Đặt header cho form
//        },
//        body: new URLSearchParams({  // Định dạng body cho phù hợp với application/x-www-form-urlencoded
//            statusComplaint: status,
//            response: responseComplaint
//        })
//    })
//    // .then(response => response.json())
//    .then(data => {
//        console.log(data);
//        alert("Phản hồi đã được cập nhật thành công: ");
//        const modalBody = document.getElementById('modal-body');
//        modalBody.innerHTML = '';  // Đảm bảo modal-body không giữ lại dữ liệu cũ
//
//    // Cập nhật thông tin mới trong modal
//    modalBody.innerHTML = `
//        <p><strong>ID:</strong> ${data.id}</p>
//        <p><strong>Trạng thái:</strong> ${data.statusComplaint}</p>
//        <p><strong>Phản hồi:</strong> ${data.response}</p>`
//    ;
//
//    //fetch
//        fetchComplaints()
//
//        closeModals();  // Đóng modal sau khi thành công
//    })
//    .catch(error => {
//        // Hiển thị lỗi nếu có vấn đề xảy ra
//        alert("Có lỗi xảy ra: " + error.message);
//    });
//
//}
function updateComplaint() {
    // Lấy giá trị của các trường trong form
    const status = document.getElementById("status").value;
    const response = document.getElementById("responses").value;

    // Lấy giá trị ID từ input ẩn
    const id = document.getElementById("complaintId").value;

    // Gọi Fetch API để gửi yêu cầu cập nhật
    fetch(`http://localhost:8080/api/complaint/${id}/status-and-response`, {
     method: 'PUT',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'  // Đặt header cho form
            },
            body: new URLSearchParams({  // Định dạng body cho phù hợp với application/x-www-form-urlencoded
                statusComplaint: status,
                response: response
            })
        })
    .then(data => {
        console.log(data);
        alert("Phản hồi đã được cập nhật thành công: ");
        const modalBody = document.getElementById('modal-body');
        modalBody.innerHTML = '';  // Đảm bảo modal-body không giữ lại dữ liệu cũ

        // Cập nhật thông tin mới trong modal
        modalBody.innerHTML = `
            <p><strong>ID:</strong> ${data.id}</p>
            <p><strong>Trạng thái:</strong> ${data.statusComplaint}</p>
            <p><strong>Phản hồi:</strong> ${data.response}</p>`
        ;

        //fetch
        fetchComplaints()

        closeModals();  // Đóng modal sau khi thành công
    })
    .catch(error => {
        // Hiển thị lỗi nếu có vấn đề xảy ra
        alert("Có lỗi xảy ra: " + error.message);
    });
}
