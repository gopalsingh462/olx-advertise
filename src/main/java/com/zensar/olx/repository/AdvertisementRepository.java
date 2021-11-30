package com.zensar.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zensar.olx.entity.AdvertiseEntity;

public interface AdvertisementRepository extends JpaRepository<AdvertiseEntity, Integer>{
	public List<AdvertiseEntity> findByUsername(String username);
	@Query(value="SELECT ae from AdvertiseEntity ae WHERE ae.posted_by=:userId AND ae.id=:adId")
	public List<AdvertiseEntity> findByPostedByAndId(@Param("userId")int userId, @Param("adId")int adId);
	public List<AdvertiseEntity> findByTitleContainingOrDescriptionContaining(String title, String descripiton);
}
