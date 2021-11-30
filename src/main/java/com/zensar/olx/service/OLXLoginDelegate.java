package com.zensar.olx.service;

import com.zensar.olx.dto.User;

public interface OLXLoginDelegate {
	public User isTokenValid(String authToken);
}
