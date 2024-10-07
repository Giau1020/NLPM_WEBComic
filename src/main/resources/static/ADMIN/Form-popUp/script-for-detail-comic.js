// Hàm hiển thị tất cả thông tin của truyện theo id
async function getComicById(id) {
    
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
    let response = await fetch(api_get);
    try{
        
        if(response.ok){
            
            const comic = await response.json();
            
            inp_id.innerHTML = comic.id;
            inp_name.innerHTML = comic.name;
            inp_author.innerHTML = comic.author;
            inp_price.innerHTML = comic.price;console.log("Bat dau lay du lieu");
            inp_slb.innerHTML = comic.sold;
            inp_nxb.innerHTML = comic.publisher;
            inp_size.innerHTML = comic.size;
            inp_pages.innerHTML = comic.pages;
            inp_genre.innerHTML = comic.genre;
            inp_content.innerHTML = comic.description;
            inp_summary.innerHTML = comic.summarize ;
            inp_quantity.innerHTML = comic.quantity;
            inp_url.src = comic.url;
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
getComicById("19");