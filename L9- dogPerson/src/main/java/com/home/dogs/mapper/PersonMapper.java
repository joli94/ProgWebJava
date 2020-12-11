package com.home.dogs.mapper;

import com.home.dogs.domain.Person;
import com.home.dogs.dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "string")
public interface PersonMapper extends EntityMapper<PersonDto, Person>{
}
