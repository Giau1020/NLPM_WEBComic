//Sự kiện khi nhấn nút `Thêm` => Hiển thị form

function popUpAddNewComic(){
    let e = document.querySelector('#form-add-new-comic');
    e.style.display = "block";

}
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
    }
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
    getComicById(title);
  }});
    

}
edit_mode();
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
        break;
        case '.admin-product-section':
            Off_div('.admin-detail-section');
            On_div('.admin-product-section');
        break;
        default:
            On_div_main();
            Off_div('.admin-product-section');
    }
}

// Script cho chế độ xóa
// Khi click vào nút 'Xóa' ô chọn tất cả truyện sẽ hiện ra, và cột xóa sẽ hiện ra
// Hàm Hiện các chế độ của nút xóa
function show_delete_comic(){
    On_div('.select-all-comic');
    On_div('.delete-comic');
    On_div('.btn-delete-comic');
   // On_div('.checkbox-delete-comic');
   const checkboxes = document.querySelectorAll('.checkbox-delete-comic');

    checkboxes.forEach(td => {
        td.style.display = 'block';
    });
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