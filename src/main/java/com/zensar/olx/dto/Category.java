package com.zensar.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Category model")
public class Category {
	@ApiModelProperty("Unique identifier of category")
	private int id;
	@ApiModelProperty("The name of the category")
	private String category;

	public Category(int id, String category) {
		super();
		this.id = id;
		this.category = category;
	}

	public Category() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
