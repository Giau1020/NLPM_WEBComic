document.addEventListener("DOMContentLoaded", () => {
    fetchComics();
});
//
//function fetchComics() {
//    fetch('/api/v1/sng/admin/comics') // Gọi đến API để lấy danh sách truyện
//        .then(response => {
//            if (!response.ok) {
//                throw new Error('Network response was not ok');
//            }
//            return response.json();
//        })
//        .then(comics => {
//            const comicTableBody = document.getElementById('comic-table-body');
//            comicTableBody.innerHTML = ''; // Xóa nội dung hiện tại của bảng
//
//            comics.forEach(comic => {
//                const row = document.createElement('tr');
//
//                row.innerHTML = `
//                    <td>${comic.name}</td>
//                    <td>${comic.price} ₫</td>
//                    <td>${comic.genres}</td>
//                    <td>${comic.authors}</td>
//                    <td>${comic.sold}</td>
//                    <td>${comic.publisher}</td>
//                `;
//
//                comicTableBody.appendChild(row);
//            });
//        })
//        .catch(error => {
//            console.error('There was a problem with the fetch operation:', error);
//            alert('Không thể tải danh sách truyện. Vui lòng thử lại sau.');
//        });
//}
function fetchComics() {
    fetch('/api/v1/sng/admin/comics') // Gọi đến API để lấy danh sách truyện
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(comics => {
            const comicTableBody = document.getElementById('comic-table-body');
            comicTableBody.innerHTML = ''; // Xóa nội dung hiện tại của bảng

            comics.forEach(comic => {
                const row = document.createElement('tr');

                // Lấy tất cả tên tác giả và nối thành chuỗi
                const authors = comic.authors && comic.authors.length > 0
                    ? comic.authors.map(author => author.name).join(', ')
                    : 'Chưa có tác giả';

                // Lấy tất cả tên thể loại và nối thành chuỗi
                const genres = comic.genres && comic.genres.length > 0
                    ? comic.genres.map(genre => genre.name).join(', ')
                    : 'Chưa có thể loại';

                row.innerHTML = `
                    <td>${comic.name}</td>
                    <td>${comic.price} ₫</td>
                    <td>${genres}</td>
                    <td>${authors}</td>
                    <td>${comic.sold}</td>
                    <td>${comic.publisher || 'Chưa có nhà xuất bản'}</td>
                `;

                comicTableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            alert('Không thể tải danh sách truyện. Vui lòng thử lại sau.');
        });
}
