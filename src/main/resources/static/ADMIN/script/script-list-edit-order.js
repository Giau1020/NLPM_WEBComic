async function ShowOrderstoEdit() {
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
            <td>${order.id}</td>
            <td>${order.fullName}</td>
            <td>${time}</td>
            <td>${order.orderStatus}</td>
            <td><img onclick = "btn_edit(${order.id}); ShowDetailOrder(${order.id});" class="edit-icon" src="../images-Admin/editing.png"  alt=""></td>
            `;
        table.appendChild(row);
        }); 
    })
    .catch(error =>{
        console.log("Lỗi khi gọi API: ", error);
    });


    
}

function btn_edit(orderid){
    
         //   const orderId = this.getAttribute('data-id');
            sessionStorage.setItem('orderId', orderid);
            window.location.href = "/html/edit_detail_order.html";
     
}
ShowOrderstoEdit();
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

function filterOrders() {
    const customerFilter = document.getElementById('customerFilter').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;

    const rows = document.querySelectorAll('tbody tr');

    rows.forEach(row => {
        const customerName = row.cells[1].textContent.toLowerCase();
        const customerID = row.cells[0].textContent.toLowerCase();
        const status = row.cells[3].textContent;

        const matchesStatus = statusFilter === "" || status === statusFilter;
        const matchesCustomer = customerFilter === "" || customerName.includes(customerFilter);
        const matchesidCustomer = customerFilter === "" || customerID.includes(customerFilter);

        if ((matchesCustomer && matchesStatus) || (matchesidCustomer && matchesStatus) ) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });

}

