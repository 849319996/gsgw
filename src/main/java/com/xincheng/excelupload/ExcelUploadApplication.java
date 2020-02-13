package com.xincheng.excelupload;

import com.xincheng.excelupload.service.FileUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

@SpringBootApplication
@EnableScheduling
public class ExcelUploadApplication {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUploadApplication.class);

    public static void main(String[] args) {


        SpringApplication.run(ExcelUploadApplication.class, args);
        logger.info("---------项目启动成功-----------");
        readExcel();
        logger.info("---------读取邮件地址个数="+ExcelUpload.list.size());

        ExcelUpload.uMap.put("1218800603@qq.com","qtfiiexukgdyhgjf");
        ExcelUpload.uMap.put("849319996@qq.com","vvvtdgvendmgbbfg");
        ExcelUpload.uMap.put("383636522@qq.com","joscdwwzkmzibhcb");
        ExcelUpload.uMap.put("1729998911@qq.com","ltgakeqyhxggeajh");
        ExcelUpload.uMap.put("785544199@qq.com","hxluhrlglgfubcdg");
        ExcelUpload.uMap.put("2689115066@qq.com","oevnmbtfmbbmddah");
//        ExcelUpload.uMap.put("15889930055@163.com","liuji19890220");

        ExcelUpload.kList.add("1218800603@qq.com");
        ExcelUpload.kList.add("849319996@qq.com");
        ExcelUpload.kList.add("383636522@qq.com");
        ExcelUpload.kList.add("1729998911@qq.com");
        ExcelUpload.kList.add("785544199@qq.com");
        ExcelUpload.kList.add("2689115066@qq.com");
//        ExcelUpload.kList.add("15889930055@163.com");

        ExcelUpload.startTime=System.currentTimeMillis();

        FileUtil.read();


    }

    public static void readExcel() {
        try {
//            Resource resource = new ClassPathResource("1.xlsx");
            File file = new File(ExcelUpload.path+"/1.xlsx");
//            File file = resource.getFile();
            // 工作表
            Workbook workbook = WorkbookFactory.create(file);

            // 表个数。
            int numberOfSheets = workbook.getNumberOfSheets();

            // 遍历表。
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);

                // 行数。
                int rowNumbers = sheet.getLastRowNum() + 1;

                // Excel第一行。
                Row temp = sheet.getRow(0);
                if (temp == null) {
                    continue;
                }

                int cells = temp.getPhysicalNumberOfCells();

                // 读数据。
                for (int row = 0; row < rowNumbers; row++) {
                    Row r = sheet.getRow(row);
                    for (int col = 0; col < cells; col++) {
                        String s = r.getCell(col).toString();
                        if(s!=null && s.length()>=0){
                            ExcelUpload.list.add(s);
                        }
                    }
                }
            }
           ExcelUpload.allSize= ExcelUpload.list.size();

            Runtime.getRuntime().addShutdownHook(new Thread(()-> {
                FileUtil.witer();
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
