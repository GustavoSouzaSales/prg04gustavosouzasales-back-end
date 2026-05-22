package br.com.ifba.prg04ultragas.infrastructure.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapperUtil {

    private final ModelMapper mapper = new ModelMapper();

    // Converte um objeto
    public <T, Y> Y map(T source, Class<Y> targetClass) {

        return mapper.map(source, targetClass);
    }

    // Converte lista de objetos
    public <T, Y> List<Y> mapAll(
            List<T> sourceList,
            Class<Y> targetClass
    ) {

        return sourceList.stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}