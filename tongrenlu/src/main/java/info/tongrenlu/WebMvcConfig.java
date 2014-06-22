package info.tongrenlu;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ControllerAdvice
public class WebMvcConfig extends WebMvcConfigurerAdapter {

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
}
