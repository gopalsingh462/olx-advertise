package com.zensar.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	EntityManager entityManager;
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

//	@Override
//	public List<Advertisement> searchAdsByFilter(String searchText, int categoryId, int userId, String dateCondition,
//			LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy, int startIndex, int numOfRecords) {
//		List<Map> categoriesList = masterDataDelegate.getAllCategories();
//
//		List<Map> statusList = masterDataDelegate.getAllStatuses();
//		//call MasterData service getAllCategories() - RestTemplate
//		//List<Map> categoriesList = masterDataDelegate.getAllCategories();
//
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(AdvertiseEntity.class);
//		Root<AdvertiseEntity> rootEntity = criteriaQuery.from(AdvertiseEntity.class);
//
//		Predicate titlePredicate = criteriaBuilder.equal(rootEntity.get("title"), searchText); //title=searchText
//		Predicate categoryPredicate = criteriaBuilder.equal(rootEntity.get("categoryId"), categoryId);
//
//		Predicate finalPredicate = criteriaBuilder.and(titlePredicate, categoryPredicate);
//		criteriaQuery.where(finalPredicate);
//		TypedQuery<AdvertiseEntity> query = entityManager.createQuery(criteriaQuery);
//		List<AdvertiseEntity> advertiseEntityList = query.getResultList();
//		List<Advertisement> adList = new ArrayList<>();
//		for(AdvertiseEntity entity: advertiseEntityList) {
//			adList.add(utils.getAdDtoFromAdEntity(entity));
//		}
//		return adList;
//	}

	@Override
	public List<Advertisement> searchAdsByFilter(String searchText, int categoryId, int postedBy, String dateCondition,
			LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortedBy, int startIndex, int records) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdvertiseEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertiseEntity.class);
		Root<AdvertiseEntity> rootEntity = criteriaQuery.from(AdvertiseEntity.class);

		Predicate titlePredicate = criteriaBuilder.like(rootEntity.get("title"), searchText);
		Predicate categoryIdPredicate = criteriaBuilder.equal(rootEntity.get("categoryId"), categoryId);
		Predicate postedByPredicate = criteriaBuilder.equal(rootEntity.get("postedBy"), postedBy);
		Predicate onDatePredicate = criteriaBuilder.equal(rootEntity.get("createdDate"), onDate);
		Predicate toDatePredicate = criteriaBuilder.equal(rootEntity.get("createdDate"), toDate);
		Predicate fromDatePredicate = criteriaBuilder.equal(rootEntity.get("createdDate"), fromDate);
		Predicate lessThanPredicate = criteriaBuilder.lessThan(rootEntity.get("createdDate"), fromDate);
		Predicate geaterThanPredicate = criteriaBuilder.greaterThan(rootEntity.get("createdDate"), fromDate);
		Order sortedOrder = criteriaBuilder.desc(rootEntity.get("createdDate"));

		Predicate finalPredecate = null;
		criteriaBuilder.or(titlePredicate, categoryIdPredicate, postedByPredicate);

		if (searchText != null) {

			if (categoryId != 0) {

				if (postedBy != 0) {

					if (dateCondition != null) {

						switch (dateCondition) {

						case "between":
							finalPredecate = criteriaBuilder.or(titlePredicate, categoryIdPredicate, postedByPredicate,
									fromDatePredicate, toDatePredicate);
							break;
						case "lessthan":
							finalPredecate = criteriaBuilder.or(titlePredicate, categoryIdPredicate, postedByPredicate,
									lessThanPredicate);
							break;
						case "greatethan":
							finalPredecate = criteriaBuilder.or(titlePredicate, categoryIdPredicate, postedByPredicate,
									geaterThanPredicate);
							break;
						case "equals":
							finalPredecate = criteriaBuilder.or(titlePredicate, categoryIdPredicate, postedByPredicate,
									onDatePredicate);
							break;
						default:
							break;
						}

						if (sortedBy != null) {
							criteriaQuery.orderBy(sortedOrder);
						}
					}

				} else {

				}

			} else {

			}

		} else {

		}

		criteriaQuery.where(finalPredecate);
		TypedQuery<AdvertiseEntity> query = entityManager.createQuery(criteriaQuery);
		List<AdvertiseEntity> advertiseEntities = query.getResultList();
		List<Advertisement> adList = new ArrayList<>();
		for (AdvertiseEntity entity : advertiseEntities) {
			adList.add(utils.getAdDtoFromAdEntity(entity));
		}
		return adList;
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
