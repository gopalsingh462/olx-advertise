package com.zensar.olx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class MasterDataDelegationImpl implements MasterDataDelegate {
	@Autowired
	RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

//	@Override
//	public List<Map> getAllCategories() {
//		List list = this.restTemplate.getForObject("http://localhost:9001/olx/advertise/category", List.class);
//		return list;
//	}
	
	@Override
	@CircuitBreaker(name="CATEGORY-CIRCUIT-BREAKER", fallbackMethod = "fallbackCategory")
	public List<Map> getAllCategories() {
//		List list = this.restTemplate.getForObject("http://masterdata-service/olx/advertise/category", List.class);
		List list = this.restTemplate.getForObject("http://olx-api-gateway/olx/masterdata/categories", List.class);
		return list;
	}

	@Override
	@CircuitBreaker(name="STATUS-CIRCUIT-BREAKER", fallbackMethod="fallbackGetAllStatuses")
	public List<Map> getAllStatuses() {
		List list = this.restTemplate.getForObject("http://masterdata-service/olx/masterdata/statuses", List.class);
		return list;
	}
	
	public List<Map> fallbackGetAllStatuses(Exception ex){
		System.out.println("Masterdata get statuses call failed...fallback is calling");
		return new ArrayList<Map>();
	}
	
	public List<Map> fallbackCategory(Exception ex){
		System.out.println("Masterdata get categories call failed...fallback is calling"+ex);
		return new ArrayList<Map>();
	}

}
