package org.privado.employee.team.mapper;

public interface DataMapper<D, E> {
    E mapData(D dto);
}
