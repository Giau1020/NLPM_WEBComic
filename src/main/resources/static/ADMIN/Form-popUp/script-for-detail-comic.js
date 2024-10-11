// Hàm hiển thị tất cả thông tin của truyện theo id
async function getComicById_Detail(id) {
    console.log('id đang đc load là:  ' +id);
    let api_get = `http://localhost:8080/api/v1/sng/admin/${id}`;
    let inp_id = document.querySelector('.id');
    let inp_name = document.querySelector('.name');
    let inp_author = document.querySelector('.author');
    let inp_price = document.querySelector('.price');
    let inp_slb = document.querySelector('.sold');
    let inp_nxb = document.querySelector('.nxb');
    let inp_size = document.querySelector('.size');
    let inp_pages = document.querySelector('.pages');
    let inp_genre = document.querySelector('.genre');
    let inp_content = document.querySelector('.content');
    let inp_summary = document.querySelector('.summary-content');
    let inp_url = document.querySelector('.img-cover');
    let inp_weight = document.querySelector('.weight');
    let inp_quantity = document.querySelector('.quantity');


    // Lấy danh sách thể loại theo id truyện
    let api_get_genre = `http://localhost:8080/api/v1/sng/admin/${id}/genres`;
    let response_genre = await fetch(api_get_genre);

    try{
        if(response_genre.ok){
            inp_genre.innerHTML = '';
            const genres = await response_genre.json();
            genres.forEach((genre, index) => {
                inp_genre.innerHTML += genre.name;
                // Nếu không phải là phần tử cuối cùng, thêm dấu phẩy
                if (index === genres.length - 1) {
                    inp_genre.innerHTML += ".";
                } else {
                    inp_genre.innerHTML += ", ";
                }
            });
            
        }
        else {
            console.error('Lỗi khi gọi API:', response_get_5url.statusText);
        }
    }catch(error){
        console.error('Lỗi khi fetch dữ liệu:', error);
     }
    // Lấy danh sách tà giả theo id truyện
    let api_get_author = `http://localhost:8080/api/v1/sng/admin/${id}/authors`;
    let response_author = await fetch(api_get_author);

    try{
        if(response_author.ok){
            inp_author.innerHTML = '';
            const authors = await response_author.json();
            authors.forEach((author,index) => {
                inp_author.innerHTML += author.name;
                if (index === authors.length - 1) {
                    inp_author.innerHTML += ".";
                } else {
                    inp_author.innerHTML += ", ";
                }
            });
        }else {
            console.error('Lỗi khi gọi API:', response_author.statusText);
        }
     }
     catch(error){
        console.error('Lỗi khi fetch dữ liệu:', error);
     
    }
    
    // Lấy danh sách 5url và hiện ra màn hình
    let imageListDiv = document.querySelector('.imgs-comic');
    let api_get_5url = `http://localhost:8080/api/v1/sng/admin/imgcomic/${id}`;
    let response_get_5url = await fetch(api_get_5url);
    
     try{
        if(response_get_5url.ok){
            imageListDiv.innerHTML = '';
            const comic_img = await response_get_5url.json();
            console.log("5url này là của: " + `${id}`);
            const urls = [comic_img.url1, comic_img.url2, comic_img.url3, comic_img.url4, comic_img.url5];
            urls.forEach(url => {
                const imgElement = document.createElement('img');
                imgElement.src ='../'+ url;
                imgElement.alt = 'images';
                imgElement.style.width = '200px';
                imgElement.style.height = 'auto'; 
                imgElement.style.display = 'block';
                imgElement.style.marginBottom = '10px';
                imgElement.style.marginLeft = '10px';
                imageListDiv.appendChild(imgElement);
            });
        } else {
            console.error('Lỗi khi gọi API:', response_get_5url.statusText);
        }
     }
     catch(error){
        console.error('Lỗi khi fetch dữ liệu:', error);
     }



    let response = await fetch(api_get);
    try{
        
        if(response.ok){
            
            const comic = await response.json();
            
            inp_id.innerHTML = comic.id;
            inp_name.innerHTML = comic.name;
           // inp_author.innerHTML = comic.author;
            inp_price.innerHTML = comic.price;console.log("Bat dau lay du lieu");
            inp_slb.innerHTML = comic.sold;
            inp_nxb.innerHTML = comic.publisher;
            inp_size.innerHTML = comic.size;
            inp_pages.innerHTML = comic.pages;
            // inp_genre.innerHTML = comic.genre;
            inp_content.innerHTML = comic.description;
            inp_summary.innerHTML = comic.summarize ;
            inp_quantity.innerHTML = comic.quantity;
            inp_url.src = '../'+comic.url;
            inp_weight.innerHTML = comic.weight;



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
