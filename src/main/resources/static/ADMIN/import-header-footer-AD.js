function loadHTMLFile(url, elementId) {
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            document.getElementById(elementId).innerHTML = data;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
  }
  
  // Gọi hàm để tải header và footer
  loadHTMLFile('./header-admin.html', 'header-admin-page');
  loadHTMLFile('./footer-admin.html', 'footer-admin-page');
  



  function loadCSSFile(url, classname){
        // Tạo thẻ <link> mới
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = `${url}`; // Đường dẫn tới file CSS cần thêm
        link.className = `${classname}`;
        document.head.appendChild(link);
        console.log('CSS mới đã được thêm!');
    
  }


// HÀM LOAD SCRIPT
    function loadScriptFile(url, classname, callback) {
        const script = document.createElement('script'); 
        script.src = `${url}`; 
        script.className = `${classname}`; 
        script.async = true; 

        // Khi script đã được tải xong
        script.onload = () => {
            console.log('Script mới đã được thêm và đã tải xong!'); 
            if (callback) callback(); 
        };

        // Nếu có lỗi khi tải script
        script.onerror = () => {
            console.error('Có lỗi khi tải script!'); 
        };

        // Thêm thẻ <script> vào trong <body> hoặc <head>
        document.body.appendChild(script);

        // Bạn có thể kiểm tra xem thẻ script đã được thêm vào hay chưa
        console.log('Script mới đã được thêm!');
    }

// Hàm remove element

  function removeElement(classname){
    let element = document.querySelector(`${classname}`);
    element.remove();
  }
//  Hàm fetch form pop up detail vào khi nhấn vào xem chi tiết trongt trang admin




  function Show_Detail_Comic(comic_id){
    let element = document.querySelector('.container-detail-backround');
    element.style.display = 'block';
    element.style.position = 'fixed';
    element.style.top= 0;
    element.style.left= 0;


    //loadHTMLFile('./Form-detail-comic.html', 'Form-detail-comic');
    loadCSSFile('./Form-popUp/Style-detail-comic.css', 'style-for-detail-comic');

    loadScriptFile('./Form-popUp/script-for-detail-comic.js', 'scrip-for-detail-comic', function() {
        console.log('Hàm callback đã được gọi sau khi script tải xong!');
        getComicById_Detail(comic_id); 
    });
    
    // Sự kiện click của button close
    let btn_close = document.querySelector('.close-btn');
    btn_close.addEventListener('click', function(){
        console.log("btn-close-click");
        // if (document.querySelector('.style-for-detail-comic')) {
        //     removeElement('.style-for-detail-comic');
        // }

        // if (document.querySelector('.scrip-for-detail-comic')) {
        //     removeElement('.scrip-for-detail-comic');
        // }
        element.style.display = 'none';
    })
    document.querySelector('.btn-detail-comic').addEventListener("click", function() {
        
    });
}


