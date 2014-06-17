package info.tongrenlu.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class PlaylistBean extends ArticleBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1084136374956711804L;

}
