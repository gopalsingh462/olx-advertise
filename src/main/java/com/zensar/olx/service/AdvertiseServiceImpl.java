package com.zensar.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.olx.dto.Advertisement;
import com.zensar.olx.dto.User;
import com.zensar.olx.entity.AdvertiseEntity;
import com.zensar.olx.exception.InvalidAdvertiseIdException;
import com.zensar.olx.exception.InvalidAuthTokenException;
import com.zensar.olx.repository.AdvertisementRepository;
import com.zensar.olx.utils.Utils;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

	@Autowired
	MasterDataDelegate masterDataDelegate;

	@Autowired
	OLXLoginDelegate loginDelegate;

	@Autowired
	Utils utils;

	@Autowired
	AdvertisementRepository advertisementRepository;

	@Override
	public Advertisement postAdvertisement(Advertisement ad, String authToken) {
//		boolean isTokenValid = loginDelegate.isTokenValid(authToken);
		User userFromToken = getUserFromToken(authToken);
		if (userFromToken.getId() != 0) {
			AdvertiseEntity advertiseEntity = utils.getAdEntityFromAdDto(ad);
			advertiseEntity.setCreated_date(LocalDate.now());
			advertiseEntity.setPosted_by(userFromToken.getId());
			advertiseEntity.setUsername(userFromToken.getUsername());
			AdvertiseEntity en = advertisementRepository.save(advertiseEntity);
			return utils.getAdDtoFromAdEntity(en);
		} else {
			throw new InvalidAuthTokenException("Authentication token is not valid");
		}
	}

	@Override
	public Advertisement editAdvertisement(Advertisement ad, String authToken) {
		User userFromToken = getUserFromToken(authToken);
		if (userFromToken.getId() != 0) {
			Optional<AdvertiseEntity> adv = advertisementRepository.findById(ad.getId());
			if (adv.isPresent()) {
				AdvertiseEntity advertiseEntity = utils.getAdEntityFromAdDto(ad);
				advertiseEntity = advertisementRepository.save(advertiseEntity);
				advertiseEntity.setModified_date(LocalDate.now());
				return utils.getAdDtoFromAdEntity(advertiseEntity);
			} else {
				throw new InvalidAdvertiseIdException("Advertisement by given id not found");
			}
		} else {
			throw new InvalidAuthTokenException("Authentication token is not valid");
		}
	}

	@Override
	public List<Advertisement> getAllAdsByUser(String authToken) {
		User user = getUserFromToken(authToken);
		if (user.getId() != 0) {
			List<Advertisement> ads = new ArrayList<>();
			List<AdvertiseEntity> adsList = advertisementRepository.findByUsername(user.getUsername());
			for (AdvertiseEntity entity : adsList) {
				ads.add(utils.getAdDtoFromAdEntity(entity));
			}
			return ads;
		} else {
			throw new InvalidAuthTokenException("Authentication token is not valid");
		}
	}

	@Override
	public Advertisement getAdByUserById(String authToken, int adId) {
		User user = getUserFromToken(authToken);
		if (user.getId() != 0) {
			List<AdvertiseEntity> adsList = advertisementRepository.findByPostedByAndId(user.getId(), adId);
			if (adsList.size() == 0) {
				throw new InvalidAdvertiseIdException("Advertise by given ID not found");
			} else {
				Advertisement ad = utils.getAdDtoFromAdEntity(adsList.get(0));
				return ad;
			}
		} else {
			throw new InvalidAuthTokenException("Authentication token is not valid");
		}
	}

	@Override
	public boolean deleteAdByUserById(String authToken, int adId) {
		User user = getUserFromToken(authToken);
		List<AdvertiseEntity> adList = advertisementRepository.findByPostedByAndId(user.getId(), adId);
		if (adList.size() > 0) {
			AdvertiseEntity entity = adList.get(0);
			advertisementRepository.delete(entity);
			return true;
		} else {
			throw new InvalidAdvertiseIdException("Advertise by given ID not found");
		}
	}

	@Override
	public List<Advertisement> searchAdsByFilter(String searchText, int categoryId, int userId, String dateCondition,
			LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy, int startIndex, int numOfRecords) {
		List<Map> categoriesList = masterDataDelegate.getAllCategories();

		List<Map> statusList = masterDataDelegate.getAllStatuses();

		List<Advertisement> list = new ArrayList<>();
		list.add(new Advertisement(1, "Laptop Ad", 3000, 1, 1, 1, LocalDate.now(), LocalDate.now(), "desc"));
		list.add(new Advertisement(1, "Table Ad", 500, 2, 1, 1, LocalDate.now(), LocalDate.now(), "desc"));
		return list;
	}

	@Override
	public List<Advertisement> searchAds(String searchText) {
		List<AdvertiseEntity> adsList = advertisementRepository.findByTitleContainingOrDescriptionContaining(searchText,
				searchText);
		List<Advertisement> adsDtoList = new ArrayList<>();
		for (AdvertiseEntity entity : adsList) {
			adsDtoList.add(utils.getAdDtoFromAdEntity(entity));
		}
		return adsDtoList;
	}

	@Override
	public Advertisement getAdById(int adId) {
		Optional<AdvertiseEntity> entity = advertisementRepository.findById(adId);
		if (entity.isPresent()) {
			return utils.getAdDtoFromAdEntity(entity.get());
		} else {
			throw new InvalidAdvertiseIdException("Advertise ID not found");
		}
	}

	private User getUserFromToken(String authToken) {
		User userFromToken = loginDelegate.isTokenValid(authToken);
		return userFromToken;
	}
}
