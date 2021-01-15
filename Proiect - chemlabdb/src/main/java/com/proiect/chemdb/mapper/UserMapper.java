package com.proiect.chemdb.mapper;

import com.proiect.chemdb.domain.User;
import com.proiect.chemdb.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends EntityMapper<UserDto, User> {
}
