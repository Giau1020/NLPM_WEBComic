//// hiển thị
//document.addEventListener("DOMContentLoaded", function () {
//    const showMonthlyFormButton = document.getElementById("showMonthlyForm");
//    const showQuarterlyFormButton = document.getElementById("showQuarterlyForm");
//    const monthlyForm = document.getElementById("monthlyForm");
//    const quarterlyForm = document.getElementById("quarterlyForm");
//
//    // Hiển thị biểu mẫu doanh thu theo tháng
//    showMonthlyFormButton.addEventListener("click", function () {
//        monthlyForm.style.display = "block";
//        quarterlyForm.style.display = "none";
//    });
//
//    // Hiển thị biểu mẫu doanh thu theo quý
//    showQuarterlyFormButton.addEventListener("click", function () {
//        quarterlyForm.style.display = "block";
//        monthlyForm.style.display = "none";
//    });
//// Xử lý sự kiện submit của form doanh thu theo tháng
//    document.getElementById("monthlyRevenueForm").addEventListener("submit", function (e) {
//        e.preventDefault();
//        const year = document.getElementById("year").value;
//        const month = document.getElementById("month").value;
//
//        if (year && month) {
//            // Gọi API doanh thu theo tháng
//            fetch(`/api/reports/monthly-revenue?year=${year}&month=${month}`)
//                .then(response => {
//                    if (!response.ok) {
//                        throw new Error("Failed to fetch monthly revenue data.");
//                    }
//                    return response.json();
//                })
//                .then(data => {
//                    // Xử lý dữ liệu trả về từ API
//                    console.log(`Monthly Revenue for Year: ${year}, Month: ${month}`, data);
//                    alert(`Doanh thu tháng ${month}/${year}: ${data[0].totalRevenue} VND`);
//                })
//                .catch(error => {
//                    console.error("Error fetching monthly revenue:", error);
//                    alert("Có lỗi xảy ra khi lấy dữ liệu doanh thu theo tháng.");
//                });
//        }
//    });
//
//    // Xử lý sự kiện submit của form doanh thu theo quý
//    document.getElementById("quarterlyRevenueForm").addEventListener("submit", function (e) {
//        e.preventDefault();
//        const year = document.getElementById("yearQ").value;
//        const quarter = document.getElementById("quarter").value;
//
//        if (year && quarter) {
//            // Gọi API doanh thu theo quý
//            fetch(`/api/reports/quarterly-revenue?year=${year}&quarter=${quarter}`)
//                .then(response => {
//                    if (!response.ok) {
//                        throw new Error("Failed to fetch quarterly revenue data.");
//                    }
//                    return response.json();
//                })
//                .then(data => {
//                    // Xử lý dữ liệu trả về từ API
//                    console.log(`Quarterly Revenue for Year: ${year}, Quarter: Q${quarter}`, data);
//                    alert(`Doanh thu quý ${quarter}/${year}: ${data[0].totalRevenue} VND`);
//                })
//                .catch(error => {
//                    console.error("Error fetching quarterly revenue:", error);
//                    alert("Có lỗi xảy ra khi lấy dữ liệu doanh thu theo quý.");
//                });
//        }
//    });
//});
document.addEventListener("DOMContentLoaded", function () {
//    const showMonthlyFormButton = document.getElementById("showMonthlyForm");
//    const showQuarterlyFormButton = document.getElementById("showQuarterlyForm");
//    const monthlyForm = document.getElementById("monthlyForm");
//    const quarterlyForm = document.getElementById("quarterlyForm");
//
//    // Hiển thị biểu mẫu doanh thu theo tháng
//    showMonthlyFormButton.addEventListener("click", function () {
//        monthlyForm.style.display = "block";
//        quarterlyForm.style.display = "none";
//    });
//
//    // Hiển thị biểu mẫu doanh thu theo quý
//    showQuarterlyFormButton.addEventListener("click", function () {
//        quarterlyForm.style.display = "block";
//        monthlyForm.style.display = "none";
//    });

//    // Xử lý sự kiện submit của form doanh thu theo tháng
//    document.getElementById("monthlyRevenueForm").addEventListener("submit", function (e) {
//        e.preventDefault();
//        const year = document.getElementById("year").value;
//        const month = document.getElementById("month").value;
//
//        if (year && month) {
//            // Gọi API doanh thu theo tháng
//            fetch(`/api/reports/monthly-revenue?year=${year}&month=${month}`)
//                .then(response => {
//                    if (!response.ok) {
//                        throw new Error("Failed to fetch monthly revenue data.");
//                    }
//                    return response.json();
//                })
//                .then(data => {
//                    // Mở trang mới và hiển thị kết quả
//                    const newWindow = window.open("", "_blank");
//                    newWindow.document.write("<h2>Monthly Revenue Report</h2>");
//                    newWindow.document.write(`<p>Year: ${year}</p>`);
//                    newWindow.document.write(`<p>Month: ${month}</p>`);
//                    newWindow.document.write(`<p>Total Revenue: ${data[0].totalRevenue} VND</p>`);
//                })
//                .catch(error => {
//                    console.error("Error fetching monthly revenue:", error);
//                    alert("Có lỗi xảy ra khi lấy dữ liệu doanh thu theo tháng.");
//                });
//        }
//    });
//
//    // Xử lý sự kiện submit của form doanh thu theo quý
//    document.getElementById("quarterlyRevenueForm").addEventListener("submit", function (e) {
//        e.preventDefault();
//        const year = document.getElementById("yearQ").value;
//        const quarter = document.getElementById("quarter").value;
//
//        if (year && quarter) {
//            // Gọi API doanh thu theo quý
//            fetch(`/api/reports/quarterly-revenue?year=${year}&quarter=${quarter}`)
//                .then(response => {
//                    if (!response.ok) {
//                        throw new Error("Failed to fetch quarterly revenue data.");
//                    }
//                    return response.json();
//                })
//                .then(data => {
//                    // Mở trang mới và hiển thị kết quả
//                    const newWindow = window.open("", "_blank");
//                    newWindow.document.write("<h2>Quarterly Revenue Report</h2>");
//                    newWindow.document.write(`<p>Year: ${year}</p>`);
//                    newWindow.document.write(`<p>Quarter: ${quarter}</p>`);
//                    newWindow.document.write(`<p>Total Revenue: ${data[0].totalRevenue} VND</p>`);
//                })
//                .catch(error => {
//                    console.error("Error fetching quarterly revenue:", error);
//                    alert("Có lỗi xảy ra khi lấy dữ liệu doanh thu theo quý.");
//                });
//        }
//    });
//});


//       let salesChart; // Biến toàn cục để lưu trữ biểu đồ
//
//       document.getElementById("showChartBtn").addEventListener("click", function() {
//           // Lấy dữ liệu từ bảng để hiển thị trên biểu đồ
//           const rows = Array.from(document.querySelectorAll("#report-table tr"));
//           const comicNames = [];
//           const salesData = [];
//
//           rows.forEach(row => {
//               const cells = row.querySelectorAll("td");
//               comicNames.push(cells[1].textContent); // Tên Truyện
//               salesData.push(parseInt(cells[4].textContent)); // Số Lượt Bán
//           });
//
//           // Hủy biểu đồ cũ nếu tồn tại
//           if (salesChart) {
//               salesChart.destroy();
//           }
//
//           // Hiển thị biểu đồ
//           const ctx = document.getElementById('salesChart').getContext('2d');
//           document.getElementById('salesChart').style.display = 'block';
//
//           salesChart = new Chart(ctx, {
//               type: 'bar',
//               data: {
//                   labels: comicNames,
//                   datasets: [{
//                       label: 'Số Lượt Bán',
//                       data: salesData,
//                       backgroundColor: 'rgba(75, 192, 192, 0.2)',
//                       borderColor: 'rgba(75, 192, 192, 1)',
//                       borderWidth: 1
//                   }]
//               },
//               options: {
//                   responsive: true,
//                   scales: {
//                       y: {
//                           beginAtZero: true
//                       }
//                   }
//               }
//           });
//       });

//    document.getElementById("showChartBtn").addEventListener("click", function() {
//        const rows = Array.from(document.querySelectorAll("#report-table tr"));
//        const comicNames = [];
//        const salesData = [];
//
//        rows.forEach(row => {
//            const cells = row.querySelectorAll("td");
//            comicNames.push(cells[1].textContent); // Tên Truyện
//            salesData.push(parseInt(cells[4].textContent)); // Số Lượt Bán
//        });
//        // Lưu dữ liệu vào localStorage
//        localStorage.setItem("comicNames", JSON.stringify(comicNames));
//        localStorage.setItem("salesData", JSON.stringify(salesData));
//
//        // Mở trang hiển thị biểu đồ
//        window.open("chart.html", "_blank");
//    });
//
//
//// bán chậm
//
//        document.getElementById("showChartlowcomicBtn").addEventListener("click", function() {
//            const rows = Array.from(document.querySelectorAll("#report-low-table tr"));
//            const comicNames = [];
//            const salesData = [];
//
//            rows.forEach(row => {
//                const cells = row.querySelectorAll("td");
//                comicNames.push(cells[1].textContent); // Tên Truyện
//                salesData.push(parseInt(cells[4].textContent)); // Số Lượt Bán
//            });
//
//            // Lưu dữ liệu vào localStorage
//            localStorage.setItem("comicNames", JSON.stringify(comicNames));
//            localStorage.setItem("salesData", JSON.stringify(salesData));
//
//            // Mở trang hiển thị biểu đồ
//            window.open("chart.html", "_blank");
//        });
//
////ton kho
//
//
//   document.getElementById("showChartsoldtableBtn").addEventListener("click", function() {
//        const rows = Array.from(document.querySelectorAll("#report-table tr"));
//        const comicNames = [];
//        const salesData = [];
//
//        rows.forEach(row => {
//            const cells = row.querySelectorAll("td");
//            comicNames.push(cells[1].textContent); // Tên Truyện
//            salesData.push(parseInt(cells[4].textContent)); // Số Lượt Bán
//        });
//        // Lưu dữ liệu vào localStorage
//        localStorage.setItem("comicNames", JSON.stringify(comicNames));
//        localStorage.setItem("salesData", JSON.stringify(salesData));
//
//        // Mở trang hiển thị biểu đồ
//        window.open("chart.html", "_blank");
//    });
//// hàng tồn kho thấp
//        document.getElementById("showChartlowstockBtn").addEventListener("click", function() {
//            const rows = Array.from(document.querySelectorAll("#report-sold-table tr"));
//            const comicNames = [];
//            const salesData = [];
//
//            rows.forEach(row => {
//                const cells = row.querySelectorAll("td");
//                comicNames.push(cells[1].textContent); // Tên Truyện
//                salesData.push(parseInt(cells[4].textContent)); // Số Lượng tồn kho
//            });
//
//            // Lưu dữ liệu vào localStorage
//            localStorage.setItem("comicNames", JSON.stringify(comicNames));
//            localStorage.setItem("salesData", JSON.stringify(salesData));
//
//            // Mở trang hiển thị biểu đồ
//            window.open("chart.html", "_blank");
//        });
//
// Hiển thị biểu đồ tổng bán hàng

document.getElementById("showChartBtn").addEventListener("click", function() {
    const rows = Array.from(document.querySelectorAll("#report-table tr"));
    const comicNames = [];
    const salesData = [];

    rows.forEach(row => {
        const cells = row.querySelectorAll("td");
        comicNames.push(cells[1].textContent); // Tên Truyện
        salesData.push(parseInt(cells[4].textContent)); // Số Lượt Bán
    });

    // Lưu dữ liệu vào localStorage cho tổng bán hàng
    localStorage.setItem("comicNames_sales", JSON.stringify(comicNames));
    localStorage.setItem("salesData_sales", JSON.stringify(salesData));

    // Mở trang hiển thị biểu đồ
    window.open("chart.html", "_blank");
});

// Hiển thị biểu đồ bán chậm
document.getElementById("showChartlowcomicBtn").addEventListener("click", function() {
    const rows = Array.from(document.querySelectorAll("#report-low-table tr"));
    const comicNames = [];
    const salesData = [];

    rows.forEach(row => {
        const cells = row.querySelectorAll("td");
        comicNames.push(cells[1].textContent); // Tên Truyện
        salesData.push(parseInt(cells[4].textContent)); // Số Lượt Bán
    });

    // Lưu dữ liệu vào localStorage cho bán chậm
    localStorage.setItem("comicNames_lowSales", JSON.stringify(comicNames));
    localStorage.setItem("salesData_lowSales", JSON.stringify(salesData));

    // Mở trang hiển thị biểu đồ
    window.open("chart.html", "_blank");
});

// Hiển thị biểu đồ tồn kho
document.getElementById("showChartsoldtableBtn").addEventListener("click", function() {
    const rows = Array.from(document.querySelectorAll("#report-sold-table tr"));
    const comicNames = [];
    const salesData = [];

    rows.forEach(row => {
        const cells = row.querySelectorAll("td");
        comicNames.push(cells[1].textContent); // Tên Truyện
        salesData.push(parseInt(cells[4].textContent)); // Số Lượt Bán
    });

    // Lưu dữ liệu vào localStorage cho tồn kho
    localStorage.setItem("comicNames_stock", JSON.stringify(comicNames));
    localStorage.setItem("salesData_stock", JSON.stringify(salesData));

    // Mở trang hiển thị biểu đồ
    window.open("chart.html", "_blank");
});

// Hiển thị biểu đồ tồn kho thấp
document.getElementById("showChartlowstockBtn").addEventListener("click", function() {
    const rows = Array.from(document.querySelectorAll("#report-lowstock-table tr"));
    const comicNames = [];
    const salesData = [];

    rows.forEach(row => {
        const cells = row.querySelectorAll("td");
        comicNames.push(cells[1].textContent); // Tên Truyện
        salesData.push(parseInt(cells[4].textContent)); // Số Lượng tồn kho
    });

    // Lưu dữ liệu vào localStorage cho tồn kho thấp
    localStorage.setItem("comicNames_lowStock", JSON.stringify(comicNames));
    localStorage.setItem("salesData_lowStock", JSON.stringify(salesData));

    // Mở trang hiển thị biểu đồ
    window.open("chart.html", "_blank");
});


