package com.lam.fileword.controller;

import cn.hutool.core.io.resource.Resource;
import com.deepoove.poi.XWPFTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.io.resource.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
@Slf4j
public class First {
    public static final String TEMPLATE_PATH = "static/simple.docx";
    @GetMapping("/sayhi")
    public String sayHi() {
        //获取到文件
        ClassPathResource resource = new ClassPathResource(TEMPLATE_PATH);
        log.info(resource.getAbsolutePath());
        try {
            XWPFTemplate template = XWPFTemplate.compile(resource.getAbsolutePath()).render(
                    new HashMap<String, Object>() {{
                        put("title", "Hi, poi-tl Word模板引擎");
                    }});
            //生成一个路径存放生成的模版
            File f1 = new File("static/"+File.separator);
            ClassPathResource out = new ClassPathResource("static/");
            template.writeAndClose(new FileOutputStream(out.getAbsolutePath()+"/output.docx"));
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
