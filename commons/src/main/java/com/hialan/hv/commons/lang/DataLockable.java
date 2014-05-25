package com.hialan.hv.commons.lang;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 15/5/13
 * Time: 3:03 PM
 */
public interface DataLockable {

    public boolean getLocked();

    public void setLocked(boolean locked);

    /**
     * 标识为已锁定
     */
    public void markLocked();
}
