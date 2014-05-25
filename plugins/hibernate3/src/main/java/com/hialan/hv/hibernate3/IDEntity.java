package com.hialan.hv.hibernate3;

import com.hialan.hv.commons.annotation.Comment;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 27/5/13
 *         Time: 3:13 PM
 */

@MappedSuperclass
public abstract class IDEntity implements Serializable {

    private static final long serialVersionUID = 6902399253319668762L;

    @Id
    @GeneratedValue
    @Comment("id唯一索引")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean hasId() {
        return this.id != null;
    }
}
