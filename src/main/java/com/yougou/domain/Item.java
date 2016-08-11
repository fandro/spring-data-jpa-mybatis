package com.yougou.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name="item")
@Entity
public class Item {

	private Integer id;
	private String itemName;

	private Set<Category> categories = new HashSet<Category>();

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="item_name")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	//使用 @ManyToMany 注解来映射多对多关联关系
	//使用 @JoinTable 来映射中间表
	//1. name 指向中间表的名字
	//2. joinColumns 映射当前类所在的表在中间表中的外键
	//2.1 name 指定外键列的列名
	//2.2 referencedColumnName 指定外键列关联当前表的哪一列
	//3. inverseJoinColumns 映射关联的类所在中间表的外键
	@JoinTable(name="item_category",
			joinColumns={@JoinColumn(name="item_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="category_id", referencedColumnName="id")})
	@ManyToMany
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
}
