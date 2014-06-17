package info.tongrenlu.support;

import java.util.List;

public class PaginateSupport {

    private Integer page = null;

    private Integer size = null;

    private Integer itemCount = null;

    private List<?> items = null;

    private int pagenum = 0;

    private int start = 0;

    private int end = 0;

    public void compute() {
        if (this.page == null || this.page <= 0) {
            this.page = 1;
        }
        if (this.size == null || this.size <= 0) {
            this.size = 10;
        }
        if (this.itemCount == null) {
            this.itemCount = 0;
        }
        this.pagenum = this.itemCount / this.size
                       + (this.itemCount % this.size == 0 ? 0 : 1);

        this.start = (this.page - 1) * this.size + 1;
        this.end = this.page * this.size;
    }

    public boolean isFirst() {
        return this.page == 1;
    }

    public boolean isLast() {
        return this.page == this.pagenum;
    }

    public int getPagenum() {
        return this.pagenum;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(final int itemCount) {
        this.itemCount = itemCount;
    }

    public List<?> getItems() {
        return this.items;
    }

    public void setItems(final List<?> items) {
        this.items = items;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

}
