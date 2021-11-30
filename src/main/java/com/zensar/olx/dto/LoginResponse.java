package com.zensar.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Login response")
public class LoginResponse {
	@ApiModelProperty("Status of login")
	private boolean status;
	@ApiModelProperty("Auth token on login success")
	private String token;

	public LoginResponse(boolean status, String token) {
		super();
		this.status = status;
		this.token = token;
	}

	public LoginResponse() {
	}

	public boolean isLoginSuccess() {
		return status;
	}

	public void setLoginSuccess(boolean isLoginSuccess) {
		this.status = isLoginSuccess;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
