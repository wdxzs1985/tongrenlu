package info.tongrenlu.www;

import info.tongrenlu.domain.AuthFileBean;
import info.tongrenlu.domain.MusicBean;
import info.tongrenlu.domain.UserBean;
import info.tongrenlu.service.ConsoleLibraryService;
import info.tongrenlu.support.PaginateSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes("LOGIN_USER")
@Transactional
public class ConsoleLibraryController {

    public static final int PAGE_SIZE = 12;

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private ConsoleLibraryService libraryService = null;

    @RequestMapping(method = RequestMethod.GET, value = "/console/library")
    public String doGetIndex(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                             @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                             final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber,
                                                                      PAGE_SIZE);
        page.addParam("userBean", loginUser);
        page.addParam("status", 1);
        this.libraryService.searchLibrary(page);
        model.addAttribute("page", page);
        return "console/user/library";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/auth")
    public String doGetAuth(@RequestParam(value = "p", defaultValue = "1") final Integer pageNumber,
                            @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                            final Model model) {
        final PaginateSupport<MusicBean> page = new PaginateSupport<>(pageNumber,
                                                                      PAGE_SIZE);
        page.addParam("userBean", loginUser);
        page.addParam("status", 0);
        this.libraryService.searchLibrary(page);
        model.addAttribute("page", page);
        return "console/user/auth";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/auth/upload")
    public String doGetAuthUpload(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                  final Model model) {
        return "console/user/auth_upload";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/console/auth/file")
    @ResponseBody
    public Map<String, Object> doGetAuthFile(@ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                             final Locale locale) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
        final List<AuthFileBean> fileBeanList = this.libraryService.getAuthFiles(loginUser);
        for (final AuthFileBean authFileBean : fileBeanList) {
            final Map<String, Object> fileModel = new HashMap<String, Object>();
            fileModel.put("id", authFileBean.getId());
            fileModel.put("status", authFileBean.getStatus());
            fileModel.put("userId", authFileBean.getUserBean().getId());
            files.add(fileModel);
        }
        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/auth/file")
    @ResponseBody
    public Map<String, Object> doPostAuthFile(@RequestParam(value = "files[]") final MultipartFile[] uploads,
                                              @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                              final Locale locale) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final List<Map<String, Object>> files = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(uploads)) {
            for (final MultipartFile upload : uploads) {
                final Map<String, Object> fileModel = new HashMap<String, Object>();
                AuthFileBean authFileBean = this.libraryService.saveAuthFile(upload,
                                                                             loginUser,
                                                                             fileModel,
                                                                             locale);
                if (authFileBean != null) {
                    fileModel.put("id", authFileBean.getId());
                    fileModel.put("status", authFileBean.getStatus());
                    fileModel.put("userId", loginUser.getId());
                }
                files.add(fileModel);
            }
        }
        model.put("files", files);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/console/auth/file/{authFileId}/delete")
    @ResponseBody
    public Map<String, Object> doPostDeleteAuthFile(@PathVariable final Integer authFileId,
                                                    @ModelAttribute("LOGIN_USER") final UserBean loginUser,
                                                    final Locale locale) {
        final Map<String, Object> model = new HashMap<String, Object>();
        this.libraryService.delete(authFileId, loginUser);
        model.put("true", true);
        return model;
    }
}
