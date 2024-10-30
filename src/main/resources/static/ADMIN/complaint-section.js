    
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
    fetch(`http://localhost:8080/api/complaint/${id}`)
        .then(response => response.json())
        .then(data => {
            let imageHTML = '';
            if (data.image) {
                // Lấy tệp hình ảnh từ API
                imageHTML = `<img src="/files/${data.image}" alt="Hình ảnh khiếu nại" style="max-width: 40%; height: auto;" />`;
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


function closeModal() {
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


function updateComplaint() {
    // Lấy giá trị của các trường trong form
    const status = document.getElementById("status").value;
    const response = document.getElementById("response").value;

    // Lấy giá trị ID từ input ẩn
    const id = document.getElementById("complaintId").value;

    // Gọi Fetch API để gửi yêu cầu cập nhật
    fetch(`http://localhost:8080/api/complaint/${id}/status-and-response`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'  // Đặt header cho form
            // 'Content-Type': 'application/json' 
        },
        body: new URLSearchParams({  // Định dạng body cho phù hợp với application/x-www-form-urlencoded
            statusComplaint: status,
            response: response
        })
    })
    // .then(response => response.json())
    .then(data => {
        console.log(data);
        alert("Phản hồi đã được cập nhật thành công: ");

        // const rows = document.querySelectorAll('#complaint-table tr');
        // rows.forEach(row => {
        //     const complaintIdCell = row.cells[0]; // Giả sử cột ID là cột đầu tiên
        //     if (complaintIdCell && complaintIdCell.textContent == id) {
        //         const statusCell = row.cells[3]; // Giả sử cột trạng thái là cột thứ 4 (index 3)
        //         if (statusCell) {
        //             statusCell.textContent = status; // Cập nhật trạng thái mới
        //         }
        //     }
        // });

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

// // xử lý khiếu nại
// function processComplaint() {
//     if (selectedComplaintId) {
//         const complaint = complaints.find(c => c.id === selectedComplaintId);
//         if (complaint && complaint.status === 'Chưa xử lý') {
//             complaint.status = 'Đã xử lý';  // Cập nhật trạng thái khiếu nại

//             // Cập nhật giao diện modal
//             document.getElementById('modal-complaint-status').textContent = 'Đã xử lý';
//             document.getElementById('process-complaint-section').style.display = 'none';  // Ẩn nút xử lý

//             // Cập nhật lại bảng
//             renderComplaints(complaints);

//             alert(`Khiếu nại ID: ${complaint.id} đã được xử lý.`);
//         }
//     }
// }


// // Khi nhấn vào dấu "x", đóng modal
// span.onclick = function() {
//     modal.style.display = "none";
// }

// // Khi nhấn ra ngoài modal, đóng modal
// window.onclick = function(event) {
//     if (event.target === modal) {
//         modal.style.display = "none";
//     }
// }
// kết thúc JS cho khiếu nại

// JS cho báo cáo thóng kê

// Hiển thị bảng doanh thu mặc định
// document.getElementById("salesTable").style.display = "table";

// // Lắng nghe sự kiện click cho nút thống kê doanh thu
// document.getElementById("revenueButton").addEventListener("click", function() {
//     document.getElementById("salesTable").style.display = "table"; // Hiển thị bảng doanh thu
//     document.getElementById("refundTable").style.display = "none"; // Ẩn bảng hoàn trả
// });

// // Lắng nghe sự kiện click cho nút thống kê hoàn trả hàng
// document.getElementById("refundButton").addEventListener("click", function() {
//     document.getElementById("refundTable").style.display = "table"; // Hiển thị bảng hoàn trả
//     document.getElementById("salesTable").style.display = "none"; // Ẩn bảng doanh thu
// });

// // xuất file excel
// document.getElementById("exportExcel").addEventListener("click", function() {
//     const salesTable = document.getElementById("salesTable");
//     const refundTable = document.getElementById("refundTable");

//     // Tạo một workbook mới
//     const wb = XLSX.utils.book_new();

//     // Chuyển đổi bảng doanh thu thành sheet
//     const salesSheet = XLSX.utils.table_to_sheet(salesTable);
//     XLSX.utils.book_append_sheet(wb, salesSheet, "Doanh Thu");

//     // Chuyển đổi bảng hoàn trả thành sheet
//     const refundSheet = XLSX.utils.table_to_sheet(refundTable);
//     XLSX.utils.book_append_sheet(wb, refundSheet, "Hoàn Trả");

//     // Xuất file Excel
//     XLSX.writeFile(wb, "BaoCaoThongKe.xlsx");
// });
//kết thúc JS cho báo cáo thống kê

// chuyển đổi giữa div báoo cáo thống kê và div xử lý khiếu nại






