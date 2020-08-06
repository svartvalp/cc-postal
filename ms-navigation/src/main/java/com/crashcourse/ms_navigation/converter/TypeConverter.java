package com.crashcourse.ms_navigation.converter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TypeConverter<S, D> extends AbstractConverter<S, D> {

    @Autowired
    public void setModelMapper(ModelMapper mapper) {
        mapper.addConverter(this);
    }
}
