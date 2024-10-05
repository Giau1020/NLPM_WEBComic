// Hàm kiểm tra trạng thái đăng nhập
function checkLoginStatus() {
    // Kiểm tra trạng thái đăng nhập từ server
    fetch('/user/is-logged-in', { credentials: 'include' })
        .then(response => response.json())
        .then(isLoggedIn => {
            if (isLoggedIn) {
                // Ẩn liên kết Đăng nhập/Đăng ký và hiển thị phần thông tin người dùng
                const authLinks = document.getElementById("authLinks");
                const loggedInUser = document.getElementById("loggedInUser");

                if (authLinks && loggedInUser) {
                    authLinks.style.display = "none";
                    loggedInUser.style.display = "block";

                    // Lấy thông tin người dùng
                    fetch('/user/info', { credentials: 'include' })
                        .then(response => response.json())
                        .then(userInfo => {
                            const usernameDisplay = document.getElementById("usernameDisplay");
                            if (usernameDisplay) {
                                usernameDisplay.textContent = userInfo.username;

                                // Xử lý menu người dùng khi nhấn vào tên
                                usernameDisplay.addEventListener("click", function(event) {
                                    event.stopPropagation();
                                    const userMenu = document.getElementById("userMenu");
                                    if (userMenu) {
                                        if (userMenu.style.display === "none" || userMenu.style.display === "") {
                                            userMenu.style.display = "block";
                                        } else {
                                            userMenu.style.display = "none";
                                        }
                                    }
                                });

                                // Ẩn menu khi nhấn ra ngoài
                                window.addEventListener("click", function(event) {
                                    const userMenu = document.getElementById("userMenu");
                                    if (userMenu && !usernameDisplay.contains(event.target) && !userMenu.contains(event.target)) {
                                        userMenu.style.display = "none"; // Ẩn menu
                                    }
                                });
                            }
                        })
                        .catch(error => console.error('Error fetching user info:', error));
                }
            } else {
                // Hiển thị liên kết Đăng nhập/Đăng ký nếu chưa đăng nhập
                document.getElementById("authLinks").style.display = "block";
                document.getElementById("loggedInUser").style.display = "none";
            }
        })
        .catch(error => console.error('Error checking login status:', error));

    // Xử lý khi người dùng nhấn Đăng xuất
    const logoutLink = document.getElementById("logoutLink");
    if (logoutLink) {
        logoutLink.addEventListener("click", function(event) {
            event.preventDefault();
            // Gọi API đăng xuất
            fetch('/user/logout', { method: 'POST', credentials: 'include' })
                .then(() => {
                    // Chuyển hướng về trang chủ sau khi đăng xuất
                    window.location.href = 'TrangChu.html';
                })
                .catch(error => console.error('Error logging out:', error));
        });
    }
}

// Tải header và footer khi trang đã sẵn sàng
document.addEventListener("DOMContentLoaded", function() {
    // Tải header
    fetch('header.html')
        .then(response => response.text())
        .then(data => {
            const headerElement = document.getElementById('header');
            if (headerElement) {
                headerElement.innerHTML = data;
                checkLoginStatus(); // Kiểm tra trạng thái đăng nhập sau khi header được tải
            } else {
                console.error('Không tìm thấy phần tử #header');
            }
        })
        .catch(error => console.error('Error loading header:', error));

    // Tải footer
    fetch("footer.html")
        .then(response => response.text())
        .then(data => {
            const footerElement = document.getElementById("footer");
            if (footerElement) {
                footerElement.innerHTML = data;
            } else {
                console.error('Không tìm thấy phần tử #footer');
            }
        });
});
