// Chọn tất cả các checkbox

const selectAllCheckbox = document.getElementById('selectAll');
//const orderCheckboxes = document.querySelectorAll('input[name="order"]');

selectAllCheckbox.addEventListener('change', function() {
    // const orderCheckboxes = document.querySelectorAll('input[name="order"]'); // Cập nhật danh sách checkbox động
    // orderCheckboxes.forEach(checkbox => {
    //     checkbox.checked = this.checked;
    // });
    const visibleOrderCheckboxes = Array.from(document.querySelectorAll('input[name="order"]'))
    .filter(checkbox => checkbox.closest('tr').style.display !== 'none');

    // Cập nhật trạng thái checked của từng checkbox hiển thị
    visibleOrderCheckboxes.forEach(checkbox => {
        checkbox.checked = this.checked;
    });
});


// Xử lý sự kiện nút cập nhật
const updateButton = document.getElementById('updateButton');
const newStatusSelect = document.getElementById('newStatus');

updateButton.addEventListener('click', function() {
    const orderCheckboxes = document.querySelectorAll('input[name="order"]'); // Cập nhật danh sách checkbox động
    const selectedOrders = [];
    orderCheckboxes.forEach(checkbox => {
      
        if (checkbox.checked) {
            selectedOrders.push(checkbox.value);
        }
    });

    const newStatus = newStatusSelect.value;

    if (selectedOrders.length === 0) {
        alert('Vui lòng chọn ít nhất một đơn hàng.');
        return;
    }

    if (newStatus === '') {
        alert('Vui lòng chọn trạng thái mới.');
        return;
    }
 updateOrderStatus(selectedOrders, newStatus);
    alert(`Cập nhật trạng thái cho các đơn hàng: ${selectedOrders.join(', ')} thành "${newStatus}"`);
});

// Hàm lọc đơn hàng
function filterOrders() {
    const customerFilter = document.getElementById('customerFilter').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;

    const rows = document.querySelectorAll('tbody tr');

    rows.forEach(row => {
        const customerName = row.cells[2].textContent.toLowerCase();
        const status = row.cells[4].textContent;

        const matchesCustomer = customerFilter === "" || customerName.includes(customerFilter);
        const matchesStatus = statusFilter === "" || status === statusFilter;

        if (matchesCustomer && matchesStatus) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });

}


async function getDataFromAPI(url) {
    try {
        const response = await fetch(url);
        
        if (!response.ok) {
            throw new Error(`Lỗi HTTP! Trạng thái: ${response.status}`);
        }
        
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
    }
}
async function ShowOrderstoUpdate() {
    let api = `http://localhost:8080/api/v1/sng/admin/orders`;
    console.log("show all orders");
    getDataFromAPI(api)
    .then(orders => {
        let table = document.querySelector('.table_order_update');
        table.innerHTML = '';
        orders.forEach(order => {
            let time =  formatDateTime(order.orderTime);
            const row = document.createElement('tr');
            row.innerHTML = `
           <td><input type="checkbox" name="order" value="${order.id}"></td>
            <td>${order.id}</td>
            <td>${order.fullName}</td>
            <td>${time}</td>
            <td>${order.orderStatus}</td>
            `;
        table.appendChild(row);
        }); 
    })
    .catch(error =>{
        console.log("Lỗi khi gọi API: ", error);
    });
    
}
ShowOrderstoUpdate();

function updateOrderStatus(ids, newStatus) {
    // Kiểm tra nếu không có ID nào được chọn hoặc trạng thái mới chưa được cung cấp
    if (ids.length === 0 || !newStatus) {
        alert("Vui lòng chọn ít nhất một đơn hàng và trạng thái mới để cập nhật.");
        return;
    }

    // Gửi yêu cầu PUT đến server
    fetch(`http://localhost:8080/api/v1/sng/admin/orders/update_status?ids=${ids.join("&ids=")}&newStatus=${encodeURIComponent(newStatus)}`, {
        method: "PUT",
    })
    .then(response => {
        if (response.ok) {
            return response.text(); // Lấy phản hồi dưới dạng text nếu thành công
        } else {
            throw new Error("Cập nhật trạng thái thất bại. Vui lòng thử lại.");
        }
    })
    .then(data => {
        alert(data); // Hiển thị phản hồi từ server
        // Tải lại trang hoặc cập nhật giao diện người dùng tại đây nếu cần
        ShowOrderstoUpdate();
    })
    .catch(error => {
        alert(error.message); // Hiển thị lỗi nếu có
    });
}
