//Sự kiện khi nhấn nút `Thêm` => Hiển thị form

function popUpAddNewComic(){
    let e = document.querySelector('#form-add-new-comic');
    e.style.display = "block";
    document.getElementById("uploadForm").reset();

    // Hiển thị danh sách thể loại (trong form thêm)
    let select_genre = document.querySelector('#inp-genre');
    let api_getListGenre = "http://localhost:8080/api/v1/sng/admin/genres";
    getData(api_getListGenre)
    .then(listGenre => {
        listGenre.forEach(genre => {console.log("Đang xử lý dữ liệu json");
            const option = document.createElement('option');
            option.value = genre.id;
            option.textContent = genre.name;
            select_genre.appendChild(option);// Khởi tạo Select2

        });
        $('#inp-genre').select2({
            placeholder: "Chọn thể loại",
            allowClear: true, // Cho phép xóa thể loại đã chọn
            // tags: true // Cho phép thêm tag mới nếu cần
        });
       

    }).catch(error => {
        console.error("Lỗi khi lấy danh sách thể loại:", error);
    });


    // Hiển thị danh sách tác giả (trong form thêm)
    let select_author = document.querySelector('#name-author');
    let api_getListAuthor = 'http://localhost:8080/api/v1/sng/admin/authors';
    getData(api_getListAuthor)
    .then(listAuthors => {
        listAuthors.forEach(author => {
            console.log("Dang xu ly du lieu cua tac gia");
            const option_author = document.createElement('option');
            option_author.value = author.id;
            option_author.textContent = author.name;   
            select_author.appendChild(option_author);
        });
        $('#name-author').select2({
                placeholder: "Nhập vào tác giả",
                allowClear: true,
                tags: true,
                
        });  
    });


    $('#name-author').on('select2:select', function(e) {
        var newTag = e.params.data.id; // Lấy giá trị tag mới
        
        // Kiểm tra nếu tag này là tag mới (không phải từ danh sách có sẵn)
            if (e.params.data.id === e.params.data.text) {
                console.log("Tag mới được thêm vào danh sách chờ: " + newTag);
                
                // Lưu tag mới vào mảng `newTags` để xử lý sau khi nhấn submit
                newTags.push(newTag);
            
            } else {
                console.log("Tag đã có: " + e.params.data.text + " với ID: " + e.params.data.id);
            }
    
    });
    console.log(newTags.length);
    

    document.getElementById('files-img').addEventListener('change', function(event) {
        const files = event.target.files;
       
        
        if (files.length !== 5) {
          
          alert("You can only upload up to 5 images");
          document.getElementById('preview').innerHTML = '';
          event.target.value = ''; // Reset the input so no files are uploaded
        } else {
            //document.getElementById('files-img').value = ' ';// Clear the error message if file count is valid
        }
      });

}
var newTags = [];

function closePopUpAddNewComic(){
    let e = document.querySelector('#form-add-new-comic');
    e.style.display = "none";
}

//ĐÓng popup
let btn_close = document.querySelector('.btn-close');
btn_close.addEventListener('click', closePopUpAddNewComic());


// Csript upload hình và form điền
const uploadForm = document.getElementById('uploadForm');
const filesInput = document.getElementById('files-img');
const filesInputCover = document.getElementById('files-cover-img');
const preview = document.getElementById('preview');
const preview1 = document.getElementById('preview1');
const responseDiv = document.getElementById('response');

// Hiển thị preview hình ảnh trước khi tải lên
filesInput.addEventListener('change', () => {
    preview.innerHTML = '';
    const files = filesInput.files;

    if(files.length === 5){
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        if (file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.onload = (e) => {
                const img = document.createElement('img');
                img.src = e.target.result;
                preview.appendChild(img);
            };
            reader.readAsDataURL(file);
        }
    }}
});
filesInputCover.addEventListener('change', () => {
    preview1.innerHTML = '';
    const files = filesInputCover.files;
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        if (file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.onload = (e) => {
                const img = document.createElement('img');
                img.src = e.target.result;
                preview1.appendChild(img);
            };
            reader.readAsDataURL(file);
        }
    }
});

//Sự kiện của nút tìm kiếm trong quản lý sản phẩm
function searchComic() {
    let btn_search_comic = document.querySelector('.search-product');
    let inp_search = document.querySelector('.search-frame-inp');
   inp_search.value = '';
    if (inp_search) { // Kiểm tra nếu inp_search không phải là null
        inp_search.focus(); // Đưa con trỏ vào ô input
        inp_search.placeholder = "Nhập nội dung tìm kiếm..."; // Thay đổi placeholder
    } else {
        console.error("Không tìm thấy phần tử với class 'search-frame-inp'");
    }

    document.querySelector('.search-frame-inp').disabled = false;
    
}

function inforTools(){
    let element = document.querySelector('.delete-product');
    let infor  = document.querySelector(`.tool-information`);
    infor.innerHTML = "Bạn đang ở chế độ xóa";
    document.querySelector('.delete-all-comics').style.display = 'block';
    document.querySelector('.delete-comic').style.display = 'block';
    var button = document.createElement("button");
    button.innerHTML = "Thoát";
    // Thiết lập CSS cho nút thoát chế độ xóa
    button.id = 'btn-exit-delete-mode';
    

    document.querySelector(".tool-information").appendChild(button);
    button.addEventListener("click", function(){
        exitToolInforTools();
    });

}
function exitToolInforTools(){
    document.querySelector('.tool-information').style.display = 'none';
    document.querySelector('.delete-all-comics').style.display = 'none';
    document.querySelector('.delete-comic').style.display = 'none';

}

// Hàm Thêm FILE CSS vào html
function addCSSFile(linkcss, id){
    var link = document.createElement("link");
    link.rel = "stylesheet";
    link.href = `${linkcss}`;
    link.id = `${id}`;
    document.getElementsByTagName("head")[0].appendChild(link);
    
}
// Hàm remove CSS
function removeCSSFile(id){
    var link = document.getElementById(`${id}`); // Tìm thẻ <link> bằng ID
    if (link) {
        link.parentNode.removeChild(link); // Gỡ bỏ thẻ <link> khỏi DOM
    }
}
// các hàm hiện ẩn chế độ chỉnh sửa
function showEditTool(className, id){
    let element = document.querySelector(`${className}`);
    if(element.style.display === 'none'){
            
        element.style.display = 'block';
        addCSSFile('./Form-popUp/Style-edit-comic.css', `${id}`);
        document.querySelector('#inp-id-comic2').value = "";
        document.querySelector('#inp-name-comic2').value = "";
        document.querySelector('#name-author2').value = "";
        document.querySelector('#price-comic2').value = "";
        document.querySelector('#inp-slb2').value = "";
        document.querySelector('#NXB2').value = "";
        document.querySelector('#inp-size2').value = "";
        document.querySelector('#inp-pages2').value = "";
        document.querySelector('#inp-genre2').value = "";
        document.querySelector('#content-comic2').value = "";
        document.querySelector('#summary_content-comic2').value = "";
        document.querySelector('#files-img2').value = "";
        document.querySelector('#inp-weight2').value = "";
        showAllComicsEditTable();
        edit_mode();
    }else if(element.style.display === 'block'){
        element.style.display = 'none';
        removeCSSFile(`${id}`);
    }
   
}
// Hàm bắt sự kiện trong chế độ chỉnh sửa
function edit_mode(){
    const table = document.querySelector('.table_edit_body');

    // Gắn sự kiện click vào bảng
    table.addEventListener('click', function(event) {
  // Kiểm tra xem click có phải vào một thẻ <tr> (hàng) không
  const clickedRow = event.target.closest('tr');

  if (clickedRow ) { 
    // Lấy nội dung của cột mà bạn muốn, ví dụ là cột thứ 2 (Title)
    const title = clickedRow.cells[0].textContent;
    console.log(title);
    // getComicById(title);
    updateComic(title);
    
  }});
    

}

// Hàm hiện div trang chủ
function On_div_main(){
    document.querySelector('.admin-detail-section').style.display = 'grid'; 
}
function On_div(className){
    let element = document.querySelector(`${className}`);
    element.style.display = 'block';
}
function Off_div(className){
    let element = document.querySelector(`${className}`);
    element.style.display = 'none';

}


function on_off_div(className){
    let element = document.querySelector(`${className}`);
    switch(className){
        case '.admin-detail-section':
            On_div_main();
            Off_div('.admin-product-section');
            Off_div('#order-manage-container');
            Off_div('#form-manage-account');
        break;
        case '.admin-product-section':
            Off_div('.admin-detail-section');// DIV main
            On_div('.admin-product-section');
            Off_div('#order-manage-container');
            Off_div('#form-manage-account');
        break;
        case '#order-manage-container':
           
            Off_div('.admin-detail-section'); // DIV main
            Off_div('.admin-product-section');
            Off_div('#form-manage-account');
            On_div('#order-manage-container');
            ShowAllOrders();
            break;

        case '#form-manage-account':
            console.log("new click ");
            Off_div('.admin-detail-section'); // DIV main
            Off_div('.admin-product-section');
            Off_div('#order-manage-container');
            On_div('#form-manage-account');
            getAllInforUser();
            break;
            
            


        default:
            On_div_main();
            Off_div('.admin-product-section');
            Off_div('#order-manage-container');
            Off_div('#form-manage-account');
    }
}

// Script cho chế độ xóa
// Khi click vào nút 'Xóa' ô chọn tất cả truyện sẽ hiện ra, và cột xóa sẽ hiện ra
// Hàm Hiện các chế độ của nút xóa

var click_delete = 0;
let isColumnHidden = true;
async function show_delete_comic(event){
    event.preventDefault();
  //  console.log("Delete button clicked");
  click_delete =1;
        document.querySelector('.delete-comic').style.display = 'table-cell';
        document.querySelector('.select-all-comic').style.display = 'block';
        document.querySelector('.btn-delete-comic').style.display = 'block';
        document.querySelector('.btn-close-delete-mode').style.display = 'block';
        isColumnHidden = !isColumnHidden; // Toggle the state
        await toggleColumnVisibility(5, true);
        updateCheckbox();

        document.querySelector('.btn-close-delete-mode').addEventListener('click', function(){
            closeDelete();
        });
        
        

}

async function toggleColumnVisibility(index, shouldShow) {
    const rows = document.querySelectorAll('.table_body tr');
    rows.forEach(row => {
        const cell = row.cells[index];
        cell.style.setProperty('display', shouldShow ? 'table-cell' : 'none', 'important');
          //  console.log(shouldShow ? "table-cell" : "none", cell); // Debugging output

       
    });
    return;
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
// Hàm lấy dữ liệu của user account
async function getAllInforUser() {
    let api = `http://localhost:8080/api/v1/sng/admin/users`;
    getDataFromAPI(api)
    .then(users => {
        let element = document.querySelector('.tbody_manage_account');
        element.innerHTML = ``; // Clear only once before the loop

        users.forEach(user => {
            let status  = user.status;
            let TT;
            if(status == true){
                TT = 'Đang hoạt động';
            }else if(status == false){
                TT = 'Tài khoản đã bị khóa';
            }
            let row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.username}</td>
                <td>@gmail.com</td>
                <td>${TT}</td>
            `;
            element.appendChild(row);
        });
    })
    .catch(error => {
        console.log(error);
    });
}
// Hàm check mật khẩu
function togglePasswordFrame() {
    document.querySelector('#username').value = ``;
    document.querySelector('#currentPassword').value = ``;
    document.getElementById("newPassword").value = ``;
    document.getElementById("confirmPassword").value = ``;
    document.getElementById("errorMessage");

    const passwordFrame = document.getElementById("passwordFrame");
    const overlay = document.getElementById("overlay");

    if (passwordFrame.style.display === "none" || passwordFrame.style.display === "") {
        passwordFrame.style.display = "block"; // Hiển thị khung
        overlay.style.display = "block"; // Hiển thị overlay
    } else {
        passwordFrame.style.display = "none"; // Ẩn khung
        overlay.style.display = "none"; // Ẩn overlay
    }
}

function validatePassword() {
    const username = document.querySelector('#username').value;
    const oldPassword = document.querySelector('#currentPassword').value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const errorMessage = document.getElementById("errorMessage");

    if (newPassword !== confirmPassword) {
        errorMessage.textContent = "Mật khẩu mới và xác nhận mật khẩu không khớp.";
        return false; // Ngăn chặn gửi form
    }

    errorMessage.textContent = ""; // Xóa thông báo lỗi nếu khớp

    updatePassword(username,oldPassword, newPassword );
   
    return true; // Cho phép gửi form
}
async function updatePassword(username, oldPassword, newPassword, token) {
    // Kiểm tra các tham số đầu vào
    if (!username || !oldPassword || !newPassword) {
        alert("Vui lòng nhập đầy đủ thông tin.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/v1/sng/admin/users/update_pass`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                // Thêm header Authorization nếu cần thiết
                ...(token && { 'Authorization': `Bearer ${token}` })
            },
            body: JSON.stringify({
                username: username,
                oldPass: oldPassword,
                newPass: newPassword
            })
        });

        // Kiểm tra trạng thái phản hồi
        if (response.ok) {
            const data = await response.json();
            // Giả sử server trả về thông báo trong trường 'message'
            if (data) {
                alert("Mật khẩu đã được cập nhật thành công!");
                togglePasswordFrame();
                return;
            } else {
                alert(`Thành công nhưng có thông báo phụ: ${data.message}`);
            }
        } else {
            // Xử lý lỗi từ server
            let errorMsg = "Đã xảy ra lỗi khi cập nhật mật khẩu.";
            try {
                const errorData = await response.json();
                if (errorData) {
                    // Nếu server trả về thông báo lỗi trong trường 'message'
                    errorMsg = errorData;
                }
            } catch (e) {
                // Nếu phản hồi không phải JSON
                errorMsg = `Lỗi: ${response.status} ${response.statusText}`;
            }
            alert(errorMsg);
        }
    } catch (error) {
        // Xử lý lỗi mạng hoặc các lỗi khác
        console.error("Đã xảy ra lỗi:", error);
        alert("Đã xảy ra lỗi khi cập nhật mật khẩu. Vui lòng thử lại sau.");
    }
}
























































// // Hàm ẩn /hiện side bar menu
// function hideDiv(id) {
//     var div = document.querySelector(id);
//     div.style.display = 'none'; // Ẩn thẻ div
    
//     changeStyle();
//   }
//   function showDivFlex(id) {
//     var div = document.querySelector(id);
//     div.style.display = 'inherit'; // Hiện thẻ div
//   }
//   function showDivGrid(id) {
//     var div = document.querySelector(id);
//     div.style.display = 'grid'; // Hiện thẻ div
//     document.querySelector('.admin-manage-menu2').remove();
//     changeStyle();
//   }

// // Hàm đổi CSS main page
// function changeStyle() {
//     var div = document.querySelector('.admin-detail-section');
//     var div_menu = document.querySelector('.detail-title');
//     // Kiểm tra nếu div đang có class "original-style"
//     if (div.classList.contains('admin-detail-section2')) {
//         // Nếu có, thay đổi class thành "new-style"
//         div.classList.remove('admin-detail-section2');
//         div.classList.add('admin-detail-section');
//     } else {
//         // Nếu không có, trở lại class "original-style"
//         div.classList.remove('admin-detail-section');
//         div.classList.add('admin-detail-section2'); 
//     }

//     if (div_menu.classList.contains('detail-title')) {
//         // Nếu có, thay đổi class thành "new-style"
//         div_menu.classList.remove('detail-title');
//         div_menu.classList.add('detail-title2');
//         addImage();
//     } else {
//         // Nếu không có, trở lại class "original-style"
//         div_menu.classList.remove('detail-title2');
//         div_menu.classList.add('detail-title'); 
//     }
// }

// function addImage() {
//     // Tạo một phần tử img
//     var img = document.createElement('img');
    
//     // Đặt thuộc tính src cho hình ảnh
//     img.src = './images-Admin/menu.png';  // Đường dẫn đến hình ảnh
//     img.alt = 'Hình Ảnh Mới';                   // Mô tả hình ảnh
//     img.className= 'admin-manage-menu2';
    
//     // Lấy div chứa hình ảnh
//     var container = document.querySelector('.detail-title2');
    
//     // Thêm hình ảnh vào container
//     container.appendChild(img);
//     img.onclick = function() {
//         showDivGrid('.admin-manage');
//         console.log("HI");
//     };
// }



//