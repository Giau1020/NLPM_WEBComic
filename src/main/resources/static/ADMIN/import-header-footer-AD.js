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


  function loadCSSFile(url){
        // Tạo thẻ <link> mới
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = `${url}`; // Đường dẫn tới file CSS cần thêm

        // Thêm thẻ <link> vào trong <head>
        document.head.appendChild(link);

        // Bạn có thể kiểm tra xem thẻ link đã được thêm vào hay chưa
        console.log('CSS mới đã được thêm!');
    
  }