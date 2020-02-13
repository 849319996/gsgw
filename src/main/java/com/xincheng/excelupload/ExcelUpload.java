package com.xincheng.excelupload;

import com.xincheng.excelupload.model.ResultObject;
import com.xincheng.excelupload.service.FileUtil;
import com.xincheng.excelupload.service.UserService;
import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import static io.github.biezhi.ome.OhMyEmail.SMTP_QQ;


/**
 * ClassName:ExcelUpload
 * Package:com.xincheng.excelupload
 * Description: excel文件上传
 *
 * @Date:2018/11/17 0:31
 * @Author: 郑军
 */
@Controller
public class ExcelUpload {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelUpload.class);

    public  static String path="/home";

    public static List<String> list = new ArrayList<>();

    public static int allSize = 0;

    //发送下标，第三天后重置
    public static int index = 0;

    //一次发送的个数
    public int num = 1;


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/excelUpload")
    public void uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("upName") String upName,
                            @RequestParam("upEmail") String upEmail, @RequestParam("upMobile") String upMobile, HttpServletResponse response) {

        ResultObject resultObject = new ResultObject();
//        ResultObject resultObject = userService.uploadExcel(file);

        String content = "客户: " + upName + " ,邮箱: " + upEmail + " ,电话: " + upMobile;
        System.err.println("content = " + content);
        OhMyEmail.config(SMTP_QQ(true), "1218800603@qq.com", "qtfiiexukgdyhgjf");
        try {
            File toFile = multipartFileToFile(file);
            OhMyEmail.subject("官网询价邮件")
                    .from("客户邮箱=" + upEmail)
                    .to("sales@bomic.cn")
                    .html(content)
                    .attach(toFile, file.getOriginalFilename())
                    .send();


//            delteTempFile(toFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        http://elektronikasales.com/images/

    }

    @RequestMapping(value = "/download")
    public void downloadFile(HttpServletResponse response) throws IOException {
        try {
            Resource resource = new ClassPathResource("enquiry.xlsx");
            File file = new File("/home/enquiry.xlsx");
//            File file = resource.getFile();
            String filename = resource.getFilename();
            InputStream inputStream = new FileInputStream(file);
            //强制下载不打开
            response.setContentType("application/force-download");
            OutputStream out = response.getOutputStream();
            //使用URLEncoder来防止文件名乱码或者读取错误
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            int b = 0;
            byte[] buffer = new byte[1000000];
            while (b != -1) {
                b = inputStream.read(buffer);
                if (b != -1) out.write(buffer, 0, b);
            }
            inputStream.close();
            out.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> uMap = new HashMap<>();
    public static List<String> kList = new ArrayList<>();

    public static long startTime = System.currentTimeMillis();
    public static Random random = new Random();

    /**
     * 表示每隔6秒发送一次邮件
     */
//    @Scheduled(cron = "* */2 * * * ?")
    @Scheduled(cron = "0 0/5 * * * ?")
    private void proces() {

        if (allSize >= index + 1) {
            int sentCount = 0;

            int size = kList.size();
            int inx = random.nextInt(size);
            String key = kList.get(inx);
            String value = uMap.get(key);

            //继续按照下标发送
            String emailStr = list.get(index);
            index++;
            OhMyEmail.config(SMTP_QQ(false), key, value);
            try {
                OhMyEmail.subject("One-stop supplier of electronic components")
                        .from("BOHM ELECTRONICS")
                        .to(emailStr)
                        .html("<div id=\"contentDiv\" onmouseover=\"getTop().stopPropagation(event);\" onclick=\"getTop().preSwapLink(event, 'html', 'ZC2309-mqZPCZEWWMf4ckAeM1SbZa2');\" style=\"position:relative;font-size:14px;height:auto;padding:15px 15px 10px 15px;z-index:1;zoom:1;line-height:1.7;\" class=\"body\">    <div id=\"qm_con_body\"><div id=\"mailContentContainer\" class=\"qmbox qm_con_body_content qqmail_webmail_only\" style=\"\">\n" +
                                "<style>.qmbox body { line-height: 1.5; }.qmbox blockquote { margin-top: 0px; margin-bottom: 0px; margin-left: 0.5em; }.qmbox p { margin-top: 0px; margin-bottom: 0px; }.qmbox div.FoxDiv20200209121713906567 { }.qmbox body { font-size: 10.5pt; font-family: 微软雅黑; color: rgb(0, 0, 0); line-height: 1.5; }</style>\n" +
                                "<div><span></span></div><blockquote style=\"margin-Top: 0px; margin-Bottom: 0px; margin-Left: 0.5em\"><div><div class=\"FoxDiv20200209121713906567\"><blockquote style=\"margin-Top: 0px; margin-Bottom: 0px; margin-Left: 0.5em\"><div><div class=\"FoxDiv20200209105708132759\"><blockquote style=\"margin-Top: 0px; margin-Bottom: 0px; margin-Left: 0.5em\"><div><div class=\"FoxDiv20200209095800127737\"><blockquote style=\"margin-Top: 0px; margin-Bottom: 0px; margin-Left: 0.5em\"><div><div class=\"FoxDiv20200209093209864973\"><blockquote style=\"margin-Top: 0px; margin-Bottom: 0px; margin-Left: 0.5em\"><div><div class=\"FoxDiv20200208215123974570\"><div><div>Dear Sir or Madam, </div><div><div><span style=\"font-size: 10.5pt; line-height: 1.5; background-color: transparent;\"><br></span></div><div><span style=\"font-size: 10.5pt; line-height: 1.5; background-color: transparent;\">We are the most efficient and simplified compone-stop-shop BOM List Solution server. There are more than 30K + parts in stock for quick response to customers' demand. Max 2 hrs quotation time and Max 2 working days delivery for &nbsp;stocked parts are our strength, which will smooth your bussiness process and increase your business opportunity at the best!</span></div><div><span style=\"font-size: 10.5pt; line-height: 1.5; background-color: transparent;\"><br></span></div><div><span style=\"font-size: 10.5pt; line-height: 1.5; background-color: transparent;\">Meanwhile, our teamwork provide PCB &amp; PCBA sourcing, audit, SMT tracking service with IPC standard quality control. This will help you save time and efforts on both sample &amp; production of PCBA.</span></div><div><span style=\"font-size: 10.5pt; line-height: 1.5; background-color: transparent;\"><br></span></div><div><span style=\"font-size: 10.5pt; line-height: 1.5; background-color: transparent;\">We are your reliable BOM sourcing &nbsp;&amp; PCBA partner in life. The longer we can deal, the more comfortable you may feel!</span></div></div></div><div><span style=\"font-size: 10.5pt; line-height: 1.5; background-color: transparent;\"><br></span></div><div><span style=\"background-color: rgb(255, 0, 0); font-size: 24px;\">Recent promotions：</span></div><div>\n" +
                                "\n" +
                                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"534\" height=\"252\" style=\"border-collapse:collapse;width:400.52pt;\">\n" +
                                " <colgroup><col width=\"53\" style=\"width:39.75pt;\">\n" +
                                " <col width=\"202\" style=\"width:151.50pt;\">\n" +
                                " <col width=\"102\" style=\"width:76.50pt;\">\n" +
                                " <col width=\"177\" style=\"width:132.75pt;\">\n" +
                                " </colgroup><tbody><tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et6\" height=\"28\" width=\"53\" x:str=\"\" style=\"height: 21pt; width: 39.75pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-color: rgb(146, 208, 80); background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">##</td>\n" +
                                "  <td class=\"et6\" width=\"202\" x:str=\"\" style=\"width: 151.5pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-color: rgb(146, 208, 80); background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">P/N</td>\n" +
                                "  <td class=\"et6\" width=\"102\" x:str=\"\" style=\"width: 76.5pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-color: rgb(146, 208, 80); background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">Manufacture</td>\n" +
                                "  <td class=\"et6\" width=\"177\" x:str=\"\" style=\"width: 132.75pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-color: rgb(146, 208, 80); background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">Unit price(USD)</td>\n" +
                                " </tr>\n" +
                                " <tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et7\" height=\"28\" x:num=\"1\" style=\"height: 21pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center;\">1</td>\n" +
                                "  <td class=\"et8\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">CC0805KRX7R9BB684</td>\n" +
                                "  <td class=\"et9\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">YAGEO</td>\n" +
                                "  <td class=\"et10\" x:num=\"0.006428571\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">0.0064</td>\n" +
                                " </tr>\n" +
                                " <tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et7\" height=\"28\" x:num=\"2\" style=\"height: 21pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center;\">2</td>\n" +
                                "  <td class=\"et8\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">CC0805KRX7R9BB683</td>\n" +
                                "  <td class=\"et9\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">YAGEO</td>\n" +
                                "  <td class=\"et10\" x:num=\"0.004\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">0.0040</td>\n" +
                                " </tr>\n" +
                                " <tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et7\" height=\"28\" x:num=\"3\" style=\"height: 21pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center;\">3</td>\n" +
                                "  <td class=\"et8\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">CL21A106KOFNNNE</td>\n" +
                                "  <td class=\"et9\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">SAMSUNG</td>\n" +
                                "  <td class=\"et10\" x:num=\"0.005714286\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">0.0057</td>\n" +
                                " </tr>\n" +
                                " <tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et7\" height=\"28\" x:num=\"4\" style=\"height: 21pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center;\">4</td>\n" +
                                "  <td class=\"et11\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">BC847C</td>\n" +
                                "  <td class=\"et12\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">SEMTECH</td>\n" +
                                "  <td class=\"et10\" x:num=\"0.002857143\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">0.0029</td>\n" +
                                " </tr>\n" +
                                " <tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et7\" height=\"28\" x:num=\"5\" style=\"height: 21pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center;\">5</td>\n" +
                                "  <td class=\"et11\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">BC857C</td>\n" +
                                "  <td class=\"et12\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">SEMTECH</td>\n" +
                                "  <td class=\"et10\" x:num=\"0.002857143\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">0.0029</td>\n" +
                                " </tr>\n" +
                                " <tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et7\" height=\"28\" x:num=\"6\" style=\"height: 21pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center;\">6</td>\n" +
                                "  <td class=\"et11\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">BC817-40</td>\n" +
                                "  <td class=\"et12\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">SEMTECH</td>\n" +
                                "  <td class=\"et10\" x:num=\"0.004285714\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">0.0043</td>\n" +
                                " </tr>\n" +
                                " <tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et7\" height=\"28\" x:num=\"7\" style=\"height: 21pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center;\">7</td>\n" +
                                "  <td class=\"et11\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">IR333C-A </td>\n" +
                                "  <td class=\"et12\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">EVERLIGHT</td>\n" +
                                "  <td class=\"et13\" x:num=\"0.014285714\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 12pt; font-family: 宋体; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: bottom; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">0.014 </td>\n" +
                                " </tr>\n" +
                                " <tr height=\"28\" style=\"height:21.00pt;\">\n" +
                                "  <td class=\"et7\" height=\"28\" x:num=\"8\" style=\"height: 21pt; padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center;\">8</td>\n" +
                                "  <td class=\"et11\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">PD438B </td>\n" +
                                "  <td class=\"et12\" x:str=\"\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 10pt; font-family: 'Bookman Old Style'; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: middle; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">EVERLIGHT</td>\n" +
                                "  <td class=\"et13\" x:num=\"0.047142857\" style=\"padding-top: 1px; padding-left: 1px; padding-right: 1px; font-size: 12pt; font-family: 宋体; border: 0.5pt solid rgb(0, 0, 0); border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; vertical-align: bottom; white-space: nowrap; text-align: center; background-image: initial; background-attachment: initial; background-size: initial; background-origin: initial; background-clip: initial; background-position: initial; background-repeat: initial;\">0.047 </td>\n" +
                                " </tr>\n" +
                                "</tbody></table>\n" +
                                "</div>\n" +
                                "<div><br></div><hr style=\"width: 210px; height: 1px;\" color=\"#b5c4df\" size=\"1\" align=\"left\">\n" +
                                "<div><span><div style=\"margin: 10px;\"><div style=\"font-family: 微软雅黑; font-size: 14px; line-height: 21px;\"><span style=\"font-size: 13.3333px; line-height: 20px;\"><b>Regards, </b></span></div><div style=\"font-family: 微软雅黑; font-size: 14px; line-height: 21px;\"><span style=\"font-size: 13.3333px; line-height: 20px;\"><b>&nbsp;</b></span></div><div style=\"font-family: 微软雅黑; font-size: 14px; line-height: 21px;\"><span style=\"font-size: 13.3333px; line-height: 20px;\"><b>Joe Liu</b></span></div><div style=\"font-family: 微软雅黑; font-size: 14px; line-height: 21px;\"><span style=\"font-size: 13.3333px; line-height: 20px;\"><b>HONG KONG BOHM ELECTRONICS LIMITED</b></span></div><div><p class=\"p0\" style=\"font-family: 微软雅黑; line-height: 12.5pt; font-size: 14px; color: rgb(255, 0, 0); margin-top: 0px; margin-bottom: 0px;\"><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0); font-size: 10.5pt;\"><span style=\"line-height: 23px;\"><font color=\"#008000\"><span style=\"color: rgb(0, 0, 0);\"><b>Mobile/wechat: <span class=\"js-phone-number\"><span style=\"border-bottom:1px dashed #ccc;z-index:1\" t=\"7\" onclick=\"return false;\" data=\"+86-15302733392\">+86-15302733392</span></span></b></span></font></span></span></p><p class=\"p0\" style=\"font-family: 微软雅黑; line-height: 12.5pt; font-size: 14px; color: rgb(255, 0, 0); margin-top: 0px; margin-bottom: 0px;\"><b><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0); font-size: 10.5pt;\"></span><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0); font-size: 10.5pt;\">Tel</span><span style=\"color: rgb(0, 0, 0); font-size: 10.5pt;\">：</span><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0); font-size: 10.5pt;\">86-</span><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0); font-size: 10.5pt;\">755</span><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0); font-size: 10.5pt;\">-</span><span style=\"color: rgb(44, 43, 44); font-size: 13px; font-variant-ligatures: normal; orphans: 2; widows: 2; background-color: transparent;\">&nbsp;<span style=\"border-bottom:1px dashed #ccc;z-index:1\" t=\"7\" onclick=\"return false;\" data=\"3368-0782\">3368-0782</span></span><span style=\"color: rgb(44, 43, 44); font-size: 13px; font-variant-ligatures: normal; orphans: 2; widows: 2; background-color: transparent;\">&nbsp;</span></b></p><p class=\"p0\" style=\"margin-top: 0px; margin-bottom: 0px;\"><span style=\"background-color: transparent; line-height: 16.6667px;\"><b>Website: </b></span><span style=\"background-color: transparent; line-height: 16.6667px;\"><b><a href=\"http://www.bomic.cn\" rel=\"noopener\" target=\"_blank\">www.bomic.cn</a></b></span></p><p class=\"p0\" style=\"font-family: 微软雅黑; line-height: 12.5pt; font-size: 14px; color: rgb(255, 0, 0); margin-top: 0px; margin-bottom: 0px;\"><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0); font-size: 10.5pt;\"><b>QQ: <span style=\"border-bottom:1px dashed #ccc;z-index:1\" t=\"7\" onclick=\"return false;\" data=\"383636522\">383636522</span></b></span></p><p class=\"p0\" style=\"font-family: 微软雅黑; line-height: 12.5pt; color: rgb(255, 0, 0); margin-top: 0px; margin-bottom: 0px;\"><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0);\"><b style=\"color: rgb(255, 0, 0); line-height: 16.6667px;\"><span lang=\"EN-US\" style=\"color: rgb(0, 0, 0);\"><span style=\"line-height: normal; text-align: justify;\">E-Mail/Skype</span><span style=\"line-height: normal; text-align: justify;\">：</span></span><span style=\"line-height: normal; white-space: nowrap; color: rgb(0, 0, 0); background-color: transparent;\"></span><a href=\"mailto:sales@bomic.cn\" style=\"line-height: 21px; background-color: transparent;\" rel=\"noopener\" target=\"_blank\">sales@bomic.cn</a><span style=\"color: rgb(0, 0, 0); line-height: 21px; background-color: rgba(0, 0, 0, 0);\">&nbsp;</span></b></span></p><p class=\"p0\" style=\"font-family: 微软雅黑; line-height: 12.5pt; font-size: 14px; color: rgb(255, 0, 0); margin-top: 0px; margin-bottom: 0px;\"><img src=\"/cgi-bin/viewfile?f=F4C684F88DAF4EB66BC1A1706537D78EDBFFF856DB01DBA351576B2B802EB2212797355CF3DBA3091B36F96F8DF7BBC6E7535D778EA0A145D7A3E4C7B8F66317BBFFCD043EC746AF79EA77EA1CA70EA8B965096019AE91A66EB3C053F5AD3D2F&amp;mailid=ZC2309-mqZPCZEWWMf4ckAeM1SbZa2&amp;sid=sIwxZ-_IZPvEOghW&amp;net=889192575\" border=\"0\"></p></div></div></span></div>\n" +
                                "</div></div></blockquote>\n" +
                                "</div></div></blockquote>\n" +
                                "</div></div></blockquote>\n" +
                                "</div></div></blockquote>\n" +
                                "</div></div></blockquote>\n" +
                                "\n" +
                                "<style type=\"text/css\">.qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {display: none !important;}</style></div></div><!-- --><style>#mailContentContainer .txt {height:auto;}</style>  </div>")
//                        .attach(toFile, file.getOriginalFilename())
                        .send();

                FileUtil.witer();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOGGER.info("发送定时邮件成功="+emailStr+" ,index="+index);
        } else {
            //重置天数

            long now = System.currentTimeMillis();
            int diff = differentDaysByMillisecond(startTime, now);
            if(diff>=30){
                index=0;
                LOGGER.info("重置天数,重新发送邮寄");
                ExcelUpload.startTime=now;
                FileUtil.witer();
            }
        }
    }

    /**
     * 2个时间的相隔天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(long date1, long date2) {
        int days = (int) Math.abs(((date2- date1) / (1000 * 3600 * 24)));
        return days;
    }


    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }

    }

}
