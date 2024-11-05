

// // Hàm để lấy và hiển thị khiếu nại theo userId
// function getComplaintsByUserId(userId) {
//     fetch(`http://localhost:8080/api/complaint/user/${userId}`)
//         .then(response => {
//             if (!response.ok) throw new Error('Lỗi mạng: ' + response.statusText);
//             return response.json();
//         })
//         .then(data => {
//             if (data.length === 0) {
//                 alert("Không tìm thấy khiếu nại nào cho người dùng này.");
//             } else {
//                 displayComplaints(data);
//             }
//         })
//         .catch(error => console.error('Có lỗi xảy ra:', error));
// }

// // Hàm để hiển thị khiếu nại trong bảng
// function displayComplaints(complaints) {
//     const complaintTable = document.getElementById('complaint-table');
//     complaintTable.innerHTML = ''; // Làm sạch bảng hiện tại

//     complaints.forEach(({ id, orderId, complaintTime, statusComplaint, descriptionComplaint }) => {
//         const row = `<tr>
//             <td>${id}</td>
//             <td>${orderId}</td>
//             <td>${new Date(complaintTime).toLocaleString()}</td>
//             <td>${statusComplaint}</td>
//             <td><button>Xem chi tiết</button></td>
//         </tr>`;
//         complaintTable.innerHTML += row; // Thêm hàng mới vào bảng
//     });
// }


// Lấy thẻ input ẩn chứa userId
const userId = document.getElementById('userIdinput');

    function getComplaintsByUserId(userId) {
            fetch(`http://localhost:8080/api/complaint/user/${userId}`)
                .then(response => {
                    if (!response.ok) throw new Error('Lỗi mạng: ' + response.statusText);
                    return response.json();
                })
                .then(data => {
                    if (data.length === 0) {
                        alert("Không tìm thấy khiếu nại nào cho người dùng này.");
                    } else {
                        displayComplaints(data);
                    }
                })
                .catch(error => console.error('Có lỗi xảy ra:', error));
        }

// Hàm để hiển thị khiếu nại trong bảng
function displayComplaints(complaints) {
    const complaintTable = document.getElementById('complaint-table');
    complaintTable.innerHTML = ''; // Làm sạch bảng hiện tại

    complaints.forEach(({ id, orderId, complaintTime, statusComplaint }) => {
        const row = `<tr>
            <td>${id}</td>
            <td>${orderId}</td>
            <td>${new Date(complaintTime).toLocaleString()}</td>
            <td>${statusComplaint}</td>
            <td><button onclick="viewComplaint('${id}')">Xem chi tiết</button></td>
        </tr>`;
        complaintTable.innerHTML += row; // Thêm hàng mới vào bảng
    });
}

// getComplaintsByUserId('userId');
// Hàm lấy thông tin người dùng (bao gồm userId)

fetch('http://localhost:8080/user/info', {
    method: 'GET',
    credentials: 'include' // Gửi cookie cùng yêu cầu
})
.then(response => {
    if (!response.ok) {
        if (response.status === 401) {
            throw new Error('Bạn chưa đăng nhập.');
        }
        throw new Error('Lỗi khi lấy thông tin người dùng.');
    }
    return response.json();
})
.then(user => {
    console.log('User Info:', user);
    if (user && user.id) {
        // Gán userId vào thẻ input ẩn
        document.getElementById('userIdinput').value = user.id;
        console.log('userId đã được gán:', user.id);
        getComplaintsByUserId(user.id);
    } else {
        throw new Error('Không tìm thấy thông tin người dùng.');
    }
})
.catch(error => {
    alert(error.message);
    console.error('Lỗi lấy thông tin người dùng:', error);
});

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
                <p><strong>Phản hồi từ người bán:</strong> ${data.response}</p>
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