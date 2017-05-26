package br.com.compraki.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.compraki.model.carro.Carro;
import br.com.compraki.repository.Carros;

public class CarroConverter implements Converter<String, Carro> {

    @Autowired
    private Carros carros;

    @Override
    public Carro convert(String source) {
        if (!StringUtils.isEmpty(source)) {
            return carros.getFullCar(Long.valueOf(source));
        } else {
            return null;
        }
    }

}