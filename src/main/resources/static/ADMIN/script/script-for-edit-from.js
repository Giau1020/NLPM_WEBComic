// Các API đc sử dụng
function resetEditMode(){
    document.getElementById('name-author2').innerHTML = '';
    document.getElementById('inp-genre2').innerHTML = '';
    document.getElementById('preview2').innerHTML = '';
    document.getElementById('preview1').innerHTML = '';
    document.getElementById('cover-img2').style.display = 'none';
    document.querySelector('.select2-search__field').placeholder = '';
    $('.select2-search__field').select2({
        placeholder: ''
    });
    $('.select2-search__field').on('select2:open', function() {
        $('.select2-dropdown').css('width', '500px');
    });
    
    

}

async function updateComic(comicId) {
    // Các API get
    let api_get_all_authors = 'http://localhost:8080/api/v1/sng/admin/authors';// API lấy danh sách author
    let api_get_all_genres = 'http://localhost:8080/api/v1/sng/admin/genres';// API lấy danh sách author
    let api_get_comic_authors = `http://localhost:8080/api/v1/sng/admin/${comicId}/authors`; //API lấy danh sách tác giả theo id;
    let api_get_comic_genres = `http://localhost:8080/api/v1/sng/admin/${comicId}/genres`; //API lấy danh sách thể loại theo id;
    let api_get_comic_byId = `http://localhost:8080/api/v1/sng/admin/${comicId}`; // API lấy comic theo ID

    // Hiển thị dữ liệu lên form
    // Hiểm thị dữ liệu cơ bản
    let inp_id = document.querySelector('#inp-id-comic2');
    let inp_name = document.querySelector('#inp-name-comic2');
    let inp_author = document.querySelector('#name-author2');
    let inp_price = document.querySelector('#price-comic2');
    let inp_slb = document.querySelector('#inp-slb2');
    let inp_nxb = document.querySelector('#NXB2');
    let inp_size = document.querySelector('#inp-size2');
    let inp_pages = document.querySelector('#inp-pages2');
    let inp_genre = document.querySelector('#inp-genre2');
    let inp_content = document.querySelector('#content-comic2');
    let inp_summary = document.querySelector('#summary_content-comic2');
    let inp_url = document.querySelector('#cover-img2');
    let inp_weight = document.querySelector('#inp-weight2');
    
    

// Cách 1: Đặt giá trị của select thành rỗng (trường hợp không có option nào có giá trị rỗng thì không option nào được chọn)
document.getElementById('inp-genre2').value = '';





    try{
        let comic = await getData(api_get_comic_byId);
        
        if(comic){
            console.log("Bat dau lay du lieu");
            inp_id.value = comic.id;
            inp_name.value = comic.name;
            inp_price.value = comic.price;
            inp_slb.value = comic.quantity;
            inp_nxb.value = comic.publisher;
            inp_size.value = comic.size;
            inp_pages.value = comic.pages;
            inp_content.value = comic.description;
            inp_summary.value = comic.summarize ;
            inp_url.style.display = "block";
            inp_url.src = comic.url;
            inp_weight.value = comic.weight;
        }
    
    
    // Hiển thị danh sách tác giả và danh sách genre (kể cả đc select hay chưa)
   
        let authors = await getData(api_get_all_authors);
        let genres = await getData(api_get_all_genres);

        let comic_authors = await getData(api_get_comic_authors);
        let comic_genres  = await getData (api_get_comic_genres);
        let selectedAuthor = [];
        let selectedAuthorId = [];
        if(authors) {
           
            document.getElementById('name-author2').innerHTML = '';
            authors.forEach(author => {
                let option = document.createElement('option');
                option.value = author.id;
                option.textContent = author.name;

                if(comic_authors && comic_authors.map(a => a.id).includes(author.id)){
                    option.selected = true;
                    selectedAuthor.push(author.name); 
                    selectedAuthorId.push(author.id);
                }
                inp_author.appendChild(option);
            });
            $('#name-author2').select2({
                placeholder: selectedAuthor.join(', '), // Placeholder là danh sách tác giả đã chọn
                allowClear: true,
                tags: true
            }).on('select2:select', async function (e) {
                await getIdAuthors('#name-author2');

            });
        }
        
        let selectedGenres = [];
        let selectedGenresId = [];
        if(genres) {
            
             // Xóa nội dung cũ
             document.getElementById('inp-genre2').innerHTML = '';
            genres.forEach(genre => {
                let option = document.createElement('option');
                option.value = genre.id;
                option.textContent = genre.name;
        
                // Kiểm tra nếu genre đã được chọn
                if(comic_genres && comic_genres.map(g => g.id).includes(genre.id)){
                    option.selected = true;
                    selectedGenres.push(genre.name);
                    selectedGenresId.push(genre.id);
                }
        
                inp_genre.appendChild(option);
            });
        
            // Khởi tạo select2 với placeholder là các thể loại đã chọn
            $('#inp-genre2').select2({
                placeholder: selectedGenres.join(', '),
                allowClear: true,
                tags: false  // Cho phép thêm thể loại mới bằng cách nhập
            });
            
        }
       
        var currentComic = {
            name: comic.name,
            price: comic.price,
            url: comic.url,
            sold: comic.sold,
            quantity: comic.quantity,
            weight: comic.weight,
            description: comic.description,
            pages: comic.pages,
            size: comic.size,
            publisher: comic.publisher,
            summarize: comic.summarize,
            authorIds:selectedGenresId,
            genreIds:selectedAuthorId
            
        };
        console.log("aaaa");
        console.log(currentComic);

        document.getElementById('files-cover-img2').addEventListener('change', function (event) {
            let input = event.target;
            let reader = new FileReader();
    
            reader.onload = function () {
                let imgElement = document.getElementById('cover-img2');
                imgElement.src = reader.result;
            }
    
            if (input.files && input.files[0]) {
                reader.readAsDataURL(input.files[0]); // Đọc tệp và hiển thị hình ảnh
            }
        });




    //    Hiển thị 5 ảnh
    let api_5url = `http://localhost:8080/api/v1/sng/admin/imgcomic/${comicId}`; // api lấy 5 url truyện
    let urls = await getData(api_5url);
    let url = [];
    if(urls){
        url.push(urls.url1);
        url.push(urls.url2);
        url.push(urls.url3);
        url.push(urls.url4);
        url.push(urls.url5);
    }
    if(url.length === 5){
        // Xóa nội dung cũ của phần tử preview2
        document.getElementById('preview2').innerHTML = '';
    
        // Tạo và thêm các thẻ img dựa trên các URL trong mảng
        url.forEach(imgUrl => {
            if (imgUrl) { // Kiểm tra nếu URL tồn tại
                let img = document.createElement('img');
                img.src = imgUrl; // Gán URL cho thuộc tính src của img
                img.alt = 'Comic Image'; // Thêm thuộc tính alt cho ảnh
                img.style.width = '150px'; // Tùy chỉnh kích thước ảnh
                img.style.margin = '10px'; // Thêm khoảng cách giữa các ảnh
    
                // Thêm thẻ img vào phần tử với ID preview2
                document.getElementById('preview2').appendChild(img);
            }});
        }
        

        

    }catch(e){
        console.error('Error while updating data:', error.message);
    }

document.getElementById('files-img2').addEventListener('change', function(event) {
                let input = event.target;
                let previewDiv2 = document.getElementById('preview2');
                previewDiv2.innerHTML = ''; // Xóa nội dung cũ trước khi thêm ảnh mới
            
                if (input.files) {
                    // Lặp qua tất cả các tệp được chọn
                    Array.from(input.files).forEach(file => {
                        let reader = new FileReader();
            
                        // Khi file được đọc xong
                        reader.onload = function(e) {
                            let imgElement = document.createElement('img');
                            imgElement.src = e.target.result; // URL của hình ảnh
                            imgElement.style.maxWidth = "200px";
                            imgElement.style.margin = "10px";
            
                            previewDiv2.appendChild(imgElement); // Thêm thẻ <img> vào div#preview2
                        }
            
                        // Đọc file dưới dạng URL
                        reader.readAsDataURL(file);
                    });
                }
            });



    // Bắt đầu lưu truyện 
    // Giả sử đây là dữ liệu hiện tại của comic, bạn có thể lấy từ API trước đó




// Gửi comicDTO với chỉ những trường thay đổi đến API
document.querySelector('.btn-save-edit').addEventListener('click', function (){
    // Đây là các giá trị mới mà người dùng nhập vào từ form (hoặc UI khác)
let selectedGenres = $('#inp-genre2').select2('data'); 
let genreIds = selectedGenres.map(genre => parseInt(genre.id));
let selectedAuthors = $('#name-author2').select2('data'); 
let authorsIds = selectedAuthors.map(author => parseInt(author.id));
const updatedComicData = {
    name: document.querySelector('#inp-name-comic2').value,
    price: document.querySelector('#price-comic2').value,  // Không thay đổi giá
    url: document.querySelector('#files-cover-img2').value,
    sold: currentComic.sold,
    quantity: document.querySelector('#inp-slb2').value,  // Không thay đổi số lượng
    weight: document.querySelector('#inp-weight2').value,
    description: document.querySelector('#content-comic2').value,
    pages: document.querySelector('#inp-pages2').value,
    size: document.querySelector('#inp-size2').value,
    publisher: document.querySelector('#NXB2').value,
    summarize:document.querySelector('#summary_content-comic2').value,
    authorIds: authorsIds,
    genreIds: genreIds
};



// Tạo một đối tượng comicDTO mới, chỉ bao gồm các trường có thay đổi
const comicDTO = {};

// Kiểm tra từng trường và chỉ thêm vào comicDTO nếu có thay đổi
if (updatedComicData.name !== currentComic.name) {
    comicDTO.name = updatedComicData.name;
}else{
    comicDTO.name = currentComic.name;
}

if (updatedComicData.price !== currentComic.price) {
    comicDTO.price = updatedComicData.price;
}else{
    comicDTO.price = currentComic.price;
}
let newurl = '';

   
    async function sendFile() {
        let fileInput  = document.querySelector('#files-cover-img2');
        
        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];

            // Tạo FormData để gửi file
            const formData = new FormData();
            formData.append("file", file);

            // Gửi file lên server
            const uploadResponse = await fetch("http://localhost:8080/api/v1/sng/admin/upload", {
                method: 'POST',
                body: formData
            });
            if (uploadResponse.ok) {
                newurl = await uploadResponse.text(); // Nhận URL của file
               
                return newurl;
           
             
           
            } else {
                
                alert("Không thể tải lên hình ảnh");
                return null;
            }
        } else {
            alert("Vui lòng chọn hình ảnh");
        }
    }
    
    async function updateUrl() {
        // Đợi kết quả trả về từ sendFile trước khi cập nhật comicDTO
        const uploadedUrl = await sendFile(); 
    
        if (uploadedUrl) {
            comicDTO.url = uploadedUrl;  // Thiết lập URL sau khi upload thành công
            console.log("DTO: ", comicDTO.url);
        } else {
            console.log("Không thể cập nhật URL cho comicDTO");
        }
    }
  

 if (updatedComicData.url !== currentComic.url ) {

}else{
    comicDTO.url = currentComic.url;
    console.log("DTO: zzsaed");
}

if (updatedComicData.sold !== currentComic.sold) {
    comicDTO.sold = updatedComicData.sold;
}else{
    comicDTO.sold = currentComic.sold;
}

if (updatedComicData.quantity !== currentComic.quantity) {
    comicDTO.quantity = updatedComicData.quantity;
}else{
    comicDTO.quantity = currentComic.quantity;
}

if (updatedComicData.weight !== currentComic.weight) {
    comicDTO.weight = updatedComicData.weight;
}else{
    comicDTO.weight= currentComic.weight;
}

if (updatedComicData.description !== currentComic.description) {
    comicDTO.description = updatedComicData.description;
}else{
    comicDTO.description = currentComic.description;
}

if (updatedComicData.pages !== currentComic.pages) {
    comicDTO.pages = updatedComicData.pages;
}else{
    comicDTO.pages = currentComic.pages;
}

if (updatedComicData.size !== currentComic.size) {
    comicDTO.size = updatedComicData.size;
}else{
    comicDTO.size= currentComic.size;
}

if (updatedComicData.publisher !== currentComic.publisher) {
    comicDTO.publisher = updatedComicData.publisher;
}else{
    comicDTO.publisher = currentComic.publisher;
}

if (updatedComicData.summarize !== currentComic.summarize) {
    comicDTO.summarize = updatedComicData.summarize;
}else{
    comicDTO.summarize = currentComic.summarize;
}

if (JSON.stringify(updatedComicData.authorIds) !== JSON.stringify(currentComic.authorIds) || JSON.stringify(updatedComicData.authorIds) == null) {
    comicDTO.authorIds = updatedComicData.authorIds;
    //getIdAuthors('#name-author');
}

if (JSON.stringify(updatedComicData.genreIds) !== JSON.stringify(currentComic.genreIds) || JSON.stringify(updatedComicData.genreIds) == null) {
    comicDTO.genreIds = updatedComicData.genreIds;
}
    async function runmain() {
console.log(document.querySelector('#files-cover-img2').value );

        if(document.querySelector('#files-cover-img2').value == ""){
            comicDTO.url = currentComic.url;
            
        }
        else{await updateUrl();comicDTO.url = newurl;
            console.log("comic DTO111",comicDTO.url);}
        
       
       await updateComicById(comicId, comicDTO);
       await UpdateImgComic(comicId);
       document.querySelector("#form-edit-comic").style.display = 'none';
       removeCSSFile(`css-for-popup`);

    }
    runmain();
    
});



}
document.querySelector(".btn-cancel-edit").addEventListener("click", function(){
    document.querySelector("#form-edit-comic").style.display = 'none';
    removeCSSFile(`css-for-popup`);
})
async function updateComicById(comicId, comicDTO) {
   
        const api = `http://localhost:8080/api/v1/sng/admin/update/${comicId}`;  // Endpoint theo mapping của Spring Boot
    
        try {
            const response = await fetch(api, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json', // Đặt tiêu đề để xác định định dạng dữ liệu gửi lên là JSON
                },
                body: JSON.stringify(comicDTO), // Chuyển đổi comicDTO thành JSON
            });
    
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
    
            const updatedComic = await response.json(); // Chuyển đổi phản hồi JSON thành đối tượng
            console.log('Updated comic:', updatedComic); // Xử lý phản hồi thành công
            return updatedComic;
        } catch (error) {
            console.error('Error updating comic:', error); // Xử lý lỗi
            return null;
        }
    
}
async function getIdAuthors(id) {
    let selectedAuthors = $(id).select2('data');
    let authorIds = selectedAuthors.map(author => parseInt(author.id));

    const validAuthorIds = await sendAuthorDatatoAuthor();
    

    // Gộp 2 mảng authorIds và validAuthorIds
    let mergedAuthorIds = authorIds.concat(validAuthorIds).filter(id => !Number.isNaN(id));
    // console.log("id tác giả mới đc return lại là: ", mergedAuthorIds);
  return mergedAuthorIds; // Trả về mảng ID hợp lệ
}
async function sendAuthorDatatoAuthor() {
    if (newTags.length > 0) {
        try {
            const response = await fetch('http://localhost:8080/api/v1/sng/admin/authors/addnew', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ names: newTags }) // Gửi danh sách tác giả mới
            });

            if (response.ok) {
                const data = await response.json(); // Nhận phản hồi từ server
                console.log("Phản hồi từ server:", data);
                console.log("tac gia moi da dc tao");
                // Kiểm tra xem có nhận được phản hồi từ server và có ID hợp lệ
                const validIds = data.map(author => {
                    const id = parseInt(author.id); // Chuyển ID sang số
                    if (isNaN(id)) {
                        console.error("ID không hợp lệ:", author);
                        return null; // Bỏ qua nếu ID không hợp lệ
                    }
                    return id; // Trả về ID hợp lệ
                }).filter(id => id !== null); // Lọc ra các ID hợp lệ

                console.log("ID của các tác giả mới:", validIds);

                newTags = []; // Xóa danh sách các tag mới sau khi thêm thành công
                return validIds; // Trả về các ID hợp lệ của tác giả mới
            } else {
                console.error("Lỗi từ server khi thêm tác giả:", response.statusText);
                return [];
            }
        } catch (error) {
            console.error("Lỗi khi thêm tác giả:", error);
            return [];
        }
    } else {
        console.log("Không có tác giả mới cần thêm.");
        return [];
    }
}

async function UpdateImgComic(comicId){
    let currentImg = [];
    let api = `http://localhost:8080/api/v1/sng/admin/imgcomic/${comicId}`;
    
    try {
        let urlss = await getData(api);
        if (urlss) {
            currentImg.push(urlss.url1);
            currentImg.push(urlss.url2);
            currentImg.push(urlss.url3);
            currentImg.push(urlss.url4);
            currentImg.push(urlss.url5);
        }
    } catch (e) {
        console.log("Lỗi khi lấy dữ liệu hiện tại:", e);
    }
    
    let urls = [];
    let addData = {};
    let fileInput = document.querySelector('#files-img2');
    
    if (fileInput && fileInput.files && fileInput.files.length === 5) {
        const formData = new FormData();
        
        for (let i = 0; i < fileInput.files.length; i++) {
            formData.append("files", fileInput.files[i]);
        }
        
        try {
            const uploadResponse = await fetch("http://localhost:8080/api/v1/sng/admin/imgcomic/upload", {
                method: 'POST',
                body: formData
            });
            
            if (uploadResponse.ok) {
                urls = await uploadResponse.json();
                
                if (urls.length === 5) {
                    addData = {
                        "url1": urls[0],
                        "url2": urls[1],
                        "url3": urls[2],
                        "url4": urls[3],
                        "url5": urls[4]
                    };
                }
            } else {
                alert("Không thể tải lên hình ảnh");
            }
        } catch (error) {
            console.log("Lỗi khi tải lên hình ảnh:", error);
        }
    } else {
        // Nếu không chọn file mới, giữ lại URL hiện tại
        addData = {
            "url1": currentImg[0],
            "url2": currentImg[1],
            "url3": currentImg[2],
            "url4": currentImg[3],
            "url5": currentImg[4]
        };
    }
    let api_update =`http://localhost:8080/api/v1/sng/admin/imgcomic/update/${comicId}`;
    try {
        const updateOptions = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(addData)
        };
        
        let response = await fetch(api_update, updateOptions);
       
    } catch (error) {
        console.log("Lỗi khi cập nhật dữ liệu:", error);
    }
}

async function search_comic_edit(query) {
    let api_search_comic = `http://localhost:8080/api/v1/sng/admin/comic/search?query=${encodeURIComponent(query)}`;
    fetch(api_search_comic)
    .then(response => {
        // console.log(response.status);
        // if (!response.ok) {
        //     throw new Error('Không tìm thấy kết quả');
        // }
        return response.json(); // Chuyển đổi phản hồi thành JSON
    })
    .then(comics => {
        // Hiển thị danh sách kết quả tìm kiếm
       console.log(comics);


    // Hiển thị count KQTK
    

    // Hiển thị danh sách truyện đã tìm kiếm
    
    const table_list_comic = document.querySelector('.table_edit_body');
    table_list_comic.innerHTML = "";
    
    if (!Array.isArray(comics)) {
        throw new TypeError('Dữ liệu không phải là một mảng');
    }
    comics.forEach(comic => {
       
        const row = document.createElement('tr');
        row.innerHTML = `
        <td class="id_comic_detail">${comic.id}</td>
        <td>${comic.name}</td>
        <td>${comic.description}</td>
        <td>${comic.price}</td>
        <td class="checkbox-delete-comic" style="display: none;" ><input type="checkbox" name="checkbox-delete-comic" id="checkbox-delete-comic"></td>
    `;
    table_list_comic.appendChild(row);
   
    }); 
    
    
        

    })
    .catch(error => {
        console.error('Lỗi:', error);
        
    });
}


// Sự kiện khi click nút tìm kiếm hoặc enter
document.querySelector('.search-frame-edit').addEventListener('keydown', function(event) {
    if (event.key === 'Enter'|| event.keyCode === 13) {
        search_comic_edit(document.querySelector('.inp_edit').value);
        // alert('Bạn đã nhấn Enter!');
    }
});
document.querySelector('.img_edit').addEventListener('click', function(event) {
        search_comic_edit(document.querySelector('.inp_edit').value);
    
});

