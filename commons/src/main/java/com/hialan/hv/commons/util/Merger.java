package com.hialan.hv.commons.util;

public interface Merger<E, T> {

    public String getKey(E input);

    public T convert(E input);

    public void merge(E input, T target);

    public boolean isMergeEnable(E e, T temp);


}
