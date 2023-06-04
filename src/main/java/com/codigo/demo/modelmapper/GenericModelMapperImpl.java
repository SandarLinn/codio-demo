package com.codigo.demo.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GenericModelMapperImpl implements GenericModelMapper {

	private  ModelMapper mapper = modelMapper();
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

	@Override
	public <E, O> O convertEntityToDTO(E entity, Class<O> clazz) {
		mapper.getConfiguration().setAmbiguityIgnored(true);
		return this.mapper.map(entity, clazz);
	}

	@Override
	public <D, O> O convertDTOToEntity(D dto, Class<O> clazz) {
		mapper.getConfiguration().setAmbiguityIgnored(true);
		return this.mapper.map(dto, clazz);
	}

	@Override
	public <R, O> O convertRequestToObject(R request, Class<O> clazz) {
		return this.mapper.map(request, clazz);
	}

}
