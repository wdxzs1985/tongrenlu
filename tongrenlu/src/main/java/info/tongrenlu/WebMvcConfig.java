package info.tongrenlu;

import info.tongrenlu.exception.ForbiddenException;
import info.tongrenlu.exception.PageNotFoundException;
import info.tongrenlu.interceptor.AccessInterceptor;
import info.tongrenlu.interceptor.AutoLoginInterceptor;
import info.tongrenlu.interceptor.ConsoleAuthInterceptor;
import info.tongrenlu.interceptor.EditAdminAuthInterceptor;
import info.tongrenlu.interceptor.GuestAuthInterceptor;
import info.tongrenlu.interceptor.ShopAdminAuthInterceptor;
import info.tongrenlu.interceptor.TimerInterceptor;
import info.tongrenlu.interceptor.UserAdminAuthInterceptor;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
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

    @Value("${file.outputPath}")
    private String outputPath = null;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/files/**")) {
            registry.addResourceHandler("/files/**").addResourceLocations("file:" + this.outputPath);
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
    private EditAdminAuthInterceptor editAdminAuthInterceptor = null;
    @Autowired
    private ShopAdminAuthInterceptor shopAdminAuthInterceptor = null;
    @Autowired
    private UserAdminAuthInterceptor userAdminAuthInterceptor = null;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.timerInterceptor).addPathPatterns("/**").excludePathPatterns("/error");
        registry.addInterceptor(this.accessInterceptor).addPathPatterns("/**").excludePathPatterns("/error");
        registry.addInterceptor(this.autoLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(WebMvcConfig.EXCLUDE_PATH);
        registry.addInterceptor(this.guestAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(WebMvcConfig.EXCLUDE_PATH);
        registry.addInterceptor(this.consoleAuthInterceptor).addPathPatterns("/console/**", "/shop/**");
        registry.addInterceptor(this.editAdminAuthInterceptor).addPathPatterns("/admin/music", "/admin/comment");
        registry.addInterceptor(this.shopAdminAuthInterceptor).addPathPatterns("/admin/shop");
        registry.addInterceptor(this.userAdminAuthInterceptor).addPathPatterns("/admin/user");
    }

    @Autowired
    private MessageSource messageSource = null;

    @ExceptionHandler(PageNotFoundException.class)
    public ModelAndView handlePageNotFoundException(final PageNotFoundException ex) {
        String message = ex.getMessage();
        if (StringUtils.isBlank(message)) {
            message = this.messageSource.getMessage("error.pageNotFound", null, null);
        }
        final ModelAndView model = new ModelAndView("error", Collections.singletonMap("error", message));
        return model;
    }

    @ExceptionHandler(ForbiddenException.class)
    public ModelAndView handleForbiddenException(final ForbiddenException ex) {
        String message = ex.getMessage();
        if (StringUtils.isBlank(message)) {
            message = this.messageSource.getMessage("error.forbidden", null, null);
        }
        final ModelAndView model = new ModelAndView("error", Collections.singletonMap("error", message));
        return model;
    }
}
