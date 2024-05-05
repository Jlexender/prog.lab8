package ru.lexender.springcrud8.model.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class ModelMapperService {
    ModelMapper modelMapper;

    public <S, T> T toDTO(S object, Class<T> dtoClass) {
        return modelMapper.map(object, dtoClass);
    }

    public <S, T> T fromDto(S dtoObject, Class<T> entityClass) {
        return modelMapper.map(dtoObject, entityClass);
    }
}
