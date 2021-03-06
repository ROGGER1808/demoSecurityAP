package com.transon.securityDemo.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    private String updatedBy;

    @Column(name = "isActive")
    private Boolean isActive = true;

    public Date getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new Date();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
