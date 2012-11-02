package com.fuyou.util;

import com.google.common.collect.Lists;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: yubaofu Date: 12-10-15 Time: 下午3:23 To change this template use File | Settings |
 * File Templates.
 */
public class ExcelWriter {
    private static final Log log = LogFactory.getLog(ExcelWriter.class);

    public interface ExcelOperation {
        public void setHeader(WritableSheet sheet) throws WriteException;

        public void addData(WritableSheet sheet) throws WriteException;

        public void setColumnWidth(WritableSheet sheet);
    }

    public void toExcel(String shellName, OutputStream outputStream, ExcelOperation excelOperation) throws IOException,
            WriteException {
        WorkbookSettings ws = new WorkbookSettings();
        WritableWorkbook workbook = Workbook.createWorkbook(outputStream, ws);
        WritableSheet sheet = workbook.createSheet(shellName, 0);
        try {
            excelOperation.setHeader(sheet);
            excelOperation.addData(sheet);
            excelOperation.setColumnWidth(sheet);
            workbook.write();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            workbook.close();
        }
    }

    public static final WritableCellFormat WRAPPEDTEXT = new WritableCellFormat();
    public static final jxl.write.NumberFormat NF = new jxl.write.NumberFormat("#");
    public static final WritableCellFormat NUMBER = new WritableCellFormat(NF);
    public static final WritableFont FONT = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
    public static final WritableCellFormat DEFAULT_FORMAT = new WritableCellFormat(FONT);

    static {
        try {
            DEFAULT_FORMAT.setVerticalAlignment(VerticalAlignment.CENTRE);
            DEFAULT_FORMAT.setAlignment(Alignment.CENTRE);
        } catch (WriteException e) {
            log.error(e.getMessage(), e);
        }

    }

    public static void main(String[] args) throws Exception {
        String fileName = URLEncoder.encode("om", "utf-8") + "_"
                + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        //response.setContentType("application/msexcel;");
        //response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");

        ExcelWriter ew = new ExcelWriter();
        ew.toExcel("无线返佣订单", null, new ExcelWriter.ExcelOperation() {
            @Override
            public void setHeader(WritableSheet sheet) throws WriteException {
                sheet.addCell(new Label(0, 0, "订单号/酒店名称", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(1, 0, "房型", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(2, 0, "客人姓名", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(3, 0, "入住时间", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(4, 0, "离店时间", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(5, 0, "间", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(6, 0, "预订时间", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(7, 0, "订单状态", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(8, 0, "联系人姓名", ExcelWriter.DEFAULT_FORMAT));
                sheet.addCell(new Label(9, 0, "联系人手机", ExcelWriter.DEFAULT_FORMAT));
            }

            @Override
            public void addData(WritableSheet sheet) throws WriteException {
                int row = 1;
                for (Map<String, Object> map : new HashSet<Map<String, Object>>()) {
                    List<WritableCell> cells = Lists.newArrayList();
                    int column = 0;
                    cells.add(new Label(column++, row, map.get("orderNo")
                            + map.get("hotelName").toString(), ExcelWriter.WRAPPEDTEXT));// 订单号/酒店
                    cells.add(new Label(column++, row, map.get("roomName").toString(), ExcelWriter.WRAPPEDTEXT));// 房型
                    cells.add(new Label(column++, row, (String) map.get("customerNames"), ExcelWriter.WRAPPEDTEXT));// 客人姓名
                    cells.add(new Label(column++, row, (String) map.get("fromDate"), ExcelWriter.WRAPPEDTEXT));// 入住
                    cells.add(new Label(column++, row, (String) map.get("toDate"), ExcelWriter.WRAPPEDTEXT));// 离店
                    cells.add(new jxl.write.Number(column++, row, ((Integer) map.get("roomNum")).intValue(),
                            ExcelWriter.NUMBER));// 间
                    cells.add(new Label(column++, row, (String) map.get("orderDate"), ExcelWriter.WRAPPEDTEXT));// 预订时间
                    cells.add(new Label(column++, row, (String) map.get("status"), ExcelWriter.WRAPPEDTEXT));// 状态
                    cells.add(new Label(column++, row, (String) map.get("contactName"), ExcelWriter.WRAPPEDTEXT));// 联系人姓名
                    cells.add(new Label(column++, row, (String) map.get("contactMobile"), ExcelWriter.WRAPPEDTEXT));// 联系人手机
                    for (WritableCell cell : cells) {
                        sheet.addCell(cell);
                    }
                    row++;
                }
            }

            @Override
            public void setColumnWidth(WritableSheet sheet) {
                sheet.setColumnView(0, 40);// A
                sheet.setColumnView(1, 30);// B
                sheet.setColumnView(2, 10);// C
                sheet.setColumnView(3, 10);// D
                sheet.setColumnView(4, 10);// E
                sheet.setColumnView(5, 5);// F
                sheet.setColumnView(6, 15);
                sheet.setColumnView(7, 10);
                sheet.setColumnView(8, 15);
            }
        });

    }


}
