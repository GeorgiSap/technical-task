package com.example.technical_task.service.mapper;

public interface EntityMapper<E, D> {
    D toDto(E entity);

    E toEntity(D dto);

}
