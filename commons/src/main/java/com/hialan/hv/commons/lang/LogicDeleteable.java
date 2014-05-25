package com.hialan.hv.commons.lang;

/**
 * <p>实体实现该接口表示想要逻辑删除
 * User: Alan Yang
 * Date: 13/5/13
 * Time: 2:29 PM
 */
public interface LogicDeleteable {

    public Status getDeleted();

    public void setDeleted(Status deleted);

    /**
     * 标识为已删除
     */
    public void markDeleted();

}
