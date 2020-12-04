package com.home.dogs.mapper;

import com.home.dogs.domain.Dog;
import com.home.dogs.dto.DogDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface DogMapper extends EntiyMapper<DogDto, Dog> {

    DogDto toDto(Dog entity);

    Dog toEntity(DogDto dto);
}
