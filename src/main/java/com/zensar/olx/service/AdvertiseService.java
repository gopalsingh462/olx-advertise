package com.zensar.olx.service;

import java.time.LocalDate;
import java.util.List;

import com.zensar.olx.dto.Advertisement;

public interface AdvertiseService {

	public Advertisement postAdvertisement(Advertisement ad, String authToken);

	public Advertisement editAdvertisement(Advertisement ad, String authToken);

	public List<Advertisement> getAllAdsByUser(String authToken);

	public Advertisement getAdByUserById(String authToken, int adId);

	public boolean deleteAdByUserById(String authToken, int adId);

	public List<Advertisement> searchAdsByFilter(String searchText,int categoryId, int userId, String dateCondition,LocalDate onDate,LocalDate fromDate,LocalDate toDate,String sortBy,int startIndex,int numOfRecords);
	
	public List<Advertisement> searchAds(String searchText);
	
	public Advertisement getAdById(int adId);
}
