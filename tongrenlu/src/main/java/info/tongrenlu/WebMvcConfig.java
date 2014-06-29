package info.tongrenlu;

import info.tongrenlu.interceptor.AccessInterceptor;
import info.tongrenlu.interceptor.AdminAuthInterceptor;
import info.tongrenlu.interceptor.AutoLoginInterceptor;
import info.tongrenlu.interceptor.ConsoleAuthInterceptor;
import info.tongrenlu.interceptor.GuestAuthInterceptor;
import info.tongrenlu.interceptor.TimerInterceptor;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ControllerAdvice
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    public static final String[] EXCLUDE_PATH = { "/signin",
                                                 "/signin/salt",
                                                 "/signout",
                                                 "/signup",
                                                 "/signup/**",
                                                 "/forgot",
                                                 "/forgot/**" };

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }

    @Value("${file.outputPathWindows}")
    private String outputPathWindows = null;

    @Value("${file.outputPathLinux}")
    private String outputPathLinux = null;

    public String getOutputPath() {
        return SystemUtils.IS_OS_WINDOWS ? this.outputPathWindows
                                        : this.outputPathLinux;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/files/**")) {
            registry.addResourceHandler("/files/**")
                    .addResourceLocations("file:" + this.getOutputPath());
        }
    }

    @Autowired
    private TimerInterceptor timerInterceptor = null;
    @Autowired
    private AccessInterceptor accessInterceptor = null;
    @Autowired
    private AutoLoginInterceptor autoLoginInterceptor = null;
    @Autowired
    private GuestAuthInterceptor guestAuthInterceptor = null;
    @Autowired
    private ConsoleAuthInterceptor consoleAuthInterceptor = null;
    @Autowired
    private AdminAuthInterceptor adminAuthInterceptor = null;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.timerInterceptor).addPathPatterns("/**");
        registry.addInterceptor(this.accessInterceptor).addPathPatterns("/**");
        registry.addInterceptor(this.autoLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(WebMvcConfig.EXCLUDE_PATH);
        registry.addInterceptor(this.guestAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(WebMvcConfig.EXCLUDE_PATH);
        registry.addInterceptor(this.consoleAuthInterceptor)
                .addPathPatterns("/console/**");
        registry.addInterceptor(this.adminAuthInterceptor)
                .addPathPatterns("/admin/**");
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setForceEncoding(true);
        filter.setEncoding("UTF-8");
        return filter;
    }

}
