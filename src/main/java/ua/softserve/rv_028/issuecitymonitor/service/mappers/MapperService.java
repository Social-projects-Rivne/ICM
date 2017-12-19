package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MapperService<D, E> {

    public List<D> toDtoList(List<E> entities) {
        return entities.stream().map(this::<D, E>toDto).collect(Collectors.toList());
    }

    public List<E> toEntityList(List<D> entities) {
        return entities.stream().map(this::<D, E>toEntity).collect(Collectors.toList());
    }

    public abstract D toDto(E entity);

    public abstract E toEntity(D dto);
}
