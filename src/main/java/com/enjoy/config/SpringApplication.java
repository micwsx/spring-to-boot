package com.enjoy.config;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class SpringApplication {
    public static void run(){
            runTomcat();
//        createStandardTomcat();
        //createSimpleTomcat();
    }

    private static void createStandardTomcat(){
        try {
            Tomcat tomcat=new Tomcat();
            tomcat.setPort(9091);
            String basePath=System.getProperty("user.dir")+ File.separator+"springtoboot"+File.separator;
            tomcat.getHost().setAppBase(basePath);
            String resourcesPath=basePath + "src" + File.separator + "main" + File.separator + "resources";
            // 改变文件读取路径，从resources目录下去取文件
            StandardContext ctx=(StandardContext)tomcat.addWebapp("/", resourcesPath);
            // 禁止重新载入
            ctx.setReloadable(false);
            // class文件读取地址
            File additionWebInfClasses = new File("springtoboot/target/classes");
            // 创建WebRoot
            WebResourceRoot resources = new StandardRoot(ctx);
            // tomcat内部读取Class执行
            resources.addPreResources(new DirResourceSet(resources, "/springtoboot/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
            tomcat.start();
            // 异步等待请求执行
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    private static void createSimpleTomcat() {
        Tomcat tomcat=new Tomcat();
        try {
            tomcat.setPort(9090);
            tomcat.start();
            // 指定工作目录C
           //tomcat.addContext("/", "d:\\test\\");
            tomcat.addWebapp("/", "C:\\test\\");
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        // Wrapper wrapper= tomcat.addServlet("/", "dispatcher", new DispatcherServlet());
        // wrapper.setLoadOnStartup(1);
        // wrapper.addMapping("/");
        //
        // 阻塞等待请求
        tomcat.getServer().await();
    }

    public static void runTomcat() {

        try {
            // 创建Tomcat容器
            Tomcat tomcatServer = new Tomcat();
            // 端口号设置
            tomcatServer.setPort(9091);
            // 读取项目路径 加载静态资源
            String basePath = System.getProperty("user.dir") + File.separator ;
            tomcatServer.getHost().setAppBase(basePath);

            //改变文件读取路径，从resources目录下去取文件
            StandardContext ctx = (StandardContext) tomcatServer.addWebapp("/", basePath + "src" + File.separator + "main" + File.separator + "resources");
            // 禁止重新载入
            ctx.setReloadable(false);
            // class文件读取地址
            File additionWebInfClasses = new File("spring-to-boot/target/classes");
            // 创建WebRoot
            WebResourceRoot resources = new StandardRoot(ctx);
            // tomcat内部读取Class执行
            resources.addPreResources(
                    new DirResourceSet(resources, "/spring-to-boot/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
            tomcatServer.start();
            // 异步等待请求执行
            tomcatServer.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }



}
