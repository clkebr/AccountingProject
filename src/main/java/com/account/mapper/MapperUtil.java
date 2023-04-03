package com.account.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <R> R convertToType(Object source, R resultClass) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return  modelMapper.map(source, (Type) resultClass.getClass());
    }

}
