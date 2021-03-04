package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: luoss
 * @Date: 2021/3/4 15:39
 * @Description:
 */
public class ServerGenerator {
    static String toPath = "server\\src\\main\\java\\com\\course\\server\\service\\";


    public static void main(String[] args) throws IOException, TemplateException {
        String Domain = "Section";
        String domain = "section";
        Map<String,Object>map  = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);

        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toPath+Domain+"Service.java",map);
    }
}
