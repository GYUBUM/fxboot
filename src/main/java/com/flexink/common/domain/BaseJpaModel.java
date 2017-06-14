package com.flexink.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flexink.config.web.security.user.UserDetailsHelper;
import com.flexink.security.SessionUserDetail;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@DynamicInsert
@DynamicUpdate
public abstract class BaseJpaModel<PK extends Serializable> extends FxBootCrudModel implements Persistable<PK>, Serializable {
	
	@Override
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    protected Date createdAt;

    @Column(name = "CREATED_BY", updatable = false)
    protected String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    protected Date updatedAt;

    @Column(name = "UPDATED_BY")
    protected String updatedBy;

    @Transient
    protected SessionUserDetail createdUser;

    @Transient
    protected SessionUserDetail modifiedUser;

    @PrePersist
    protected void onPersist() {
        this.createdBy = this.updatedBy = getCurrentLoginUserCd();
        this.createdAt = this.updatedAt = new Date();
        //this.createdAt = this.updatedAt = Instant.now(Clock.systemUTC());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedBy = getCurrentLoginUserCd();
        this.updatedAt = new Date();
    }

    @PostLoad
    protected void onPostLoad() {
    }
    
    @Transient
    private String getCurrentLoginUserCd() {
    	SessionUserDetail user = null;
    	Object obj = UserDetailsHelper.getAuthenticatedUser();
    	if(obj != null && !obj.equals("anonymousUser")) {
    		user = (SessionUserDetail) obj;
    	}
    	return user == null ? "anonymous" : user.getUsername();
    }
}
