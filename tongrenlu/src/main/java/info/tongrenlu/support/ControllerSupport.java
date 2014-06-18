package info.tongrenlu.support;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class ControllerSupport {
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }

    protected String decodeQuery(final String q) {
        // if (StringUtils.isBlank(q)) {
        // return null;
        // }
        // try {
        // return new String(q.getBytes("ISO-8859-1"), "UTF-8");
        // } catch (final UnsupportedEncodingException e) {
        // return null;
        // }
        return q;
    }
}
