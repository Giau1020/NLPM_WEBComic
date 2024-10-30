   // Mở modal
   document.getElementById('openModal').onclick = function() {
    document.getElementById('invoiceModal').style.display = 'flex';
}

// Đóng modal
document.getElementById('closeModal').onclick = function() {
    document.getElementById('invoiceModal').style.display = 'none';
}

// Đóng modal khi nhấn ngoài vùng modal
window.onclick = function(event) {
    if (event.target === document.getElementById('invoiceModal')) {
        document.getElementById('invoiceModal').style.display = 'none';
    }
}

function viewInvoice(orderId) {
    // Chuyển hướng đến trang hóa đơn với orderId
    window.location.href = `/invoice.html?orderId=${orderId}`;
    // document.getElementById('order-id').value = orderId;
}
 // Hàm xem chi tiết sản phẩm và thêm nút đánh giá chỉ cho đơn hàng đã hoàn thành
