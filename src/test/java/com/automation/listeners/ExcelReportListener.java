package com.automation.listeners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/** Creates one timestamped Excel execution report for every TestNG suite run. */
public class ExcelReportListener implements ITestListener, ISuiteListener {
    private final List<TestRecord> records = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void onTestSuccess(ITestResult result) {
        addResult(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        addResult(result, "FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        addResult(result, "SKIPPED");
    }

    @Override
    public void onFinish(ISuite suite) {
        File reportDirectory = new File("target/test-reports");
        if (!reportDirectory.exists() && !reportDirectory.mkdirs()) {
            throw new RuntimeException("Unable to create Excel report directory: " + reportDirectory);
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        File reportFile = new File(reportDirectory, "test-report-" + timestamp + ".xlsx");
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream output = new FileOutputStream(reportFile)) {
            Sheet sheet = workbook.createSheet("Test Results");
            CellStyle headerStyle = createHeaderStyle(workbook);
            String[] headers = {"Suite", "Test", "Class", "Method", "Status", "Duration (ms)", "Details"};
            Row header = sheet.createRow(0);
            for (int index = 0; index < headers.length; index++) {
                Cell cell = header.createCell(index);
                cell.setCellValue(headers[index]);
                cell.setCellStyle(headerStyle);
            }

            int rowNumber = 1;
            synchronized (records) {
                for (TestRecord record : records) {
                    Row row = sheet.createRow(rowNumber++);
                    row.createCell(0).setCellValue(record.suite);
                    row.createCell(1).setCellValue(record.testName);
                    row.createCell(2).setCellValue(record.className);
                    row.createCell(3).setCellValue(record.methodName);
                    row.createCell(4).setCellValue(record.status);
                    row.createCell(5).setCellValue(record.duration);
                    row.createCell(6).setCellValue(record.details);
                }
            }
            for (int index = 0; index < headers.length; index++) {
                sheet.autoSizeColumn(index);
            }
            workbook.write(output);
            System.out.println("Excel test report created: " + reportFile.getPath());
        } catch (IOException exception) {
            throw new RuntimeException("Unable to write Excel test report", exception);
        }
    }

    private void addResult(ITestResult result, String status) {
        Throwable throwable = result.getThrowable();
        records.add(new TestRecord(
            result.getTestContext().getSuite().getName(),
            result.getTestContext().getName(),
            result.getTestClass().getName(),
            result.getMethod().getMethodName(),
            status,
            result.getEndMillis() - result.getStartMillis(),
            throwable == null ? "" : throwable.toString()));
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private static class TestRecord {
        private final String suite;
        private final String testName;
        private final String className;
        private final String methodName;
        private final String status;
        private final long duration;
        private final String details;

        private TestRecord(String suite, String testName, String className, String methodName,
                           String status, long duration, String details) {
            this.suite = suite;
            this.testName = testName;
            this.className = className;
            this.methodName = methodName;
            this.status = status;
            this.duration = duration;
            this.details = details;
        }
    }
}
