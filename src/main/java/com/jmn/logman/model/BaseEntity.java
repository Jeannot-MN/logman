package com.jmn.logman.model;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.time.Instant;

public abstract class BaseEntity implements Serializable {

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private Long createdBy;

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
