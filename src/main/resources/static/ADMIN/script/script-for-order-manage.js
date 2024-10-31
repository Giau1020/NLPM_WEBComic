async function ShowAllOrders() {
    let api = `http://localhost:8080/api/v1/sng/admin/orders`;
    console.log("show all orders");
    getDataFromAPI(api)
    .then(orders => {
        let table = document.querySelector('.table_body_order');
        table.innerHTML = '';
        orders.forEach(order => {
            let time =  formatDateTime(order.orderTime);
            const row = document.createElement('tr');
            row.innerHTML = `
            <td class="id_order_detail">${order.id}</td>
            <td>${order.fullName}</td>
            <td> ${time}</td>
            <td>${order.totalPrice}</td>
            `;
        table.appendChild(row);
        }); 
    })
    .catch(error =>{
        console.log("Lỗi khi gọi API: ", error);
    });
    
}

// Format ngày giờ lại
function formatDateTime(dateTimeStr) {
    const date = new Date(dateTimeStr);
    return date.toLocaleString('vi-VN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false
    });
}






