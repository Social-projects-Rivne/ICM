package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MapperService<D, E> {

    public List<D> toDtoList(List<E> entities) {
        return entities.stream().map(this::<D, E>toDto).collect(Collectors.toList());
    }

    public List<E> toEntityList(List<D> entities) {
        return entities.stream().map(this::<D, E>toEntity).collect(Collectors.toList());
    }

    public Page<D> toDtoPage(Page<E> entities) {
        return entities.map(this::<D, E>toDto);
    }

    public Page<E> toEntityPage(Page<D> entities) {
        return entities.map(this::<D, E>toEntity);
    }

    public abstract D toDto(E entity) throws NullPointerException;

    public abstract E toEntity(D dto) throws NullPointerException;
}
