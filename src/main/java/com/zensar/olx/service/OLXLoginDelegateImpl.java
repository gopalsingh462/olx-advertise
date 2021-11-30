package com.zensar.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zensar.olx.dto.User;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OLXLoginDelegateImpl implements OLXLoginDelegate{

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	@CircuitBreaker(name="VALIDATE-TOKEN-CIRCUIT-BREAKER", fallbackMethod = "fallbackIsTokenValid")
	public User isTokenValid(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+authToken);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<User> b = restTemplate.exchange("http://olx-api-gateway/olx/authentication/validatetoken", HttpMethod.GET, entity, User.class);
		return b.getBody();
	}
	
	public User fallbackIsTokenValid(String authToken, Exception ex) {
		return new User();
	}

}
