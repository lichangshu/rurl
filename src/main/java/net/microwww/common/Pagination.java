/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * startIndex 从 0 开始， currentPage 从 1 开始 索引页面默认是 -1 ， 不进行分页
 *
 * @author changshu.li
 */
public class Pagination<T> {

    public static final int DEFAULT_PAGE_SIZE = 20;

    // 当前页的下标索引
    private int startIndex = -1;
    // 每页查询数量
    private int pageSize = DEFAULT_PAGE_SIZE;
    // 总记录数
    private long totalCount;
    private Map<String, Object> param = new HashMap();
    private List<T> list;

    public static <U> Pagination<U> getTop(int size) {
        Pagination<U> page = new Pagination<U>();
        page.setStartIndex(0);
        page.setPageSize(size);
        return page;
    }

    /**
     * page 从1开始
     *
     * @param <U>
     * @param pageIndex
     * @param size
     * @return
     */
    public static <U> Pagination<U> getPage(int pageIndex, int size) {
        Pagination<U> page = new Pagination<U>();
        page.setStartIndex((pageIndex - 1) * size);
        page.setPageSize(size);
        return page;
    }

    public static <U> Pagination<U> getIndex(int from, int size) {
        Pagination<U> page = new Pagination<U>();
        page.setStartIndex(from);
        page.setPageSize(size);
        return page;
    }

    public static <U> Pagination<U> getNoPage() {
        Pagination<U> page = new Pagination<U>();
        return page;
    }

    public Pagination() {
    }

    public Pagination(int totalCount, List<T> list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public boolean needPage() {
        int from = this.getStartIndex();
        int size = this.getPageSize();
        return from >= 0 && size >= 0;
    }

    public boolean isFirstPage() {
        if (!needPage()) {
            return true;
        }
        return this.getStartIndex() <= 0;
    }

    public boolean isLastPage() {
        if (!needPage()) {
            return true;
        }
        return this.getTotalCount() > (this.getStartIndex() + this.getPageSize());
    }

    public int getCurrentPage() {
        if (!needPage()) {
            return 1;
        }
        double index = this.getStartIndex();
        double size = this.getPageSize();
        return (int) Math.ceil((index + 1) / size);
    }

    public int getTotalPage() {
        if (!needPage()) {
            return 1;
        }
        double totals = this.getTotalCount();
        double size = this.getPageSize();
        return (int) Math.ceil(totals / size);
    }

    public int getStartIndex() {
        return startIndex;
    }

    /**
     * 从 0 开始计数
     *
     * @param pageIndex
     */
    public void setStartIndex(int pageIndex) {
        this.startIndex = pageIndex;
    }

    /**
     * 从 0 开始计数
     *
     * @param page
     */
    public void setPage(int page) {
        this.startIndex = page * this.getPageSize();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        if (this.getStartIndex() < 0) {
            this.setStartIndex(0);
        }
    }

    public long getTotalCount() {
        if (!needPage()) {
            return list == null ? 0 : list.size();
        }
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 只读 MAP
     *
     * @return
     */
    public Map<String, Object> getParam() {
        return Collections.unmodifiableMap(param);
    }

    public Object getParam(String key) {
        if (param != null) {
            return param.get(key);
        }
        return null;
    }

    public void putParam(String key, Object v) {
        param.put(key, v);
    }

    public Object removeParam(String key) {
        return param.remove(key);
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
