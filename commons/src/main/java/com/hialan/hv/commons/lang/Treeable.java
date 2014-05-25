package com.hialan.hv.commons.lang;

import java.io.Serializable;

/**
 * 树结构
 *
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 23/5/13
 *         Time: 3:26 PM
 */
public interface Treeable<PK extends Serializable> {

    public void setName(String name);

    public String getName();

    /**
     * 父路径
     */
    public PK getParentId();

    public void setParentId(PK parentId);

    public boolean isRoot();

    public boolean isLeaf();

    public boolean hasChildren();

}
