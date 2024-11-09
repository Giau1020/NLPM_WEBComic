// Lấy các phần tử nút và bảng
const reportComicBtn = document.querySelector('.report-comic');
const reportQuantityBtn = document.querySelector('.report-quantity');
const reportCartbtn = document.querySelector('.report-cart')
const comicTable = document.querySelector('.table-comic');
const quantityTable = document.querySelector('.table-sold');
const cartTable = document.querySelector('.table-cart');


////
//// Khi trang tải, thiết lập hiển thị cho các bảng
document.addEventListener("DOMContentLoaded", function () {
    // Đặt bảng tồn kho ẩn và bảng bestseller hiển thị
    comicTable.style.display = 'block';
    quantityTable.style.display = 'none';
    cartTable.style.display ='none';
    // Fetch dữ liệu cho bảng bestseller
    fetch('/api/comics/top-sellers') // Thay thế bằng API thực tế của bạn
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("report-table");
            tableBody.innerHTML = ""; // Xóa nội dung cũ

            // Thêm dữ liệu vào bảng bestseller
            data.forEach(comic => {
                const row = `
                    <tr>
                        <td>${comic[0]}</td>
                        <td>${comic[1]}</td>
                        <td>${comic[2]}</td>
                        <td>${comic[3]}</td>
                        <td>${comic[4]}</td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error fetching data for bestseller:', error));

    // Fetch dữ liệu cho quản lý tồn kho
    fetch('/api/comics')  // Đường dẫn đến API của bạn
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('report-sold-table');
            tableBody.innerHTML = ''; // Xóa nội dung cũ trước khi thêm mới

            data.forEach(comic => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${comic.id}</td>
                    <td>${comic.name}</td>
                    <td>${comic.author}</td>
                    <td>${comic.publisher}</td>
                    <td>${comic.quantity}</td>
                `;
                tableBody.appendChild(row);
            });

            // Hiển thị bảng "tồn kho" nếu cần
            // quantityTable.style.display = 'block'; // Không cần nếu đã ẩn ở đầu
        })
        .catch(error => {
            console.error('Lỗi khi lấy dữ liệu tồn kho:', error);
        });

        //fetch api cho giỏ hàng
        fetch('/api/carts/top-comics') // Thay thế bằng API thực tế của bạn
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("report-cart-table");
            tableBody.innerHTML = ""; // Xóa nội dung cũ

            // Thêm dữ liệu vào bảng bestseller
            data.forEach(cart => {
                const row = `
                    <tr>
                        <td>${cart[0]}</td>
                        <td>${cart[1]}</td>
                        <td>${cart[2]}</td>
                        <td>${cart[3]}</td>
                        <td>${cart[4]}</td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error fetching data for bestseller:', error));

});





// Sự kiện khi nhấn nút "Truyện bán chạy nhất"
reportComicBtn.addEventListener('click', () => {
    comicTable.style.display = 'block';  // Hiển thị bảng truyện bán chạy nhất
    quantityTable.style.display = 'none'; // Ẩn bảng quản lý tồn kho
    cartTable.style.display ='none';
});

// Sự kiện khi nhấn nút "Quản lý tồn kho"
reportQuantityBtn.addEventListener('click', () => {
    quantityTable.style.display = 'block'; // Hiển thị bảng quản lý tồn kho
    comicTable.style.display = 'none';      // Ẩn bảng truyện bán chạy nhất
    cartTable.style.display ='none';
});
reportCartbtn.addEventListener('click', () =>{
    quantityTable.style.display = 'none'; // Hiển thị bảng quản lý tồn kho
    comicTable.style.display = 'none';      // Ẩn bảng truyện bán chạy nhất
    cartTable.style.display ='block';
});


// //  xuất file excel
//  document.getElementById("report-excel").addEventListener("click", function() {
//     const reportcomic = document.getElementById("table-comic");
//     const reportquantity = document.getElementById("table-sold");
//     const reportcart = document.getElementById("table-cart");

//     if (!reportcomic || !reportquantity || !reportcart) {
//         console.error("Không tìm thấy một trong các bảng!");
//         return;
//     }

//     // Tạo một workbook mới
//     const wb = XLSX.utils.book_new();

//     // Chuyển đổi bảng bestseller thành sheet
//     const reportcomicSheet = XLSX.utils.table_to_sheet(reportcomic);
//     XLSX.utils.book_append_sheet(wb, reportcomicSheet, "Truyện bán chạy nhất");

//     // Chuyển đổi bảng tồn kho thành sheet
//     const reportquantitySheet = XLSX.utils.table_to_sheet(reportquantity);
//     XLSX.utils.book_append_sheet(wb, reportquantitySheet, "Tồn Kho");

//     // chuyển đổi bảng sản phẩm có nhiều trong giỏ hàng
//     const reportcartSheet = XLSX.utils.table_to_sheet(reportcart);
//     XLSX.utils.book_append_sheet(wb, reportcartSheet, "Giỏ hàng");

//     // Xuất file Excel
//     XLSX.writeFile(wb, "BaoCaoThongKe.xlsx");
// });

document.getElementById("report-excel").addEventListener("click", function () {
    // Tạo workbook mới bằng ExcelJS
    const workbook = new ExcelJS.Workbook();

    // Thêm sheet cho bảng "Truyện bán chạy nhất"
    const worksheetComic = workbook.addWorksheet("Truyện bán chạy nhất");
    worksheetComic.columns = [
        { header: 'ID Truyện', key: 'id', width: 10 },
        { header: 'Tên Truyện', key: 'name', width: 40 },
        { header: 'Tên Tác Giả', key: 'author', width: 20 },
        { header: 'Nhà Xuất Bản', key: 'publisher', width: 30 },
        { header: 'Số Lượng', key: 'quantity', width: 15 }
    ];

    // Lấy dữ liệu từ bảng "Truyện bán chạy nhất"
    const tableComic = document.querySelector("#report-table");
    const rowsComic = tableComic.querySelectorAll("tr");

    rowsComic.forEach((row, rowIndex) => {
        const cells = row.querySelectorAll("td, th");
        const rowData = {};

        cells.forEach((cell, cellIndex) => {
            if (cellIndex === 0) rowData.id = cell.innerText;
            if (cellIndex === 1) rowData.name = cell.innerText;
            if (cellIndex === 2) rowData.author = cell.innerText;
            if (cellIndex === 3) rowData.publisher = cell.innerText;
            if (cellIndex === 4) rowData.quantity = cell.innerText;
        });

        if (rowIndex > 0) { // Bỏ qua tiêu đề
            worksheetComic.addRow(rowData);
        }
    });

    worksheetComic.getRow(1).font = { bold: true };
    worksheetComic.getRow(1).fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'FFFFCCCC' }
    };
    worksheetComic.eachRow({ includeEmpty: false }, function (row) {
        row.eachCell({ includeEmpty: false }, function (cell) {
            cell.border = {
                top: { style: 'thin' },
                left: { style: 'thin' },
                bottom: { style: 'thin' },
                right: { style: 'thin' }
            };
        });
    });

    // Thêm sheet cho bảng "Tồn kho"
    const worksheetQuantity = workbook.addWorksheet("Tồn kho");
    worksheetQuantity.columns = [
        { header: 'ID Truyện', key: 'id', width: 10 },
        { header: 'Tên Truyện', key: 'name', width: 40 },
        { header: 'Tên Tác Giả', key: 'author', width: 20 },
        { header: 'Nhà Xuất Bản', key: 'publisher', width: 30 },
        { header: 'Số Lượng', key: 'quantity', width: 15 }
    ];

    // Lấy dữ liệu từ bảng "Tồn kho"
    const tableQuantity = document.querySelector("#report-sold-table");
    const rowsQuantity = tableQuantity.querySelectorAll("tr");

    rowsQuantity.forEach((row, rowIndex) => {
        const cells = row.querySelectorAll("td, th");
        const rowData = {};

        cells.forEach((cell, cellIndex) => {
            if (cellIndex === 0) rowData.id = cell.innerText;
            if (cellIndex === 1) rowData.name = cell.innerText;
            if (cellIndex === 2) rowData.author = cell.innerText;
            if (cellIndex === 3) rowData.publisher = cell.innerText;
            if (cellIndex === 4) rowData.quantity = cell.innerText;
        });

        if (rowIndex > 0) { // Bỏ qua tiêu đề
            worksheetQuantity.addRow(rowData);
        }
    });

    worksheetQuantity.getRow(1).font = { bold: true };
    worksheetQuantity.getRow(1).fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'FFFFCCCC' }
    };
    worksheetQuantity.eachRow({ includeEmpty: false }, function (row) {
        row.eachCell({ includeEmpty: false }, function (cell) {
            cell.border = {
                top: { style: 'thin' },
                left: { style: 'thin' },
                bottom: { style: 'thin' },
                right: { style: 'thin' }
            };
        });
    });

    // Thêm sheet cho bảng "Giỏ hàng"
    const worksheetCart = workbook.addWorksheet("Giỏ hàng");
    worksheetCart.columns = [
        { header: 'ID Truyện', key: 'id', width: 10 },
        { header: 'Tên Truyện', key: 'name', width: 40 },
        { header: 'Tên Tác Giả', key: 'author', width: 20 },
        { header: 'Nhà Xuất Bản', key: 'publisher', width: 30 },
        { header: 'Số Lượng', key: 'quantity', width: 15 }
    ];

    // Lấy dữ liệu từ bảng "Giỏ hàng"
    const tableCart = document.querySelector("#report-cart-table");
    const rowsCart = tableCart.querySelectorAll("tr");

    rowsCart.forEach((row, rowIndex) => {
        const cells = row.querySelectorAll("td, th");
        const rowData = {};

        cells.forEach((cell, cellIndex) => {
            if (cellIndex === 0) rowData.id = cell.innerText;
            if (cellIndex === 1) rowData.name = cell.innerText;
            if (cellIndex === 2) rowData.author = cell.innerText;
            if (cellIndex === 3) rowData.publisher = cell.innerText;
            if (cellIndex === 4) rowData.quantity = cell.innerText;
        });

        if (rowIndex > 0) { // Bỏ qua tiêu đề
            worksheetCart.addRow(rowData);
        }
    });

    worksheetCart.getRow(1).font = { bold: true };
    worksheetCart.getRow(1).fill = {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'FFFFCCCC' }
    };
    worksheetCart.eachRow({ includeEmpty: false }, function (row) {
        row.eachCell({ includeEmpty: false }, function (cell) {
            cell.border = {
                top: { style: 'thin' },
                left: { style: 'thin' },
                bottom: { style: 'thin' },
                right: { style: 'thin' }
            };
        });
    });

    // Xuất file Excel với cả ba bảng
    workbook.xlsx.writeBuffer().then(function (data) {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = 'BaoCaoThongKe.xlsx';
        link.click();
    });
});
