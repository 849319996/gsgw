package com.xincheng.excelupload.service;

import com.xincheng.excelupload.ExcelUpload;
import io.github.biezhi.ome.OhMyEmail;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import static io.github.biezhi.ome.OhMyEmail.SMTP_QQ;

public class FileUtil {

    public static void witer() {
        File file = null;
        FileWriter fw = null;
//        Resource resource = new ClassPathResource(ExcelUpload.path+"\\time.txt");
        file = new File(ExcelUpload.path+"/time.txt");
        try {
//            file = resource.getFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(ExcelUpload.startTime+","+ExcelUpload.index);
//            fw.write(System.currentTimeMillis() + "," + 91);
            fw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void read() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(ExcelUpload.path+"/time.txt")));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                System.out.println("s = " + s);
                String[] split = s.split(",");
                long start = Long.parseLong(split[0]);
                int index = Integer.parseInt(split[1]);

                ExcelUpload.startTime=start;
                ExcelUpload.index=index;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        witer();
//        read();

        OhMyEmail.config(SMTP_QQ(true), "15889930055@163.com", "liuji19890220");
        try {
            OhMyEmail.subject("官网询价邮件")
                    .from("客户邮箱=" )
                    .to("849319996@qq.com")
                    .html("11")
                    .send();


//            delteTempFile(toFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
