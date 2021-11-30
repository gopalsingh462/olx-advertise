package com.zensar.olx.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.olx.dto.Advertisement;
import com.zensar.olx.service.AdvertiseService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("olx/advertiseservice")
public class AdvertisementController {

	@Autowired
	AdvertiseService advertiseService;

	@PostMapping(value = "/advertise", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="Posts an advertisement")
	public Advertisement postAdvertisement(@RequestBody Advertisement ad,
			@RequestHeader("auth-token") String authToken) {
		return advertiseService.postAdvertisement(ad, authToken);
	}

	@PutMapping(value = "/advertise", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="Edit an advertisement")
	public Advertisement editAdvertisement(@RequestBody Advertisement ad,
			@RequestHeader("auth-token") String authToken) {
		return advertiseService.editAdvertisement(ad, authToken);
	}

	@GetMapping(value = "/user/advertise", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="Get all advertisements of a user")
	public List<Advertisement> getAllAdsByUser(@RequestHeader("auth-token") String authToken) {
		return advertiseService.getAllAdsByUser(authToken);
	}

	@GetMapping(value = "/user/advertise/{adId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="Get specific ad of a user")
	public Advertisement getAdByUserById(@RequestHeader("auth-token") String authToken,
			@PathVariable("adId") int adId) {
		return advertiseService.getAdByUserById(authToken, adId);
	}
	
	@DeleteMapping(value = "/user/advertise/{adId}")
	@ApiOperation(value="Delete advertise")
	public boolean deleteAdByUserById(@RequestHeader("auth-token") String authToken, @PathVariable("adId") int adId) {
		return advertiseService.deleteAdByUserById(authToken, adId);
	}

	@GetMapping(value = "/advertise/search/filtercriteria", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="Search ads by given filter criteria")
	public @ResponseBody List<Advertisement> searchAdsByFilter(@RequestParam(name = "searchText", required = false) String searchText,
			@RequestParam(name = "category", required = false) int categoryId,
			@RequestParam(name = "postedBy", required = false) int userId,
			@RequestParam(name = "dateCondition", required = false) String dateCondition,
			@RequestParam(name = "onDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate onDate,
			@RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "startIndex", required = false) int startIndex,
			@RequestParam(name = "records", required = false) int numOfRecords) {
		return advertiseService.searchAdsByFilter(searchText, categoryId, userId, dateCondition, onDate, fromDate,
				toDate, sortBy, startIndex, numOfRecords);
	}

	@GetMapping(value = "advertise/search", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="Search ads by given text")
	public List<Advertisement> searchAds(@RequestParam("searchText") String searchText) {
		return advertiseService.searchAds(searchText);
	}

	@GetMapping(value = "/advertise/{adId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="Get advertise by Id")
	public Advertisement getAdById(@PathVariable("adId") int adId) {
		return advertiseService.getAdById(adId);
	}
}
