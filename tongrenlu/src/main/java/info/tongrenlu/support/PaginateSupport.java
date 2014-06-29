package info.tongrenlu.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginateSupport<T> {

    public static int PAGESIZE = 10;

    private int pageNumber = 1;

    private int pageSize = PaginateSupport.PAGESIZE;

    private int pageCount = 0;

    private int itemCount;

    private List<T> items;

    private Map<String, Object> params;

    public PaginateSupport(final Integer pageNumber) {
        this(pageNumber, 10);
    }

    public PaginateSupport(final Integer pageNumber, final Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.itemCount = 0;
        this.items = new ArrayList<>();
        this.params = new HashMap<>();
    }

    public void compute() {
        // init
        this.itemCount = Math.max(this.itemCount, 0);
        this.pageNumber = Math.max(this.pageNumber, 0);
        this.pageSize = Math.max(this.pageSize, 10);
        this.pageCount = 0;

        // pageCount
        final int mod = this.itemCount % this.pageSize;
        final int offset = mod == 0 ? 0 : 1;
        this.pageCount = (this.itemCount - mod) / this.pageSize + offset;
        this.pageNumber = Math.min(this.pageNumber, this.pageCount);

        this.params.put("start", this.getStart());
        this.params.put("pageSize", this.getPageSize());
    }

    public int getStart() {
        return (Math.max(this.pageNumber, 1) - 1) * this.pageSize;
    }

    public boolean isFirst() {
        return this.pageNumber == 1;
    }

    public boolean isLast() {
        return this.pageNumber == this.pageCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(final int pageCount) {
        this.pageCount = pageCount;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(final int itemCount) {
        this.itemCount = itemCount;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(final List<T> items) {
        this.items = items;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public PaginateSupport<T> addParam(final String key, final Object value) {
        this.params.put(key, value);
        return this;
    }

}
