package com.lam.fileword.controller;

import cn.hutool.core.io.resource.Resource;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.io.resource.ClassPathResource;

import java.io.*;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
@Slf4j
public class First {
    public static final String TEMPLATE_PATH = "static/simple.docx";
    @GetMapping("/sayhi")
    public String sayHi(HttpServletResponse response) { //还可以在这里传参数 将请求到的数据传参过来，再通过map整理，最后返回一份word文档
        //获取到文件路径
        ClassPathResource resource = new ClassPathResource(TEMPLATE_PATH);
        log.info(resource.getAbsolutePath());
        try {
            //这里存入数据 hashmap类型
            XWPFTemplate template = XWPFTemplate.compile(resource.getAbsolutePath()).render(
                    new HashMap<String, Object>() {{
                        put("title", "Hi, poi-tl Word模板引擎");
                    }});
            //生成一个路径存放生成的模版
//            File f1 = new File("static/"+File.separator);
//            ClassPathResource out = new ClassPathResource("static/");
            //这样的static会指向生成的目标的文件夹
//            template.writeAndClose(new FileOutputStream(out.getAbsolutePath()+"/output.docx"));
            //设置网络流输出
            response.setContentType("application/octet-stream");
            //out_template.docx这里可以设置成动态的 这个就是浏览器下载文件时的文件名
            response.setHeader("Content-disposition","attachment;filename=\""+"out_template.docx"+"\"");
            /*getOutputStream() 方法返回一个 ServletOutputStream，它是 OutputStream 的子类，专门用于处理HTTP响应的二进制数据。
            * 通过这个输出流，你可以将任何类型的二进制数据（例如文件内容、图片等）发送给客户端。
            * 下面是一个简单的例子，展示了如何使用这个输出流来发送文件内容给客户端：
            *
            * */
            OutputStream out2 = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out2);
            template.write(bos);
            bos.flush();
            out2.flush();
            //关闭输入输出流
            PoitlIOUtils.closeQuietlyMulti(template, bos, out2);
            System.out.println(File.separator); //就是/ 斜杆
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (resource.exists()) {
//            System.out.println("file exists");
//
//            // 如果需要读取文件内容，可以这样操作：
//            try (InputStream in = resource.getStream()) {
//                // 这里可以处理输入流，例如转换为字符串或者保存到另一个地方
//                // 注意：这只是一个例子，根据实际情况调整
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("file not exists");
//        }

        return "hi springboot";
    }
}
