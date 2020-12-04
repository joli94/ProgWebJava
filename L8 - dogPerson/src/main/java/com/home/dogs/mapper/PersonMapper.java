package com.home.dogs.mapper;

import com.home.dogs.domain.Person;
import com.home.dogs.dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper extends EntiyMapper<PersonDto, Person>{

    PersonDto toDto(Person entity);

    Person toEntity(PersonDto dto);
}
