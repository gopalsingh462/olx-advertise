package com.zensar.olx.dto;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Advertisement Model")
public class Advertisement {
	@ApiModelProperty("Unique identifier of the advertisement")
	private int id;
	@ApiModelProperty("Title of the advertisement")
	private String title;
	@ApiModelProperty("Price of the advertisement item")
	private double price;
	@ApiModelProperty("Category ID")
	private int categoryId;
	@ApiModelProperty("Status ID")
	private int statusId;
	@ApiModelProperty("User ID")
	private int userId;
	@ApiModelProperty("Date on which ad is created")
	private LocalDate createdDate;
	@ApiModelProperty("Date on which ad is last modified")
	private LocalDate modifiedDate;
	@ApiModelProperty("Description of the advertisement")
	private String description;
	public Advertisement(int id, String title, double price, int categoryId, int statusId, int userId, LocalDate createdDate, LocalDate modifiedDate, String description) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.categoryId = categoryId;
		this.statusId = statusId;
		this.userId = userId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public Advertisement() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
