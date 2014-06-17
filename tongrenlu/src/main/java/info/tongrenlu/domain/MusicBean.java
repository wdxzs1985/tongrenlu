package info.tongrenlu.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class MusicBean extends ArticleBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9222246466457163515L;

}
