// Hàm hiển thị toàn bộ truyện
let api = "http://localhost:8080/api/v1/sng/admin/comics";
async function showAllComics() {
    // Fetch dữ liệu từ API
    try{
    let comicObj = await fetch(api);
    let comics = await comicObj.json();

    const table_list_comic = document.querySelector('.table_body');
    table_list_comic.innerHTML = "";
    console.log("Hi");
    console.log(comics);
    if (!Array.isArray(comics)) {
        throw new TypeError('Dữ liệu không phải là một mảng');
    }
    comics.forEach(comic => {
        console.log(comic);
        const row = document.createElement('tr');
        row.innerHTML = `
        <td>${comic.id}</td>
        <td>${comic.name}</td>
        <td>${comic.description}</td>
        <td>${comic.price}</td>
        <td><a href="#"><img src="./images-Admin/edit-text.png" alt=""></a></td>
        <td class="checkbox-delete-comic" style="display: none;" ><input type="checkbox" name="checkbox-delete-comic" id="checkbox-delete-comic"></td>
    `;
    table_list_comic.appendChild(row);
        
    });}
    catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }


    
}
document.addEventListener('DOMContentLoaded', showAllComics);
showAllComics();


// Hàm hiển thị toàn bộ truyện

async function showAllComicsEditTable() {
    // Fetch dữ liệu từ API
    try{
    let comicObj = await fetch(api);
    let comics = await comicObj.json();

    const table_list_comic = document.querySelector('.table_edit_body');
    table_list_comic.innerHTML = "";
    console.log("Hi");
    console.log(comics);
    if (!Array.isArray(comics)) {
        throw new TypeError('Dữ liệu không phải là một mảng');
    }
    comics.forEach(comic => {
        console.log(comic);
        const row = document.createElement('tr');
        row.innerHTML = `
        <td>${comic.id}</td>
        <td>${comic.name}</td>
        <td>${comic.description}</td>
        <td>${comic.price}</td>
        
       
    `;
    table_list_comic.appendChild(row);
        
    });}
    catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }

}
document.addEventListener('DOMContentLoaded', showAllComicsEditTable);
showAllComicsEditTable();

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
            console.log(comic);
            inp_id.value = comic.id;
            inp_name.value = comic.name;
            inp_author.value = comic.author;
            inp_price.value = comic.price;console.log("Bat dau lay du lieu");
            inp_slb.value = comic.slb;
            inp_nxb.value = comic.nxb;
            inp_size.value = comic.size;
            inp_pages.value = comic.pages;
            inp_genre.textContent = comic.genre;
            inp_content.value = comic.description;
            inp_summary.value = comic.summarize ;
            inp_url.style.display = "block";
            inp_url.src = comic.url;
            inp_weight.value = comic.weight;



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
    const addAPI  = "http://localhost:8080/api/v1/sng/admin";
    let id = document.querySelector('#inp-id-comic').value;
    let name = document.querySelector('#inp-name-comic').value;
    let author = document.querySelector('#name-author').value;
    let price = document.querySelector('#price-comic').value;
    let slb = document.querySelector('#inp-slb').value;
    let nxb = document.querySelector('#NXB').value;
    let size = document.querySelector('#inp-size').value;
    let pages = document.querySelector('#inp-pages').value;
    let genre = document.querySelector('#inp-genre').value;
    let content = document.querySelector('#content-comic').value;
    let summary = document.querySelector('#summary_content-comic').value;
    // let url = document.querySelector('#files-img').value;
    let weight = document.querySelector('#inp-weight').value;
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
            await sendComicData();
        } else {
            alert("Không thể tải lên hình ảnh");
        }
    } else {
        alert("Vui lòng chọn hình ảnh");
    }

    async function sendComicData() {
        const addData = {
            "name": name,
            "price": price,
            "url": url, // Sử dụng URL đã nhận
            "slb": slb,
            "nxb": nxb,
            "size": size,
            "description": content,
            "pages": pages,
            "author": author,
            "weight": weight,
            "genre": genre,
            "summerize": summary
        };

        const updateOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(addData)
        };

        let add = await fetch(addAPI, updateOptions);
        addImgComic();
        showAllComics();
        if (add.ok) {
            alert(`Thêm truyện thành công!`);
            showAllComics()
        } else {
            alert("Không thể thêm truyện");
        }
    }
}

// Hàm gửi 5 url lên server và lưu 5 url vào csdl
async function addImgComic() {
    let addAPI = "http://localhost:8080/api/v1/sng/admin/imgcomic/add";
    let fileInput  = document.querySelector('#files-img');
    let urls = [];
    if (fileInput.files.length > 0) {
        

        // Tạo FormData để gửi file
        const formData = new FormData();
        for (let i = 0; i < fileInput.files.length; i++) {
            formData.append("files", fileInput.files[i]); 
        }
        console.log("formdata");
        

        // Gửi file lên server
        const uploadResponse = await fetch("http://localhost:8080/api/v1/sng/admin/imgcomic/upload", {
            method: 'POST',
            body: formData
        });
        if (uploadResponse.ok) {
            urls = await uploadResponse.json(); // Nhận URL của file
            // Gửi dữ liệu comic sau khi có URL
            await sendImgComicData();
        } else {
            alert("Không thể tải lên hình ảnh");
        }
    } else {
        alert("Vui lòng chọn hình ảnh");
    }
    
    async function sendImgComicData() {
        const addData = {
            "comic_id": '1',
            "url1": `${urls[0]}`,
            "url2": `${urls[1]}`,
            "url3": `${urls[2]}`,
            "url4": `${urls[3]}`,
            "url5": `${urls[4]}`
        };

        const updateOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(addData)
        };

        let add = await fetch(addAPI, updateOptions);
        
        if (add.ok) {
            alert(`Thêm truyện thành công!!!!`);
            
        } else {
            alert("Không thể thêm truyện");
        }
    }
    
}