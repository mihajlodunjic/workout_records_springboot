package org.fon.workout_records.mapper;


public interface DtoEntityMapper<D, E> {
    D toDto(E e);
    E toEntity(D d);
}
