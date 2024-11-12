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

async function ShowDetailOrder(id) {
    console.log("Đang chạy hàm show detail order");
    let api = `http://localhost:8080/api/v1/sng/admin/orders/${id}`;
    
    getDataFromAPI(api)
    .then(order => {
        // Lấy phần tử cha nơi sẽ thêm các div mới
            // const container = document.createElement('div');
            // container.className = 'order-info-grid';
            // document.body.appendChild(container); // Thêm container vào trang

            const container = document.querySelector('.order-info-grid');
            const orderHeader = document.querySelector('.order-header');
            container.innerHTML =` `;
            orderHeader.innerHTML = ``;

           // const orderId = document.createElement('div');
            orderHeader.innerHTML = `
                <h2>Chi Tiết Đơn Hàng <span>${order.id}</span></h2>
                <button class="btn-status">${order.orderStatus}</button>
            `;
           // orderHeader.appendChild(orderId);

            // Tạo thông tin Khách hàng
            const customerInfo = document.createElement('div');
            customerInfo.className = 'card';
            customerInfo.innerHTML = `
                <h3><i class="fas fa-user"></i> Thông tin Khách hàng</h3>
                <p><strong>Tên:</strong> ${order.fullName}</p>
                <p><strong>Số điện thoại:</strong> ${order.phoneNumber}</p>
                <p><strong>Địa chỉ:</strong> ${order.address}, ${order.ward}, ${order.district}, ${order.province}</p>
            `;
            container.appendChild(customerInfo); // Thêm thông tin khách hàng vào container

            // Tạo thông tin Vận chuyển
            const shippingInfo = document.createElement('div');
            shippingInfo.className = 'card';
            shippingInfo.innerHTML = `
                <h3><i class="fas fa-truck"></i> Thông tin Vận chuyển</h3>
                <p><strong>Đơn vị vận chuyển:</strong> Viettel Post</p>
                <p><strong>Mã vận đơn:</strong> <a href="#">${order.id}</a></p>
                <p><strong>Trạng thái giao hàng:</strong> ${order.orderStatus}</p>
                <p><strong>Địa chỉ giao hàng:</strong> ${order.address}, ${order.ward}, ${order.district}, ${order.province}</p>
            `;
            container.appendChild(shippingInfo); // Thêm thông tin vận chuyển vào container
            
            let payStatus;
            let payOrder;
            if(order.Payment == "Thanh toán trực tuyến"){
                payStatus = 'Đã thanh toán';
                payOrder = "VNPay";
            }else{
                payStatus = 'Chưa thanh toán';
                payOrder = "COD";
            }

            if(order.orderStatus == "Đã hoàn thành"){
                payStatus = 'Đã thanh toán';
            }
            // Tạo thông tin Thanh toán
            const paymentInfo = document.createElement('div');
            paymentInfo.className = 'card';
            paymentInfo.innerHTML = `
                <h3><i class="fas fa-credit-card"></i> Thông tin Thanh toán</h3>
                <p><strong>Phương thức thanh toán:</strong> ${payOrder}</p>
                <p><strong>Trạng thái thanh toán:</strong> ${payStatus}</p>
                <p><strong>Tổng tiền sản phẩm:</strong> ${order.totalPrice}</p>
                <p><strong>Phí vận chuyển:</strong> 0VND</p>
                <p><strong>Tổng cộng:</strong> <span class="total-amount">${order.totalPrice}</span></p>
            `;
            container.appendChild(paymentInfo); // Thêm thông tin thanh toán vào container

            // Tạo danh sách sản phẩm
            //const productList = document.createElement('div');
            // productList.className = 'product-list';
            const productList = document.querySelector('.product-list');
            productList.innerHTML = `
                <h3><i class="fas fa-box"></i> Sản phẩm trong đơn hàng</h3>
                <table style="width: 100%;">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Giá đơn vị</th>
                            <th>Số lượng</th>
                            <th>Tổng giá</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    <tfoot>
                        <tr>
                            
                        </tr>
                    </tfoot>
                </table>
            `;
           //.appendChild(productList); // Thêm danh sách sản phẩm vào container

            const productListContainer = productList.querySelector('tbody');
            order.items.forEach(item => {
                const comic = item.comic;
                const comicName = comic.name;
                const comicPrice = comic.price;
                const itemQuantity = item.quantity;
                const totalItemPrice = item.quantity * item.price;

                // Tạo hàng mới trong bảng
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${comicName}</td>
                    <td>${comicPrice.toLocaleString()} VND</td>
                    <td>${itemQuantity}</td>
                    <td>${totalItemPrice.toLocaleString()} VND</td>
                `;
                productListContainer.appendChild(row); // Thêm hàng vào bảng
            });

            const total = productList.querySelector('tfoot');
            const row_tr = document.createElement('tr');
            row_tr.innerHTML = `
                            <td colspan="3">Tổng cộng:</td>
                            <td>${order.totalPrice} VND</td>
            `;
            total.appendChild(row_tr);
            
    })
    .catch(error =>{
        console.log("Lỗi khi gọi API: ", error);
    });


    
}function openStatusDiv() {
    document.getElementById("statusDiv").style.display = "block";
    document.getElementById("overlay").style.display = "block";

    
}

function closeStatusDiv() {
    document.getElementById("statusDiv").style.display = "none";
    document.getElementById("overlay").style.display = "none";
}

function saveStatus() {
    const status = document.getElementById("statusSelect").value;
    // Logic to save status goes here
    closeStatusDiv();
    const orderId = sessionStorage.getItem('orderId');
    
    let newStatus = document.querySelector('#statusSelect').value;
   
    updateOrderStatus(orderId, newStatus);
    document.querySelector('.btn-status').innerHTML = newStatus;

}

function updateOrderStatus(ids, newStatus) {
    // Kiểm tra nếu không có ID nào được chọn hoặc trạng thái mới chưa được cung cấp
    if (ids.length === 0 || !newStatus) {
        alert("Vui lòng chọn ít nhất một đơn hàng và trạng thái mới để cập nhật.");
        return;
    }

    // Gửi yêu cầu PUT đến server
    fetch(`http://localhost:8080/api/v1/sng/admin/orders/update_status?ids=${ids}&newStatus=${encodeURIComponent(newStatus)}`, {
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
    
    })
    .catch(error => {
        alert(error.message); // Hiển thị lỗi nếu có
    });
}


function openEditInfoDiv() {
    let status = document.querySelector('.btn-status').innerHTML;
    if(status == 'Đã hủy' || status == 'Đang vận chuyển' || status == 'Đã hoàn thành'){
        alert("Chỉ có thể chỉnh sửa khi đơn hàng ở trạng thái \"Chờ xác nhận\"");
        return;
    }

    document.getElementById("editInfoDiv").style.display = "block";
    document.getElementById("overlay").style.display = "block";
}

function closeEditInfoDiv() {
    document.getElementById("editInfoDiv").style.display = "none";
    document.getElementById("overlay").style.display = "none";
}

function saveCustomerInfo() {
    // const name = document.getElementById("editName").value;
    // const phone = document.getElementById("editPhone").value;
    // const address = document.getElementById("editAddress").value;
    
    // // Logic to save updated information goes here
    // console.log("Updated Information:", { name, phone, address });

    closeEditInfoDiv();
}





// API lấy tỉnh thành
// Get references to the select elements
const provinceSelect = document.getElementById('province');
const districtSelect = document.getElementById('district');
const wardSelect = document.getElementById('ward');

let locationData = [];

// Function to fetch location data from the API
function fetchLocationData() {
    fetch('https://esgoo.net/api-tinhthanh/4/0.htm')
        .then(response => response.json())
        .then(data => {
            if (data.error === 0) {
                locationData = data.data;
                localStorage.setItem('locationData', JSON.stringify(locationData)); 
                populateProvinces();
            } else {
                console.error('Lỗi lấy dữ liệu:', data.error_text);
            }
        })
        .catch(error => console.error('Error fetching data:', error));
}

// Check if data is in localStorage to avoid multiple API calls
if (localStorage.getItem('locationData')) {
    locationData = JSON.parse(localStorage.getItem('locationData'));
    populateProvinces();
} else {
    fetchLocationData();
}

// Function to populate the province dropdown
function populateProvinces() {
    provinceSelect.innerHTML = '<option value="">Chọn tỉnh/thành phố</option>';
    locationData.forEach(province => {
        const option = document.createElement('option');
        option.value = province.id;
        option.textContent = province.full_name;
        provinceSelect.appendChild(option);
    });
}

// Function to update the district dropdown based on the selected province
function updateDistricts() {
    const selectedProvinceId = provinceSelect.value;
    districtSelect.innerHTML = '<option value="">Chọn quận/huyện</option>';
    wardSelect.innerHTML = '<option value="">Chọn phường/xã</option>';

    const selectedProvince = locationData.find(province => province.id === selectedProvinceId);
    if (selectedProvince) {
        selectedProvince.data2.forEach(district => {
            const option = document.createElement('option');
            option.value = district.id;
            option.textContent = district.full_name;
            districtSelect.appendChild(option);
        });
    }
}

// Function to update the ward dropdown based on the selected district
function updateWards() {
    const selectedProvinceId = provinceSelect.value;
    const selectedDistrictId = districtSelect.value;
    wardSelect.innerHTML = '<option value="">Chọn phường/xã</option>';

    const selectedProvince = locationData.find(province => province.id === selectedProvinceId);
    if (selectedProvince) {
        const selectedDistrict = selectedProvince.data2.find(district => district.id === selectedDistrictId);
        if (selectedDistrict) {
            selectedDistrict.data3.forEach(ward => {
                const option = document.createElement('option');
                option.value = ward.id;
                option.textContent = ward.full_name;
                wardSelect.appendChild(option);
            });
        }
    }
}

// Event listeners to trigger updates for districts and wards
provinceSelect.addEventListener('change', updateDistricts);
districtSelect.addEventListener('change', updateWards);
console.log(locationData); // Check the structure of location data


// Hàm set thông tin cho div chỉnh sửa
function setInforEditDiv(id){
    let api = `http://localhost:8080/api/v1/sng/admin/orders/${id}`;
    getDataFromAPI(api)
    .then(order => {
        document.querySelector('#fullName').value = order.fullName;
        document.querySelector('#phoneNumber').value = order.phoneNumber;
        document.querySelector('#province').options[document.getElementById('province').selectedIndex].textContent = order.province;
        document.querySelector('#district').options[document.getElementById('district').selectedIndex].textContent = order.district;
        document.querySelector('#ward').options[document.getElementById('ward').selectedIndex].textContent = order.ward;
        document.querySelector('#address').value = order.address;
        
    })
    .catch(error =>{
        console.log("Lỗi khi gọi API: ", error);
    });
    document.querySelector(".edit-order").addEventListener('click', function(){
        update_infor_customer(id);
        location.reload();
        setInforEditDiv(id);
    });
}


async function putDataToAPI(url, data) {
    try {
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            const errorMessage = `Lỗi HTTP! Trạng thái: ${response.status}`;
            return { success: false, error: errorMessage };
        }

        const responseData = await response.json();
        return { success: true, data: responseData };
    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
        return { success: false, error: error.message };
    }
}



// Lấy thông tin từ div chỉnh sửa lưu xuống CSDL
async function update_infor_customer(id){
    let fullName =  document.querySelector('#fullName').value;
    let phoneNumber = document.querySelector('#phoneNumber').value;
    let province = document.querySelector('#province').options[document.getElementById('province').selectedIndex].textContent ;
    let district = document.querySelector('#district').options[document.getElementById('district').selectedIndex].textContent;
    let ward = document.querySelector('#ward').options[document.getElementById('ward').selectedIndex].textContent;
    let address = document.querySelector('#address').value;

    let updateData = {
        fullName: fullName,
        phoneNumber: phoneNumber,
        province: province,
        district: district,
        ward: ward,
        address: address
    }

    let api = `http://localhost:8080/api/v1/sng/admin/orders/${id}/updateCustomerInfo`;
    let check = await putDataToAPI(api, updateData);
    if (check.success) {
        alert('Cập nhật thông tin khách hàng thành công');
        
    } else {
        alert(`Cập nhật thông tin thất bại: ${response.error.message || response.error}`);
    }

}

// JS của nút hủy đơn hàng
function showConfirmCancelOrder() {
    // Hiển thị hộp thoại xác nhận hủy đơn hàng
    let status = document.querySelector('.btn-status').innerHTML;
    if(status === 'Đã hủy' || status === 'Đang vận chuyển' || status === 'Đã hoàn thành'){
        alert("Chỉ có thể xóa khi đơn hàng ở trạng thái \"Chờ xác nhận\"");
        //console.log("....");
        return;
    }
    else document.getElementById("confirmCancelOrder").style.display = "flex";
}

function confirmCancel(isConfirmed) {
    let status = document.querySelector('.btn-status').innerHTML;
    if(status === 'Đã hủy' || status === 'Đang vận chuyển' || status === 'Đã hoàn thành'){
        alert("Chỉ có thể xóa khi đơn hàng ở trạng thái \"Chờ xác nhận\"");
        return;
    }
    if (isConfirmed) {
       // alert("Đơn hàng đã bị hủy thành công.");
        // Thêm mã xử lý khi hủy đơn hàng ở đây, ví dụ gọi API hoặc tải lại trang
         // Tải lại trang sau khi hủy đơn hàng
         const orderId = sessionStorage.getItem('orderId');
        delete_order(orderId);  
    }
    // Ẩn hộp thoại xác nhận hủy đơn hàng
    document.getElementById("confirmCancelOrder").style.display = "none";
}


async function delete_order(id){
    let api = `http://localhost:8080/api/v1/sng/admin/orders/cancel/${id}`;
    
    try {
        const response = await fetch(api, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if(response.ok){
            alert("Xóa đơn hàng thành công");
            location.href = '/html/list_edit_order.html';

        }else {
            alert("Xóa đơn hàng thất bại");
        }

        
    } catch (error) {
        alert("Xóa đơn hàng thất bại");
        console.error('Có lỗi xảy ra:', error);
        return { success: false, error: error.message };
    }
}


// xem hóa đơn

function viewInvoice(orderId) {
    console.log("Đang chạy hàm viewInvoice với orderId:", orderId);

    // Kiểm tra nếu orderId hợp lệ
    if (!orderId) {
        console.error("orderId không hợp lệ.");
        return;
    }

    // URL API để lấy thông tin đơn hàng
    let api = `http://localhost:8080/api/v1/sng/admin/orders/${orderId}`;

    getDataFromAPI(api)
        .then(order => {
            // Tạo nội dung HTML cho hóa đơn
            let invoiceContent = `
                <!DOCTYPE html>
                <html lang="vi">
                <head>
                    <meta charset="UTF-8">
                    <title>Hóa Đơn - Đơn Hàng #${order.id}</title>
                    <style>
                        /* CSS cho hóa đơn */
                        body { font-family: 'Roboto', sans-serif; padding: 20px; color: #333; }
                        h1, h2 {
                         color: #e86868;
                         }
                         h1, h3 {
                         text-align: center;
                         }
                        table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 20px; }
                        th, td {
                        border: 1px solid #ddd;
                         padding: 8px;
                         text-align: left; }
                        th {
                        background-color: #f2c6c6; }
                        .total {
                         font-weight: bold;
                          }
                        .text-right { text-align: right; }
                    </style>
                </head>
                <body>
                    <h1>HÓA ĐƠN</h1>
                    <h3>Đơn Hàng ${order.id}</h3>
                    <p id="current-time"><strong>Giờ xuất hóa đơn :</strong> <span id="time"></span></p>
                    <p><strong>Ngày đặt hàng:</strong> ${formatDateTime(order.orderTime)}</p>
                    <p><strong>Tên khách hàng:</strong> ${order.fullName}</p>
                    <p><strong>Số điện thoại:</strong> ${order.phoneNumber}</p>
                    <p><strong>Địa chỉ giao hàng:</strong> ${order.address}, ${order.ward}, ${order.district}, ${order.province}</p>
                     <p><strong>Thanh toán:</strong>${order.payment ? order.payment : 'Thanh toán khi nhận hàng'}</p>

                    <table>
                        <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th>Giá đơn vị (VND)</th>
                                <th>Số lượng</th>
                                <th>Thành tiền (VND)</th>
                            </tr>
                        </thead>
                        <tbody>
            `;

            // Thêm các sản phẩm vào bảng
            order.items.forEach(item => {
                const comic = item.comic;
                const comicName = comic.name;
                const comicPrice = comic.price;
                const itemQuantity = item.quantity;
                const totalItemPrice = item.quantity * item.price;

                invoiceContent += `
                    <tr>
                        <td>${comicName}</td>
                        <td>${comicPrice.toLocaleString()}</td>
                        <td>${itemQuantity}</td>
                        <td>${totalItemPrice.toLocaleString()}</td>
                    </tr>
                `;
            });

            // Thêm tổng cộng vào hóa đơn
            invoiceContent += `
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" class="text-right total">Tổng cộng:</td>
                                <td class="total">${order.totalPrice.toLocaleString()} VND</td>
                            </tr>
                        </tfoot>
                    </table>

                    <p style="text-align: center;">Cảm ơn bạn đã mua hàng tại cửa hàng của chúng tôi!</p>
                    <h3>Công Ty SNG Comics</h3>
                    <h3>123 Đường ABC ,Thành phố XYZ </h3>
                    <h3>mọi chi tiết và thắc mắc vui lòng liên hệ qua SĐT 0123456789</h3>



                   <script>
                   document.addEventListener("DOMContentLoaded", function () {
                           const now = new Date();
                           const formattedDate = now.toLocaleDateString("vi-VN");
                           const formattedTime = now.toLocaleTimeString("vi-VN", {
                               hour: "2-digit",
                               minute: "2-digit",
                               second: "2-digit"
                           });
                           document.getElementById("time").textContent = formattedDate + ' ' + formattedTime;

                           // Tự động mở hộp thoại in sau khi tải xong
                           window.print();
                       });
                    </script>

                </body>
                </html>
            `;

            // Mở cửa sổ mới và ghi nội dung hóa đơn
            let invoiceWindow = window.open('', '', 'width=800,height=600');
            invoiceWindow.document.write(invoiceContent);
            invoiceWindow.document.close();

            // Chờ nội dung tải xong rồi in
            invoiceWindow.focus();
            invoiceWindow.print();
        })
        .catch(error => {
            console.error("Lỗi khi lấy thông tin đơn hàng:", error);
        });
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

