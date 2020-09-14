package com.enjoy.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Tomcat启动后会获取所有实现WebApplicationInitializer接口类，并执行接口onStartup方法.
 * 可继承抽象类AbstractAnnotationConfigDispatcherServletInitializer，指定Spring和SpringMVC配置类实现Spring自动启动。
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        System.out.println("-------Tomcat执行了方法----------");
        AnnotationConfigWebApplicationContext rootAppContext=new AnnotationConfigWebApplicationContext();
        // 这个类只配置扫描业务Bean
        rootAppContext.register(SpringConfig.class);
        if (rootAppContext != null) {
            ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
            listener.setContextInitializers(null);
            // 向servelt添加监听器ContextLoaderListener监听ServletContextEvent事件
            servletContext.addListener(listener);
        }

        AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
        // 这个类只配置扫描Controller，没有配置视图解析器等Bean,配置json序列化Bean等
        servletAppContext.register(SpringMVCConfig.class);
        ((ConfigurableWebApplicationContext)servletAppContext).setParent(rootAppContext);
        FrameworkServlet dispatcherServlet=new DispatcherServlet(servletAppContext);
        dispatcherServlet.setContextInitializers(null);

        // 向servlet添加dispatcherServlet，执行Servlet接口的init方法启动springMVC容器
        ServletRegistration.Dynamic registration  = servletContext.addServlet("dispatcherServlet",dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping(new String[]{"/"});

    }
}
