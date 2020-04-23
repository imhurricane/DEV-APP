package com.dev.eda.frame.view.model;

public class PageInfo {

    private int pageSize;//页大小

    private int pageNumber;//当前页码

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
