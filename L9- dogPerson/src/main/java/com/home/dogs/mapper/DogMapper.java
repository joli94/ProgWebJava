package com.home.dogs.mapper;

import com.home.dogs.domain.Dog;
import com.home.dogs.dto.DogDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "string", uses = {PersonMapper.class})
public interface  DogMapper extends EntityMapper<DogDto, Dog> {
}
