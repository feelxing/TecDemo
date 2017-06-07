package com.feelxing.tools.poi;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.opc.internal.ZipHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by MEV on 2017/6/7.
     * https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/xssf/usermodel/examples/BigGridDemo.java
 */
public class BigGridDemo {
    private static final String XML_ENCODING = "UTF-8";

    public static void main(String[] args) throws Exception {
        Long Ibegin,Iend;
        // Step 1. Create a template file. Setup sheets and workbook-level objects such as
        // cell styles, number formats, etc.
        Ibegin=System.currentTimeMillis();
        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet sheet = wb.createSheet("Big Grid");

        Map<String, XSSFCellStyle> styles = createStyles(wb);
        //name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
        String sheetRef = sheet.getPackagePart().getPartName().getName();

        //save the template
        FileOutputStream os = new FileOutputStream("D://template.xlsx");
        wb.write(os);
        os.close();

        //Step 2. Generate XML file.
        File tmp = File.createTempFile("sheet", ".xml");
        Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
        generate(fw, styles);
        fw.close();

        //Step 3. Substitute the template entry with the generated data
        FileOutputStream out = new FileOutputStream("D://big-grid.xlsx");
        substitute(new File("D://template.xlsx"), tmp, sheetRef.substring(1), out);
        out.close();

//        wb.close();
        Iend=System.currentTimeMillis();
        System.out.println("耗时："+(Iend-Ibegin)/1000+"秒");
    }

    /**
     * Create a library of cell styles.
     */
    private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        XSSFDataFormat fmt = wb.createDataFormat();

        XSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.RIGHT);
        style1.setDataFormat(fmt.getFormat("0.0%"));
        styles.put("percent", style1);

        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setDataFormat(fmt.getFormat("0.0X"));
        styles.put("coeff", style2);

        XSSFCellStyle style3 = wb.createCellStyle();
        style3.setAlignment(HorizontalAlignment.RIGHT);
        style3.setDataFormat(fmt.getFormat("$#,##0.00"));
        styles.put("currency", style3);

        XSSFCellStyle style4 = wb.createCellStyle();
        style4.setAlignment(HorizontalAlignment.RIGHT);
        style4.setDataFormat(fmt.getFormat("mmm dd"));
        styles.put("date", style4);

        XSSFCellStyle style5 = wb.createCellStyle();
        XSSFFont headerFont = wb.createFont();
        headerFont.setBold(true);
        style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style5.setFont(headerFont);
        styles.put("header", style5);

        return styles;
    }

    private static void generate(Writer out, Map<String, XSSFCellStyle> styles) throws Exception {

        Random rnd = new Random();
        Calendar calendar = Calendar.getInstance();

        SpreadsheetWriter sw = new SpreadsheetWriter(out);
        sw.beginSheet();

        //insert header row
        sw.insertRow(0);
        int styleIndex = styles.get("header").getIndex();
        sw.createCell(0, "Title", styleIndex);
        sw.createCell(1, "% Change", styleIndex);
        sw.createCell(2, "Ratio", styleIndex);
        sw.createCell(3, "Expenses", styleIndex);
        sw.createCell(4, "Date", styleIndex);

        sw.endRow();

        //write data rows
        for (int rownum = 1; rownum < 500000; rownum++) {
            sw.insertRow(rownum);

            sw.createCell(0, "Hello, " + rownum + "!");
            sw.createCell(1, (double)rnd.nextInt(100)/100, styles.get("percent").getIndex());
            sw.createCell(2, (double)rnd.nextInt(10)/10, styles.get("coeff").getIndex());
            sw.createCell(3, rnd.nextInt(10000), styles.get("currency").getIndex());
            sw.createCell(4, calendar, styles.get("date").getIndex());

            sw.endRow();

            calendar.roll(Calendar.DAY_OF_YEAR, 1);
        }
        sw.endSheet();
    }

    /**
     *
     * @param zipfile the template file
     * @param tmpfile the XML file with the sheet data
     * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
     * @param out the stream to write the result to
     */
    private static void substitute(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
        ZipFile zip = ZipHelper.openZipFile(zipfile);
        try {
            ZipOutputStream zos = new ZipOutputStream(out);

            Enumeration<? extends ZipEntry> en = zip.entries();
            while (en.hasMoreElements()) {
                ZipEntry ze = en.nextElement();
                if(!ze.getName().equals(entry)){
                    zos.putNextEntry(new ZipEntry(ze.getName()));
                    InputStream is = zip.getInputStream(ze);
                    copyStream(is, zos);
                    is.close();
                }
            }
            zos.putNextEntry(new ZipEntry(entry));
            InputStream is = new FileInputStream(tmpfile);
            copyStream(is, zos);
            is.close();

            zos.close();
        } finally {
            zip.close();
        }
    }

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >=0 ) {
            out.write(chunk,0,count);
        }
    }

    /**
     * Writes spreadsheet data in a Writer.
     * (YK: in future it may evolve in a full-featured API for streaming data in Excel)
     */
    public static class SpreadsheetWriter {
        private final Writer _out;
        private int _rownum;

        public SpreadsheetWriter(Writer out){
            _out = out;
        }

        public void beginSheet() throws IOException {
            _out.write("<?xml version=\"1.0\" encoding=\""+XML_ENCODING+"\"?>" +
                    "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">" );
            _out.write("<sheetData>\n");
        }

        public void endSheet() throws IOException {
            _out.write("</sheetData>");
            _out.write("</worksheet>");
        }

        /**
         * Insert a new row
         *
         * @param rownum 0-based row number
         */
        public void insertRow(int rownum) throws IOException {
            _out.write("<row r=\""+(rownum+1)+"\">\n");
            this._rownum = rownum;
        }

        /**
         * Insert row end marker
         */
        public void endRow() throws IOException {
            _out.write("</row>\n");
        }

        public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("<c r=\""+ref+"\" t=\"inlineStr\"");
            if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
            _out.write(">");
            _out.write("<is><t>"+value+"</t></is>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, String value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("<c r=\""+ref+"\" t=\"n\"");
            if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
            _out.write(">");
            _out.write("<v>"+value+"</v>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, double value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
            createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
        }
    }
}
