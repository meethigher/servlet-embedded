package top.meethigher.springbootservlet.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.meethigher.servletembedded.sysparams.SysParamsServlet;
import top.meethigher.servletembedded.sysparams.repository.SysParamsRepository;
import top.meethigher.servletembedded.sysparams.repository.impl.DefaultSysParamsRepository;

import javax.servlet.Servlet;
import javax.sql.DataSource;

/**
 * @author chenchuancheng
 * @since 2021/12/28 14:54
 */
@Configuration
public class SysParamsServletConfig {


    @Bean
    public SysParamsRepository sysParamsRepository(DataSource dataSource) {
        return new DefaultSysParamsRepository(dataSource);
    }

    @Bean
    public ServletRegistrationBean chenServlet(SysParamsRepository repository) {
        ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<>();
        bean.setName("sysParams");
        SysParamsServlet servlet = new SysParamsServlet(repository);
        bean.setServlet(servlet);
        bean.addUrlMappings("/sysParams/*");
        return bean;
    }
}
