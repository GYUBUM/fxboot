package com.flexink.common.domain;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flexink.common.code.FxBootType;

import lombok.Data;

@Data
public abstract class FxBootCrudModel {

    @Transient
    public FxBootType.DataStatus getDataStatus() {
        if (__deleted__) {
            return FxBootType.DataStatus.DELETED;
        }

        if (__created__) {
            return FxBootType.DataStatus.CREATED;
        }

        if (__modified__) {
            return FxBootType.DataStatus.MODIFIED;
        }

        return FxBootType.DataStatus.ORIGIN;
    }

    @Transient
    @JsonProperty("__deleted__")
    protected boolean __deleted__;

    @Transient
    @JsonProperty("__created__")
    protected boolean __created__;

    @Transient
    @JsonProperty("__modified__")
    protected boolean __modified__;

    @Transient
    @JsonIgnore
    public boolean isDeleted() {
        return __deleted__;
    }

    @Transient
    @JsonIgnore
    public boolean isCreated() {
        return __created__;
    }

    @Transient
    @JsonIgnore
    public boolean isModified() {
        return __modified__;
    }
    
}
