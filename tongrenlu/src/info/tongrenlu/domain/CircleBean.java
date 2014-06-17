package info.tongrenlu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class CircleBean {

    private String circleId = null;

    private String name = null;

    private String link = null;

    private String penName = null;

    private String area1 = null;

    private String area2 = null;

    private String area3 = null;

    private TagBean tagBean = null;

    private int userCount = 0;

    private int userCount2 = 0;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public String getPenName() {
        return this.penName;
    }

    public void setPenName(final String penName) {
        this.penName = penName;
    }

    public String getArea1() {
        return this.area1;
    }

    public void setArea1(final String area1) {
        this.area1 = area1;
    }

    public String getArea2() {
        return this.area2;
    }

    public void setArea2(final String area2) {
        this.area2 = area2;
    }

    public String getArea3() {
        return this.area3;
    }

    public void setArea3(final String area3) {
        this.area3 = area3;
    }

    public TagBean getTagBean() {
        return this.tagBean;
    }

    public void setTagBean(final TagBean tagBean) {
        this.tagBean = tagBean;
    }

    public String getCircleId() {
        return this.circleId;
    }

    public void setCircleId(final String circleId) {
        this.circleId = circleId;
    }

    public int getUserCount() {
        return this.userCount;
    }

    public void setUserCount(final int userCount) {
        this.userCount = userCount;
    }

    public int getUserCount2() {
        return this.userCount2;
    }

    public void setUserCount2(final int userCount2) {
        this.userCount2 = userCount2;
    }
}
