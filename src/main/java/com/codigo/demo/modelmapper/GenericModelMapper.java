package com.codigo.demo.modelmapper;

public interface GenericModelMapper {
	<E, O> O convertEntityToDTO(E enitiy, Class<O> clazz);

	<D, O> O convertDTOToEntity(D dto, Class<O> clazz);

	<R, O> O convertRequestToObject(R request, Class<O> clazz);
}
