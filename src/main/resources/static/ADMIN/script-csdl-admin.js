// Hàm hiển thị toàn bộ truyện
let api = "http://localhost:8080/api/v1/sng/admin/comics";
async function showAllComics() {
    // Fetch dữ liệu từ API
    console.log("showAllComics called");
    try{
    let comicObj = await fetch(api);
    let comics = await comicObj.json();

    const table_list_comic = document.querySelector('.table_body');
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
        <td><img style="width: 30px; height: 30px;"  class="btn-detail-comic" src="./images-Admin/edit-text.png" alt=""></td>
         <td class="checkbox-delete-comic-main"  ><input type="checkbox" name="checkbox-delete-comicc" class="checkbox-delete-comicc"></td>
    `;
    table_list_comic.appendChild(row);
   
  

    });  updateCheckbox();
    
    toggleColumnVisibility(5, false);
        setupDetailComicButtons();
       
       
       
}
    catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }


    
}


// document.addEventListener('DOMContentLoaded', function(){
    showAllComics();
 
    // showDetailComicButtons.js

   
// });

async function toggleColumnVisibility(index, shouldShow) {
    const rows = document.querySelectorAll('.table_body tr');
    rows.forEach(row => {
        const cell = row.cells[index];
        cell.style.setProperty('display', shouldShow ? 'table-cell' : 'none', 'important');
          //  console.log(shouldShow ? "table-cell" : "none", cell); // Debugging output

       
    });
    return;
}
function setupDetailComicButtons() {
    const buttonsDetail = document.querySelectorAll('.btn-detail-comic');

    buttonsDetail.forEach((button, index) => {
        button.addEventListener('click', function() {
            const paragraphs = document.querySelectorAll('.id_comic_detail');

            if (index < paragraphs.length) {
                const innerText = paragraphs[index].innerText;
                Show_Detail_Comic(innerText);
            } else {
                console.error('Index không hợp lệ:', index);
            }
        });
    });
}



// Hàm hiển thị toàn bộ truyện

async function showAllComicsEditTable() {
    // Fetch dữ liệu từ API
    let api_get = "http://localhost:8080/api/v1/sng/admin/comics";
    try{
    let comicObj = await fetch(api);
    let comics = await comicObj.json();

    const table_list_comic = document.querySelector('.table_edit_body');
    table_list_comic.innerHTML = "";
   
    if (!Array.isArray(comics)) {
        throw new TypeError('Dữ liệu không phải là một mảng');
    }
    comics.forEach(comic => {
        
        const row = document.createElement('tr');
        row.innerHTML = `
        <td>${comic.id}</td>
        <td>${comic.name}</td>
       
        <td>${comic.price}</td>
        
       
    `;
    table_list_comic.appendChild(row);
        
    });
    


    }
    catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }

}


// hàm lấy dữ liệu từ CSDL
async function getData(url) {
    try {
      const response = await fetch(url); 
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json(); // Chờ dữ liệu được chuyển đổi thành JSON
      return data; // Trả về dữ liệu JSON
    } catch (error) {
      console.error('Error fetching data:', error);
      throw error; // Ném lỗi để xử lý ở nơi gọi hàm
    }
  }

// document.addEventListener('DOMContentLoaded', showAllComicsEditTable);
// showAllComicsEditTable();

// Hàm lấy dữ liệu của 1 truyện bằng id
async function getComicById(id) {
    
    let api_get = `http://localhost:8080/api/v1/sng/admin/${id}`;
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
    let response = await fetch(api_get);
    try{
        
        if(response.ok){
            
            const comic = await response.json();
            
            inp_id.value = comic.id;
            inp_name.value = comic.name;
            // inp_author.value = comic.author;
            inp_price.value = comic.price;console.log("Bat dau lay du lieu");
            inp_slb.value = comic.quantity;
            inp_nxb.value = comic.publisher;
            inp_size.value = comic.size;
            inp_pages.value = comic.pages;
            // inp_genre.textContent = comic.genre;
            inp_content.value = comic.description;
            inp_summary.value = comic.summarize ;
            inp_url.style.display = "block";
            //inp_url.src = comic.url;
            inp_weight.value = comic.weight;
            
            // Hiển thị tác giả
            let api_get_all_author = "http://localhost:8080/api/v1/sng/admin/authors";
            let api_get_author_selected = `http://localhost:8080/api/v1/sng/admin/${id}/authors`;
            
            let data_all_authors =await getData(api_get_all_author);
            let data_author_selected =await getData(api_get_author_selected);

            if(data_all_authors){
                let selectedAuthorNames = [];
                document.getElementById('name-author2').innerHTML = '';

                data_all_authors.forEach(author => {
                    let option = document.createElement('option');
                    option.value = author.id;
                    option.textContent = author.name;

                    if(data_author_selected && data_author_selected.map(a => a.id).includes(author.id)){
                        option.selected = true;
                        selectedAuthorNames.push(author.name); 
                    }
                    inp_author.appendChild(option);
                });
                $('#name-author2').select2({
                    placeholder: selectedAuthorNames.join(', '), // Placeholder là danh sách tác giả đã chọn
                    allowClear: true,
                    tags: true
                });
            }
            
            
           

           
            //Hiển thị thể loại
            let api_get_all_genre = "http://localhost:8080/api/v1/sng/admin/genres";
            let api_get_genre_selected = `http://localhost:8080/api/v1/sng/admin/${id}/genres`;

            let data_all_genres = await getData(api_get_all_genre);
            let data_genre_selected = await getData(api_get_genre_selected);

            if (data_all_genres) {
                let selectedGenreNames = [];
                document.getElementById('inp-genre2').innerHTML = '';

                data_all_genres.forEach(genre => {
                    let option = document.createElement('option');
                    option.value = genre.id;
                    option.textContent = genre.name;

                    if (data_genre_selected && data_genre_selected.map(g => g.id).includes(genre.id)) {
                        option.selected = true;
                        selectedGenreNames.push(genre.name);
                    }
                    inp_genre.appendChild(option);
                });

                $('#inp-genre2').select2({
                    placeholder: selectedGenreNames.join(', '), // Placeholder là danh sách thể loại đã chọn
                    allowClear: true,
                    tags: false
                });
            }


            // Hiển thị ảnh bìa
            inp_url.src = comic.url;
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

            // Lấy 5 url của comic
           
            let api_get_5_url = `http://localhost:8080/api/v1/sng/admin/imgcomic/${comic.id}`;
            let img = [];
            let imgs = await getData(api_get_5_url);
            if(imgs){
                if (imgs.url1) img.push(imgs.url1);
                if (imgs.url2) img.push(imgs.url2);
                if (imgs.url3) img.push(imgs.url3);
                if (imgs.url4) img.push(imgs.url4);
                if (imgs.url5) img.push(imgs.url5);

                
            }
            let previewDiv = document.getElementById('preview2');
            previewDiv.innerHTML = '';  // Xóa nội dung cũ (nếu có) trước khi thêm ảnh mới

            img.forEach(url => {
                previewDiv.innerHTML = '';
                let imgElement = document.createElement('img'); // Tạo thẻ <img>
                imgElement.src = url; // Gán URL cho thuộc tính src của <img>
                imgElement.style.maxWidth = "200px"; // Giới hạn kích thước hiển thị của hình ảnh (có thể thay đổi tùy ý)
                imgElement.style.margin = "10px"; // Tạo khoảng cách giữa các hình ảnh

                previewDiv.appendChild(imgElement); // Thêm <img> vào div#preview
            });

            // Hiển thị 5 url của comic lên div#preview2
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
            
                            previewDiv.appendChild(imgElement); // Thêm thẻ <img> vào div#preview2
                        }
            
                        // Đọc file dưới dạng URL
                        reader.readAsDataURL(file);
                    });
                }
            });

          

            // Ví dụ gọi hàm
            const updatedComicData = {
                name: document.querySelector('#inp-name-comic2').value,
                price: document.querySelector('#price-comic2').value,
                url: document.querySelector('#cover-img2').value,
                sold: comic.sold,
                quantity: document.querySelector('#inp-slb2').value,
                Weight: document.querySelector('#inp-weight2').value,
                Description: document.querySelector('#content-comic2').value,
                Pages: document.querySelector('#inp-pages2').value,
                Size: document.querySelector('#inp-size2').value,
                Publisher:  document.querySelector('#NXB2').value,
                Summarize: document.querySelector('#summary_content-comic2').value,
                authors: [1, 2], // ID của các tác giả mới
                genres: [3, 4], // ID của các thể loại mới
            };
            document.querySelector('.btn-save-edit').addEventListener('click', function() {
                updateComic(comic.id, updatedComicData);
            });
            
            
            
            
            



        }

    }catch(error){
        if (error.response && error.response.status === 404) {
            console.log("Comic not found");
            return null; // Trả về null hoặc xử lý theo ý bạn
        } else {
            console.error("Error fetching comic:", error);
            return null; // Hoặc xử lý lỗi tùy ý
        }
    }
}




// Hàm thêm 1 truyện mới vào
async function addComic(){
  //  await sendAuthorDatatoAuthor();
    const addAPI  = "http://localhost:8080/api/v1/sng/admin/addNewComic";
    let name = document.querySelector('#inp-name-comic').value;
    let price = parseFloat(document.querySelector('#price-comic').value); 
    let slb = parseInt(document.querySelector('#inp-slb').value); 
    //let urls = document.querySelector('#cover-url').value; 
    let Weight = document.querySelector('#inp-weight').value; 
    let Description = document.querySelector('#content-comic').value; 
    let Pages = document.querySelector('#inp-pages').value; 
    let Size = document.querySelector('#inp-size').value; 
    let Publisher = document.querySelector('#NXB').value; 
    let Summarize = document.querySelector('#summary_content-comic').value; 
    console.log("chỗ này thêm truyện èn")

    let fileInput  = document.querySelector('#files-cover-img');
    let url = '';
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
            url = await uploadResponse.text(); // Nhận URL của file
            // Gửi dữ liệu comic sau khi có URL
           // await sendAuthorDatatoAuthor();
            let idofauthor =await getIdAuthors('#name-author');
           let comiccId = await sendComicData(idofauthor);
        if (comiccId) {
            await addImgComic(comiccId); // Chỉ gọi hàm thêm ảnh nếu comicId hợp lệ
        } else {
            console.error("Không thể lấy ID của comic để thêm hình ảnh");
        }
           
        } else {
            alert("Không thể tải lên hình ảnh");
        }
    } else {
        alert("Vui lòng chọn hình ảnh");
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
    
    
    async function getIdAuthors(id) {
        let selectedAuthors = $(id).select2('data');
        let authorIds = selectedAuthors.map(author => parseInt(author.id));

        const validAuthorIds = await sendAuthorDatatoAuthor();
        

        // Gộp 2 mảng authorIds và validAuthorIds
        let mergedAuthorIds = authorIds.concat(validAuthorIds).filter(id => !Number.isNaN(id));
        console.log("id tác giả mới đc return lại là: ", mergedAuthorIds);
      return mergedAuthorIds; // Trả về mảng ID hợp lệ
    }
    async function sendComicData(authorIds) {


    if (!name || name.trim() === "") {
        alert("Tên truyện không được để trống!");
        return;
    }
        let selectedGenres = $('#inp-genre').select2('data'); 
        let genreIds = selectedGenres.map(genre => parseInt(genre.id));
        const addData = {
            "name": name,
            "price": price,
            "url": url, // URL của ảnh sau khi upload
            "sold": 0, 
            "quantity": slb,
            "weight": Weight, 
            "description": Description, 
            "pages": Pages, 
            "size": Size, 
            "publisher": Publisher, //
            "summarize": Summarize,
            "authorIds": authorIds,
            "genreIds": genreIds 
        };
     //   console.log("Selected authors:", authorIds);
      //  console.log("Selected genres:", genreIds);
        
        
        
      //  console.log("Dữ liệu gửi lên server:", addData);
        const updateOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(addData)
        };

        let add = await fetch(addAPI, updateOptions);
        if (add.ok) {
            alert(`Thêm truyện thành công!`);
            const comicc = await add.json(); // Lấy phản hồi từ server, chứa thông tin của comic vừa tạo
         const comiccId = comicc.id; 
            showAllComics();
            let e = document.querySelector('#form-add-new-comic');
            e.style.display = "none";
         //   console.log("nó rì tun nè");
            return comiccId;

        } else {
            let errorText = await add.text(); // Lấy chi tiết lỗi từ server
            console.error("Không thể thêm truyện: " + errorText);
            let e = document.querySelector('#form-add-new-comic');
            e.style.display = "none";
        }
    }
}





// Hàm gửi 5 url lên server và lưu 5 url vào csdl
async function addImgComic(comiccId) {
    let addImgAPI = "http://localhost:8080/api/v1/sng/admin/imgcomic/add";
    let fileInput  = document.querySelector('#files-img');
    let urls = [];
    if (fileInput.files.length===5 ) {
        

        // Tạo FormData để gửi file
        const formData = new FormData();
        for (let i = 0; i < fileInput.files.length; i++) {
            formData.append("files", fileInput.files[i]); 
        }
      //  console.log("formdata");
        

        // Gửi file lên server
        const uploadResponse = await fetch("http://localhost:8080/api/v1/sng/admin/imgcomic/upload", {
            method: 'POST',
            body: formData
        });
        if (uploadResponse.ok) {
            urls = await uploadResponse.json(); // Nhận URL của file
            // Gửi dữ liệu comic sau khi có URL
            
            await sendImgComicData(comiccId);
        } else {
            alert("Không thể tải lên hình ảnh");
        }
    } else {
        alert("Vui lòng chọn 5 hình ảnh");
    }
    
    async function sendImgComicData(comiccId) {
        
        const addData = {
            
            "comicId": comiccId,
            "url1": `${urls[0]}`,
            "url2": `${urls[1]}`,
            "url3": `${urls[2]}`,
            "url4": `${urls[3]}`,
            "url5": `${urls[4]}`
        };
        console.log("id cua comic", comiccId);
        const updateOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(addData)
        };

        let add = await fetch(addImgAPI, updateOptions);
        
        if (add.ok) {
            alert(`Tải ảnh lên thành công`);
            
        } else {
            alert("Không thể tải ảnh lên");
        }
    
}
}


// Tìm kiếm truyện
async function search_comic(query) {
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
      // console.log(comics);


    // Hiển thị count KQTK
    
    document.querySelector('.KQTK').style.display = "block";
    document.querySelector('.count_search').style.display = "block";
    document.querySelector('.close_search').style.display = 'block';
    if(comics.length === 0){
        document.querySelector('.count_search').innerText= '0';
    }
    else document.querySelector('.count_search').innerHTML = comics.length;

    // Hiển thị danh sách truyện đã tìm kiếm
    
    const table_list_comic = document.querySelector('.table_body');
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
        <td><img style="width: 30px; height: 30px;"  class="btn-detail-comic" src="./images-Admin/edit-text.png" alt=""></td>
        <td class="checkbox-delete-comic" style="display: none;" ><input type="checkbox" name="checkbox-delete-comic" id="checkbox-delete-comic"></td>
    `;
    table_list_comic.appendChild(row);
   
    }); setupDetailComicButtons();
    
    document.querySelector('.close_search').addEventListener('click', function() {
       close_edit_mode();
    });
        

    })
    .catch(error => {
        console.error('Lỗi:', error);
        
    });
}


// Sự kiện khi click nút tìm kiếm hoặc enter
document.querySelector('.search-frame-inp').addEventListener('keydown', function(event) {
    if (event.key === 'Enter'|| event.keyCode === 13) {
        search_comic(document.querySelector('.search-frame-inp').value);
        // alert('Bạn đã nhấn Enter!');
    }
});
document.querySelector('.header-btn-search').addEventListener('click', function(event) {
        search_comic(document.querySelector('.search-frame-inp').value);
    
});// Sự kiện khi click nút tìm kiếm hoặc enter


function close_edit_mode(){
    document.querySelector('.KQTK').style.display = 'none';
        document.querySelector('.count_search').style.display = 'none';
        document.querySelector('.close_search').style.display = 'none';
        document.querySelector('.search-frame-inp').disabled = true;
        document.querySelector('.search-frame-inp').value ='';
        document.querySelector('.search-frame-inp').placeholder =" ";

        showAllComics();
}


// Hiển thị toàn bộ account
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
async function getAllInforUser() {
    let api = `http://localhost:8080/api/v1/sng/admin/users`;
    getDataFromAPI(api)
    .then(users => {
        let element = document.querySelector('.tbody_manage_account');
        element.innerHTML = ``; // Clear only once before the loop

        users.forEach(user => {
            let status  = user.status;
            let TT;
            let email = user.email;
            if(user.email === null){
                email = "Chưa cập nhật";
            }
            if(status == true){
                TT = 'Đang hoạt động';
            }else if(status == false){
                TT = 'Tài khoản đã bị khóa';
            }
            let row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.username}</td>
                <td>${email}</td>
                <td>${TT}</td>

            `;
            element.appendChild(row);
        });
    })
    .catch(error => {
        console.log(error);
    });
}
