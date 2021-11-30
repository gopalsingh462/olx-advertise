package com.zensar.olx.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import com.zensar.olx.dto.Advertisement;
import com.zensar.olx.entity.AdvertiseEntity;

public class Utils {
	@Autowired
	ModelMapper modelMapper;

	public Advertisement getAdDtoFromAdEntity(AdvertiseEntity advertiseEntity) {
		TypeMap<AdvertiseEntity, Advertisement> typeMap = this.modelMapper.typeMap(AdvertiseEntity.class,
				Advertisement.class);

		typeMap.addMappings(mapper -> {
			mapper.map(source -> source.getCategory(), Advertisement::setCategoryId);
			mapper.map(source -> source.getStatus(), Advertisement::setStatusId);
			mapper.map(source -> source.getPosted_by(), Advertisement::setUserId);
		});

		return modelMapper.map(advertiseEntity, Advertisement.class);
	}

	public AdvertiseEntity getAdEntityFromAdDto(Advertisement advertisement) {
		TypeMap<Advertisement, AdvertiseEntity> typeMap = this.modelMapper.typeMap(Advertisement.class,
				AdvertiseEntity.class);
		typeMap.addMappings(mapper -> {
			mapper.map(source -> source.getCategoryId(), AdvertiseEntity::setCategory);
			mapper.map(source -> source.getStatusId(), AdvertiseEntity::setStatus);
			mapper.map(source -> source.getUserId(), AdvertiseEntity::setPosted_by);
		});
		return modelMapper.map(advertisement, AdvertiseEntity.class);
	}

}
