package com.spring.sevices;

import com.spring.entities.Claims;
import com.spring.entities.ProjectDetail;
import com.spring.entities.Status;
import com.spring.repository.ClaimRepository;
import com.spring.repository.ClaimsRepository;
import com.spring.repository.ProjectDetailRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class ClaimsServiceImple implements ClaimsService {
    @Autowired
    private ClaimsRepository claimsRepository;


    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ProjectDetailRepository projectDetailRepository;

    @Override
    public Claims saveClaim(Claims claim) {

        claimsRepository.save(claim);

        return claim;
    }

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ClaimsServiceImple() {
        workbook = new XSSFWorkbook();
    }

    @Override
    public List<Claims> getAllClaims() {
        return claimsRepository.findAll();
    }

    @Override
    public void export(List<Claims> lists, HttpServletResponse response) throws IOException, IllegalAccessException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String currentDateTime = dateFormatter.format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=Claims_" + currentDateTime + ".xlsx");
        writeHeaderLine();  // Ghi tiêu đề
        writeDataLines(lists);  // Ghi dữ liệu
        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Employees");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Field[] fields = Claims.class.getDeclaredFields();
        int columnCount = 0;
        for (Field field : fields) {
            createCell(row, columnCount++, field.getName(), style);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer integer) {
            cell.setCellValue(integer);
        } else if (value instanceof Boolean aBoolean) {
            cell.setCellValue(aBoolean);
        } else if (value instanceof Timestamp timestamp) {
            cell.setCellValue(timestamp);
        } else if (value instanceof Time time) {
            cell.setCellValue(time);
        } else if (value instanceof java.sql.Date sqlDate) {
            cell.setCellValue(sqlDate);
        } else if (value instanceof String string) {
            cell.setCellValue(string);
        } else {
            cell.setCellValue(String.valueOf(value));
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines(List<Claims> lists) throws IllegalAccessException {
        int rowCount = 1; // Bắt đầu ghi từ hàng thứ 2
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Claims t : lists) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            Field[] fields = Claims.class.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // Cho phép truy cập vào các trường private
                Object value = field.get(t); // Lấy giá trị của trường
                createCell(row, columnCount++, value, style); // Ghi giá trị vào ô
            }
        }
    }


}
