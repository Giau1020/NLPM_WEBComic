// Hàm kiểm tra trạng thái đăng nhập
function checkLoginStatus() {
    fetch('/user/is-logged-in', { credentials: 'include' })
        .then(response => response.json())
        .then(isLoggedIn => {
            const authLinks = document.getElementById("authLinks");
            const loggedInUser = document.getElementById("loggedInUser");
          
            if (isLoggedIn) {
                if (authLinks && loggedInUser) {
                 
                    authLinks.style.display = "none";
                    loggedInUser.style.display = "block";
                  
                    // Lấy thông tin người dùng
                    fetch('/user/info', { credentials: 'include' })
                        .then(response => response.json())
                        .then(userInfo => {
                            const usernameDisplay = document.getElementsByClassName("usernameDisplay")[0];
                            if (usernameDisplay) {
                                usernameDisplay.textContent = userInfo.username;

                                // Xử lý menu người dùng khi nhấn vào tên
                                usernameDisplay.addEventListener("click", function(event) {
                                    event.stopPropagation();
                                    const userMenu = document.getElementById("userMenu");
                                    if (userMenu) {
                                        userMenu.style.display = (userMenu.style.display === "none" || userMenu.style.display === "") ? "block" : "none";
                                    }
                                });

                                // Ẩn menu khi nhấn ra ngoài
                                window.addEventListener("click", function(event) {
                                    const userMenu = document.getElementById("userMenu");
                                    if (userMenu && !usernameDisplay.contains(event.target) && !userMenu.contains(event.target)) {
                                        userMenu.style.display = "none";
                                    }
                                });
                            }

                        const test=document.getElementsByClassName("usernameDisplay")[1];
                        if (test) {
                            

                            // Xử lý menu người dùng khi nhấn vào tên
                            test.addEventListener("click", function(event) {
                                event.stopPropagation();
                                const userMenu = document.getElementById("userMenu");
                                if (userMenu) {
                                    userMenu.style.display = (userMenu.style.display === "none" || userMenu.style.display === "") ? "block" : "none";
                                }
                            });

                            // Ẩn menu khi nhấn ra ngoài
                            window.addEventListener("click", function(event) {
                                const userMenu = document.getElementById("userMenu");
                                if (userMenu && !usernameDisplay.contains(event.target) && !userMenu.contains(event.target)) {
                                    userMenu.style.display = "none";
                                }
                            });
                        }
                        })
                        .catch(error => console.error('Error fetching user info:', error));
                }
            } else {
                // Hiển thị liên kết Đăng nhập/Đăng ký nếu chưa đăng nhập
                if (authLinks && loggedInUser) {
                    authLinks.style.display = "block";
                    loggedInUser.style.display = "none";
                }
            }
        })
        .catch(error => console.error('Error checking login status:', error));

    // Xử lý khi người dùng nhấn Đăng xuất
    const logoutLink = document.getElementById("logoutLink");
    if (logoutLink) {
        logoutLink.addEventListener("click", function(event) {
            event.preventDefault();
            fetch('/user/logout', { method: 'POST', credentials: 'include' })
                .then(() => {
                    window.location.href = 'TrangChu.html';
                })
                .catch(error => console.error('Error logging out:', error));
        });
    }
}

// // Hàm gán sự kiện cho nút tìm kiếm
// function setupSearchEvent() {
//     const searchButton = document.getElementById("btsearch");
//     if (searchButton) {
//         searchButton.addEventListener("click", function() {
//             var query = document.getElementById("khungsearch").value;
//             if (query) {
//                 window.location.href = "Search.html?query=" + encodeURIComponent(query);
//             } else {
//                 alert("Vui lòng nhập từ khóa tìm kiếm!");
//             }
//         });
//     } else {
//         console.error("Phần tử #btsearch không tồn tại.");
//     }
// }
// Hàm gán sự kiện cho nút tìm kiếm
function setupSearchEvent() {
    const searchButton = document.getElementById("btsearch");
    const searchInput = document.getElementById("khungsearch");

    // Gán giá trị từ URL vào khung tìm kiếm khi trang tải
    const urlParams = new URLSearchParams(window.location.search);
    const query = urlParams.get('query');
    if (query) {
        searchInput.value = query; // Gán lại giá trị vào khung tìm kiếm
    }

    if (searchButton && searchInput) {
        // Sự kiện cho nút click
        searchButton.addEventListener("click", function() {
            performSearch();
        });

        // Sự kiện khi nhấn phím Enter
        searchInput.addEventListener("keydown", function(event) {
            if (event.key === "Enter") {
                performSearch();
            }
        });

    } else {
        console.error("Phần tử #btsearch hoặc #khungsearch không tồn tại.");
    }
}

// Hàm thực hiện tìm kiếm
function performSearch() {
    var query = document.getElementById("khungsearch").value.trim(); // Loại bỏ khoảng trắng
    if (query) {
        window.location.href = "Search.html?query=" + encodeURIComponent(query);
    } else {
        alert("Vui lòng nhập từ khóa tìm kiếm!");
    }
}

document.addEventListener("DOMContentLoaded", function() {
    // Tải header
    fetch('header.html')
        .then(response => response.text())
        .then(data => {
            const headerElement = document.getElementById('header');
            if (headerElement) {
                headerElement.innerHTML = data;

                // Sau khi header được tải, kiểm tra trạng thái đăng nhập và gán sự kiện tìm kiếm
                checkLoginStatus();
                setupSearchEvent();

                const categoryMenuToggle = document.getElementById("danhmuc");
                const categoryMenu = document.getElementById("categoryMenu");
                const genreList = document.getElementById("genreList");
            
                // Hiển thị hoặc ẩn menu khi nhấn vào "Danh mục sản phẩm"
                categoryMenuToggle.addEventListener("click", function(event) {
                    event.stopPropagation();
                    categoryMenu.style.display = (categoryMenu.style.display === "none" || categoryMenu.style.display === "") ? "block" : "none";
            
                    // Chỉ gọi API và thêm thể loại khi lần đầu nhấn vào danh mục sản phẩm
                    if (genreList.children.length === 0) { // Kiểm tra nếu danh sách chưa có thể loại
                        fetch("http://localhost:8080/comics/genres")
                            .then(response => response.json())
                            .then(data => {
                                // Xóa các thể loại cũ (nếu có)
                                genreList.innerHTML = '';
                                // Duyệt qua các thể loại và thêm các thẻ <a> vào danh sách
                                data.forEach(genre => {
                                    const listItem = document.createElement("li");
                                    const link = document.createElement("a");
                                    
                                    link.textContent = `Truyện ${genre.name}`;
                                    link.href = `httl.html?genreId=${genre.id}`;
                                    listItem.appendChild(link);
                                    genreList.appendChild(listItem);
                                });
                            })
                            .catch(error => console.error('Error fetching genres:', error));
                    }
                });
            
                // Ẩn menu khi nhấn ra ngoài
                window.addEventListener("click", function(event) {
                    if (!categoryMenuToggle.contains(event.target) && !categoryMenu.contains(event.target)) {
                        categoryMenu.style.display = "none";
                    }
                });
            } else {
                console.error('Không tìm thấy phần tử #header');
            }
        })
        .catch(error => console.error('Error loading header:', error));
        
        fetch('footer.html')
        .then(response => response.text())
        .then(data => {
            const footerElement = document.getElementById('footer');
            if (footerElement) {
                footerElement.innerHTML = data;
            } else {
                console.error('Không tìm thấy phần tử #footer');
            }
        })
        .catch(error => console.error('Error loading footer:', error));
});


