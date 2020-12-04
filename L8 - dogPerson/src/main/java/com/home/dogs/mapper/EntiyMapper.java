package com.home.dogs.mapper;

public interface EntiyMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);
}
