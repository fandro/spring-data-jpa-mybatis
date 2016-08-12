package com.yougou.domain;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Table(name="category")
@Entity
public class Category {

	private Integer id;
	private String categoryName;

	private String createdByUser;

	private Date creationTime;

	private String modifiedByUser;

	private Date modifyActionTime;

	private Set<Item> items = new HashSet<Item>();

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="category_name")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@ManyToMany(mappedBy="categories")
	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

    @Column(name = "created_by_user", nullable = false)
//    @CreatedBy
    public String getCreatedByUser() {
        return createdByUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = false)
    @CreatedDate
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


    @Column(name = "modified_by_user", nullable = false)
    @LastModifiedBy
    public String getModifiedByUser() {
        return modifiedByUser;
    }

    public void setModifiedByUser(String modifiedByUser) {
        this.modifiedByUser = modifiedByUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_action_time")
    @LastModifiedDate
    public Date getModifyActionTime() {
        return modifyActionTime;
    }

    public void setModifyActionTime(Date modifyActionTime) {
        this.modifyActionTime = modifyActionTime;
    }
}
