package com.hialan.hv.commons.pagination;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Alan Yang
 * E-mail:alanly@gmail.com
 * Date: 2011-6-22
 * Time: 13:57:25
 * description
 */
public class Pagination {
    public static final String CURRENT_PAGE = "_currentPage";
    public static final String PAGE_SIZE = "_pageSize";
    public static final String SORT_FIELD = "_sortField";
    public static final String SORT_DIR = "_sortDir";
    public static final String SORT_ASC = "ASC";
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    private int total = -1;
    private int currentPage = DEFAULT_PAGE_NUMBER;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int startRow = 0;
    private int endRow = 0;
    private String url;
    private int totalPage;
    private int prePage;
    private int nextPage;
    private String sortField;
    private boolean sortDirection;

    private List list = new ArrayList();

    public boolean isDefineSort() {
        return StringUtils.isNotBlank(sortField);
    }

    public boolean isAsc() {
        return sortDirection;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        if (total > 0) {
            calculateAll();
        }
    }

    public List<Integer> getPageList() {
        return getPageList(5);
    }

    public List<Integer> getPageList(int num) {
        ArrayList<Integer> pageList = new ArrayList<Integer>();
        int showPage = num >= 1 ? num : 5;
        int startPage = 1;
        int endPage = totalPage;
        int _showPage = (showPage - 1) / 2;
        if (currentPage - startPage > _showPage) {
            startPage = currentPage - _showPage;
        }
        if (endPage - currentPage > _showPage) {
            endPage = currentPage + _showPage;
        }
        if (startPage == 1 && endPage != totalPage && (endPage - startPage + 1) != showPage) {
            endPage = startPage + showPage - 1;
            if (endPage > totalPage) {
                endPage = totalPage;
            }
        }
        if (endPage == totalPage && startPage != 1 && (endPage - startPage + 1) != showPage) {
            startPage = endPage - showPage + 1;
            if (startPage < 1) {
                startPage = 1;
            }
        }

        for (int i = startPage; i <= endPage; i++) {
            pageList.add(i);
        }
        return pageList;
    }

    public void calculateAll() {
        calculateTotalPage();
        resetCurrentPage();
        calculateRowRange();
        calculatePageRange();
    }

    private void calculatePageRange() {
        prePage = currentPage - 1;
        if (currentPage != totalPage) {
            nextPage = currentPage + 1;
        }
    }

    private void calculateRowRange() {
        startRow = (currentPage - 1) * pageSize;
        endRow = currentPage * pageSize;
        if (endRow > total) {
            endRow = total;
        }
    }

    private void resetCurrentPage() {
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }
    }

    private void calculateTotalPage() {
        totalPage = (int) Math.ceil(new Double(total).doubleValue() / getPageSize());
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url + CURRENT_PAGE + "=";
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public String getParameter() {
        return url.split("\\?")[1];
    }

    public String getEncodeUrl() {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortDirection(boolean sortDirection) {
        this.sortDirection = sortDirection;
    }
}
