package com.example.demo.Service;
import com.example.demo.DTO.SalesStatisticsDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
//public class PdfService {
//
//    public ByteArrayInputStream generateRevenueReportPDF(
//            int year,
//            LocalDate reportDate,
//            double totalRevenue,
//            int totalOrders,
//            int totalComicsSold,
//            List<Map<String, Object>> monthlyRevenue,
//            List<Map<String, Object>> quarterlyRevenue) throws DocumentException, IOException {
//
//        Document document = new Document();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        PdfWriter.getInstance(document, out);
//        document.open();
//
//        // Title
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
//        Paragraph title = new Paragraph("Annual Revenue Report", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//
//        // Report Date
//        if (reportDate != null) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//            Paragraph date = new Paragraph("Report Date: " + reportDate.format(formatter));
//            date.setAlignment(Element.ALIGN_RIGHT);
//            document.add(date);
//        }
//
//        // Basic information
//        document.add(new Paragraph("Year: " + year));
//        document.add(new Paragraph("Total Revenue: $" + totalRevenue));
//        document.add(new Paragraph("Total Orders: " + totalOrders));
//        document.add(new Paragraph("Total Comics Sold: " + totalComicsSold));
//        document.add(Chunk.NEWLINE);
//
//        // Monthly Revenue Table
//        document.add(new Paragraph("Monthly Revenue Summary"));
//        PdfPTable monthlyTable = new PdfPTable(2);
//        monthlyTable.setWidthPercentage(100);
//        addTableHeader(monthlyTable, "Month", "Revenue");
//
//        for (Map<String, Object> monthData : monthlyRevenue) {
//            monthlyTable.addCell(monthData.get("month").toString());
//            monthlyTable.addCell(monthData.get("totalRevenue").toString());
//        }
//        document.add(monthlyTable);
//        document.add(Chunk.NEWLINE);
//
//        // Quarterly Revenue Table
//        document.add(new Paragraph("Quarterly Revenue Summary"));
//        PdfPTable quarterlyTable = new PdfPTable(2);
//        quarterlyTable.setWidthPercentage(100);
//        addTableHeader(quarterlyTable, "Quarter", "Revenue");
//
//        for (Map<String, Object> quarterData : quarterlyRevenue) {
//            quarterlyTable.addCell(quarterData.get("quarter").toString());
//            quarterlyTable.addCell(quarterData.get("totalRevenue").toString());
//        }
//        document.add(quarterlyTable);
//
//        document.close();
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//
//    private void addTableHeader(PdfPTable table, String... headers) {
//        for (String header : headers) {
//            PdfPCell headerCell = new PdfPCell();
//            headerCell.setPhrase(new Phrase(header));
//            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(headerCell);
//        }
//    }
//}



//public class PdfService {
//
//    // Định dạng tiền tệ VND với dấu phân cách hàng nghìn và ký hiệu ₫
//    private String formatCurrency(double amount) {
//        DecimalFormat formatter = new DecimalFormat("###,###,###");
//        return "₫" + formatter.format(amount);  // Định dạng tiền với dấu phân cách và ký hiệu VND
//    }
//
//    public ByteArrayInputStream generateRevenueReportPDF(
//            int year,
//            LocalDate reportDate,
//            double totalRevenue,
//            int totalOrders,
//            int totalComicsSold,
//            List<Map<String, Object>> monthlyRevenue,
//            List<Map<String, Object>> quarterlyRevenue,
//            List<SalesStatisticsDTO> salesStatistics) throws DocumentException, IOException {
//
//        // Đảm bảo sử dụng phông chữ hỗ trợ tiếng Việt
//        BaseFont vietnameseFont = BaseFont.createFont("src/main/resources/static/ADMIN//Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        Font titleFont = new Font(vietnameseFont, 16, Font.BOLD, BaseColor.BLACK);
//        Font normalFont = new Font(vietnameseFont, 12, Font.NORMAL, BaseColor.BLACK);
//
//        Document document = new Document();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        PdfWriter.getInstance(document, out);
//        document.open();
//
//        // Title
//        Paragraph title = new Paragraph("Báo Cáo Doanh Thu Hàng Năm", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//
//        // Report Date
//        if (reportDate != null) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//            Paragraph date = new Paragraph("Ngày Báo Cáo: " + reportDate.format(formatter), normalFont);
//            date.setAlignment(Element.ALIGN_RIGHT);
//            document.add(date);
//        }
//
//        // Basic information
//        document.add(new Paragraph("Năm: " + year, normalFont));
//        document.add(new Paragraph("Tổng Doanh Thu: " + formatCurrency(totalRevenue), normalFont));
//        document.add(new Paragraph("Tổng Số Đơn Hàng: " + totalOrders, normalFont));
//        document.add(new Paragraph("Tổng Số Truyện Đã Bán: " + totalComicsSold, normalFont));
//        document.add(Chunk.NEWLINE);
//
//        // Monthly Revenue Table with spacing and color customization
//        document.add(new Paragraph("Tóm Tắt Doanh Thu Hàng Tháng", titleFont));
//        document.add(new Paragraph(" "));
//
//        PdfPTable monthlyTable = new PdfPTable(2);
//        monthlyTable.setWidthPercentage(100);
//        monthlyTable.setWidths(new float[]{3f, 3f});
//
//        // Customizing table header with color
//        PdfPCell monthHeaderCell = new PdfPCell(new Phrase("Tháng", normalFont));
//        monthHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//        monthHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        monthlyTable.addCell(monthHeaderCell);
//
//        PdfPCell revenueHeaderCell = new PdfPCell(new Phrase("Doanh Thu", normalFont));
//        revenueHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//        revenueHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        monthlyTable.addCell(revenueHeaderCell);
//
//        // Fill table with monthly revenue data
//        for (Map<String, Object> monthData : monthlyRevenue) {
//            monthlyTable.addCell(monthData.get("month").toString());
//            monthlyTable.addCell(formatCurrency(Double.parseDouble(monthData.get("totalRevenue").toString())));  // Ensure proper formatting
//        }
//        document.add(monthlyTable);
//        document.add(Chunk.NEWLINE);
//
//        // Quarterly Revenue Table
//        document.add(new Paragraph("Tóm Tắt Doanh Thu Hàng Quý", titleFont));
//        document.add(new Paragraph(" "));
//
//        PdfPTable quarterlyTable = new PdfPTable(2);
//        quarterlyTable.setWidthPercentage(100);
//        quarterlyTable.setWidths(new float[]{3f, 3f});
//
//        // Customizing table header with color
//        PdfPCell quarterHeaderCell = new PdfPCell(new Phrase("Quý", normalFont));
//        quarterHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//        quarterHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        quarterlyTable.addCell(quarterHeaderCell);
//
//        PdfPCell quarterRevenueHeaderCell = new PdfPCell(new Phrase("Doanh Thu", normalFont));
//        quarterRevenueHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//        quarterRevenueHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        quarterlyTable.addCell(quarterRevenueHeaderCell);
//
//        // Fill table with quarterly revenue data
//        for (Map<String, Object> quarterData : quarterlyRevenue) {
//            quarterlyTable.addCell(quarterData.get("quarter").toString());
//            quarterlyTable.addCell(formatCurrency(Double.parseDouble(quarterData.get("totalRevenue").toString())));  // Ensure proper formatting
//        }
//
//        document.add(quarterlyTable);
//        document.add(Chunk.NEWLINE);
//
//        // Sales Statistics Table (ID instead of Comic Name)
//        // Title for the Sales Statistics section with font size 16Font titleFont = new Font(vietnameseFont, 16, Font.BOLD, BaseColor.BLACK);  // Ensure title font size is 16
////        document.add(new Paragraph("Thống Kê Doanh Thu: Đóng Góp Doanh Thu Theo Truyện", titleFont));
////         document.add(new Paragraph(" "));
////// Create Sales Statistics Table with proper column headers
////        PdfPTable salesStatsTable = new PdfPTable(4);  // 4 columns for ID, Quantity Sold, Revenue, Contribution
////        salesStatsTable.setWidthPercentage(100);
////        salesStatsTable.setWidths(new float[]{1.5f, 2f, 2f, 2f});  // Set width proportions for each column
////
////// Add the headers to the tableFont titleFont = new Font(vietnameseFont, 16, Font.BOLD, BaseColor.BLACK);
////        addTableHeader(salesStatsTable,  "ID", "Số Lượng Bán", "Doanh Thu", "Đóng Góp (%)");
////
////// Add data to the sales statistics table
////        for (SalesStatisticsDTO salesData : salesStatistics) {
////            salesStatsTable.addCell(String.valueOf(salesData.getId()));
////            salesStatsTable.addCell(String.valueOf(salesData.getTotalQuantity()));
////            salesStatsTable.addCell(formatCurrency(salesData.getTotalRevenue()));
////            salesStatsTable.addCell(String.format("%.2f", salesData.getContribution()));
////        }
////
////// Add the table to the document
////        document.add(salesStatsTable);
//
//
//        // Thêm tiêu đề cho phần "Thống Kê Doanh Thu: Đóng Góp Doanh Thu Theo Truyện"
//        document.add(new Paragraph("Thống Kê Doanh Thu: Đóng Góp Doanh Thu Theo Truyện", titleFont));
//        document.add(new Paragraph(" "));  // Thêm một khoảng trống giữa tiêu đề và bảng
//
//// Tạo bảng Sales Statistics với 4 cột: ID, Số Lượng Bán, Doanh Thu, Đóng Góp (%)
//        PdfPTable salesStatsTable = new PdfPTable(4);  // 4 cột cho ID, Quantity Sold, Revenue, Contribution
//        salesStatsTable.setWidthPercentage(100);  // Đặt độ rộng của bảng chiếm 100% chiều rộng của trang
//        salesStatsTable.setWidths(new float[]{1.5f, 2f, 2f, 2f});  // Đặt tỷ lệ chiều rộng cho các cột
//
//// Thêm tiêu đề cho các cột của bảng với phông chữ được chỉ định
//        addTableHeader(salesStatsTable, normalFont, "ID", "Số Lượng Bán", "Doanh Thu", "Đóng Góp (%)");
//
//// Thêm dữ liệu vào bảng Sales Statistics
//        for (SalesStatisticsDTO salesData : salesStatistics) {
//            salesStatsTable.addCell(String.valueOf(salesData.getId()));  // ID
//            salesStatsTable.addCell(String.valueOf(salesData.getTotalQuantity()));  // Số Lượng Bán
//            salesStatsTable.addCell(formatCurrency(salesData.getTotalRevenue()));  // Doanh Thu (được định dạng)
//            salesStatsTable.addCell(String.format("%.2f", salesData.getContribution()));  // Đóng Góp (%) (2 chữ số sau dấu thập phân)
//        }
//
//// Thêm bảng vào tài liệu PDF
//        document.add(salesStatsTable);  // Thêm bảng vào tài liệu PDF
//
//
//        document.close();
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//
//    private void addTableHeader(PdfPTable table, Font font, String... headers) {
//        for (String header : headers) {
//            PdfPCell headerCell = new PdfPCell();
//            headerCell.setPhrase(new Phrase(header, font));  // Dùng phông chữ được truyền vào
//            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);  // Màu nền cho tiêu đề
//            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);  // Căn giữa tiêu đề
//            headerCell.setPadding(5);  // Thêm khoảng cách trong ô
//            table.addCell(headerCell);  // Thêm ô vào bảng
//        }
//    }

//}
public class PdfService {

    // Định dạng tiền tệ VND với dấu phân cách hàng nghìn và ký hiệu ₫
    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return "₫" + formatter.format(amount);  // Định dạng tiền với dấu phân cách và ký hiệu VND
    }

    public ByteArrayInputStream generateRevenueReportPDF(
            int year,
            LocalDate reportDate,
            double totalRevenue,
            int totalOrders,
            int totalComicsSold,
            List<Map<String, Object>> monthlyRevenue,
            List<Map<String, Object>> quarterlyRevenue,
            List<SalesStatisticsDTO> salesStatistics) throws DocumentException, IOException {

        // Kiểm tra đầu vào
        if (monthlyRevenue == null || monthlyRevenue.isEmpty()) {
            throw new IllegalArgumentException("No monthly revenue data provided.");
        }
        if (quarterlyRevenue == null || quarterlyRevenue.isEmpty()) {
            throw new IllegalArgumentException("No quarterly revenue data provided.");
        }
        if (salesStatistics == null || salesStatistics.isEmpty()) {
            throw new IllegalArgumentException("No sales statistics data provided.");
        }

        // Đảm bảo sử dụng phông chữ hỗ trợ tiếng Việt
        BaseFont vietnameseFont = BaseFont.createFont("src/main/resources/static/ADMIN/fonts/Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(vietnameseFont, 16, Font.BOLD, BaseColor.BLACK);
        Font normalFont = new Font(vietnameseFont, 12, Font.NORMAL, BaseColor.BLACK);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Title
        Paragraph title = new Paragraph("Báo Cáo Doanh Thu Hàng Năm", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Report Date
        if (reportDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            Paragraph date = new Paragraph("Ngày Báo Cáo: " + reportDate.format(formatter), normalFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);
        }

        // Basic information
        document.add(new Paragraph("Năm: " + year, normalFont));
        document.add(new Paragraph("Tổng Doanh Thu: " + formatCurrency(totalRevenue), normalFont));
        document.add(new Paragraph("Tổng Số Đơn Hàng: " + totalOrders, normalFont));
        document.add(new Paragraph("Tổng Số Truyện Đã Bán: " + totalComicsSold, normalFont));
        document.add(Chunk.NEWLINE);

        // Monthly Revenue Table
        document.add(new Paragraph("Tóm Tắt Doanh Thu Hàng Tháng", titleFont));
        document.add(new Paragraph(" "));

        PdfPTable monthlyTable = new PdfPTable(2);
        monthlyTable.setWidthPercentage(100);
        monthlyTable.setWidths(new float[]{3f, 3f});

        // Customizing table header with color
        PdfPCell monthHeaderCell = new PdfPCell(new Phrase("Tháng", normalFont));
        monthHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        monthHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        monthlyTable.addCell(monthHeaderCell);

        PdfPCell revenueHeaderCell = new PdfPCell(new Phrase("Doanh Thu", normalFont));
        revenueHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        revenueHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        monthlyTable.addCell(revenueHeaderCell);

        // Fill table with monthly revenue data
        for (Map<String, Object> monthData : monthlyRevenue) {
            monthlyTable.addCell(monthData.get("month").toString());
            monthlyTable.addCell(formatCurrency(Double.parseDouble(monthData.get("totalRevenue").toString())));  // Ensure proper formatting
        }
        document.add(monthlyTable);
        document.add(Chunk.NEWLINE);

        // Quarterly Revenue Table
        document.add(new Paragraph("Tóm Tắt Doanh Thu Hàng Quý", titleFont));
        document.add(new Paragraph(" "));

        PdfPTable quarterlyTable = new PdfPTable(2);
        quarterlyTable.setWidthPercentage(100);
        quarterlyTable.setWidths(new float[]{3f, 3f});

        // Customizing table header with color
        PdfPCell quarterHeaderCell = new PdfPCell(new Phrase("Quý", normalFont));
        quarterHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        quarterHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        quarterlyTable.addCell(quarterHeaderCell);

        PdfPCell quarterRevenueHeaderCell = new PdfPCell(new Phrase("Doanh Thu", normalFont));
        quarterRevenueHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        quarterRevenueHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        quarterlyTable.addCell(quarterRevenueHeaderCell);

        // Fill table with quarterly revenue data
        for (Map<String, Object> quarterData : quarterlyRevenue) {
            quarterlyTable.addCell(quarterData.get("quarter").toString());
            quarterlyTable.addCell(formatCurrency(Double.parseDouble(quarterData.get("totalRevenue").toString())));  // Ensure proper formatting
        }

        document.add(quarterlyTable);
        document.add(Chunk.NEWLINE);

        // Sales Statistics Table with 4 columns: ID, Quantity Sold, Revenue, Contribution
        document.add(new Paragraph("Thống Kê Doanh Thu: Đóng Góp Doanh Thu Theo Truyện", titleFont));
        document.add(new Paragraph(" "));

        PdfPTable salesStatsTable = new PdfPTable(4);  // 4 columns for ID, Quantity Sold, Revenue, Contribution
        salesStatsTable.setWidthPercentage(100);  // Set the width of the table to 100%
        salesStatsTable.setWidths(new float[]{1.5f, 2f, 2f, 2f});  // Set proportional widths for columns

        // Add headers to the sales statistics table
        addTableHeader(salesStatsTable, normalFont, "ID", "Số Lượng Bán", "Doanh Thu", "Đóng Góp (%)");

        // Add data to the sales statistics table
        for (SalesStatisticsDTO salesData : salesStatistics) {
            salesStatsTable.addCell(String.valueOf(salesData.getId()));  // ID
            salesStatsTable.addCell(String.valueOf(salesData.getTotalQuantity()));  // Quantity Sold
            salesStatsTable.addCell(formatCurrency(salesData.getTotalRevenue()));  // Revenue
            salesStatsTable.addCell(String.format("%.2f", salesData.getContribution()));  // Contribution (%)
        }

        // Add the table to the document
        document.add(salesStatsTable);

        // Close the document and return the PDF byte stream
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Helper method to add table headers
    private void addTableHeader(PdfPTable table, Font font, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new Phrase(header, font));  // Use the provided font
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);  // Background color for the header
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);  // Center the header text
            headerCell.setPadding(5);  // Add padding inside the cell
            table.addCell(headerCell);  // Add the header cell to the table
        }
    }



    /////
    public ByteArrayInputStream generateEmptyRevenueReportPDF(int year, LocalDate reportDate) throws DocumentException, IOException {
        // Đảm bảo sử dụng phông chữ hỗ trợ tiếng Việt
        BaseFont vietnameseFont = BaseFont.createFont("src/main/resources/static/ADMIN/fonts/Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(vietnameseFont, 16, Font.BOLD, BaseColor.BLACK);
        Font normalFont = new Font(vietnameseFont, 12, Font.NORMAL, BaseColor.BLACK);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Title
        Paragraph title = new Paragraph("Báo Cáo Doanh Thu Hàng Năm", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Report Date
        if (reportDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            Paragraph date = new Paragraph("Ngày Báo Cáo: " + reportDate.format(formatter), normalFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);
        }

        // Nếu không có dữ liệu, thêm thông báo không có dữ liệu
        document.add(new Paragraph("Năm: " + year, normalFont));
        document.add(new Paragraph("Không có dữ liệu báo cáo cho năm " + year, normalFont));

        // Close the document and return the PDF byte stream
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

}
