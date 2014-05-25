package com.hialan.hv.hibernate3;

import com.hialan.hv.commons.annotation.Comment;
import com.hialan.hv.commons.lang.Status;
import com.hialan.hv.hibernate3.usertype.PropertiesMapUserType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


@MappedSuperclass
@TypeDefs({
        @TypeDef(name = "map", typeClass = PropertiesMapUserType.class, parameters = {
                @Parameter(name = "keyType", value = "java.lang.String"),
                @Parameter(name = "valueType", value = "java.lang.String")
        })
})
public abstract class AbstractPersistence extends IDEntity implements Serializable {

    private static final long serialVersionUID = -995070111522997248L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Comment("创建时间")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Comment("更新时间")
    private Date updateTime;

    @Version
    @Column(nullable = false)
    @Comment("版本")
    private Integer version;

    @Enumerated
//    @Column(columnDefinition = "int(1) DEFAULT 0", nullable = false) //may occur exception on h2 db
    @Comment("禁用状态")
    private Status disabledStatus = Status.NO;

    public void toggleStatus() {
        if (Status.YES == disabledStatus) {
            makeEnabled();
        } else {
            makeDisabled();
        }
    }

    public void makeDisabled() {
        disabledStatus = Status.YES;
    }

    public void makeEnabled() {
        disabledStatus = Status.NO;
    }

    public boolean isDisabled() {
        return disabledStatus.isYes();
    }

    public boolean isEnabled() {
        return disabledStatus.isNo();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Status getDisabledStatus() {
        return disabledStatus;
    }

    public void setDisabledStatus(Status disabledStatus) {
        this.disabledStatus = disabledStatus;
    }

    public String getMetaData() {
        return getClass().getSimpleName();
    }

    public boolean hasId() {
        return this.id != null;
    }
}
