package com.hialan.hv.commons.pagination;

import java.util.ArrayList;
import java.util.List;

public abstract class PageNoBuilder {

    private static interface Number {
        int TEN = 10;
        int FIVE = 5;
    }

    public static List<PageNo> getPageItems(Paginate paginate) {
        if (paginate.getTotalPage() <= Number.TEN) {
            return getMaxPageItems(paginate.getTotalPage());
        }
        if (paginate.getCurrentPage() <= Number.FIVE) {
            return getPrePageItems(paginate.getTotalPage());
        }
        if (paginate.getCurrentPage() > paginate.getTotalPage() - Number.FIVE) {
            return getLastPageItems(paginate.getTotalPage());
        }
        return getNormalItems(paginate);
    }

    private static List<PageNo> getNormalItems(Paginate paginate) {
        List<PageNo> items = new ArrayList<PageNo>();
        items.addAll(getStartPageNos());
        items.add(new PageNo(paginate.getCurrentPage() - 2));
        items.add(new PageNo(paginate.getCurrentPage() - 1));
        items.add(new PageNo(paginate.getCurrentPage()));
        items.add(new PageNo(paginate.getCurrentPage() + 1));
        items.add(new PageNo(paginate.getCurrentPage() + 2));
        items.addAll(getEndPageNos(paginate.getTotalPage()));
        return items;
    }

    private static List<PageNo> getLastPageItems(int totalPage) {
        List<PageNo> items = new ArrayList<PageNo>();
        items.addAll(getStartPageNos());
        for (int i = totalPage - Number.FIVE + 1; i <= totalPage; i++) {
            items.add(new PageNo(i));
        }
        return items;
    }

    private static List<PageNo> getPrePageItems(int totalPage) {
        List<PageNo> items = new ArrayList<PageNo>();
        for (int i = 1; i <= Number.FIVE; i++) {
            items.add(new PageNo(i));
        }
        items.addAll(getEndPageNos(totalPage));
        return items;
    }

    private static List<PageNo> getMaxPageItems(int totalPage) {
        List<PageNo> items = new ArrayList<PageNo>();
        for (int i = 1; i <= totalPage; i++) {
            items.add(new PageNo(i));
        }
        return items;
    }

    private static List<PageNo> getEndPageNos(int totalPage) {
        List<PageNo> items = new ArrayList<PageNo>();
        items.add(new PageNo(PageNo.Type.POINT));
        items.add(new PageNo(totalPage - 1));
        items.add(new PageNo(totalPage));
        return items;
    }

    private static List<PageNo> getStartPageNos() {
        List<PageNo> items = new ArrayList<PageNo>();
        items.add(new PageNo(1));
        items.add(new PageNo(2));
        items.add(new PageNo(PageNo.Type.POINT));
        return items;
    }

}