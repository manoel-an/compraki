package br.com.compraki.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.repository.IntencaoCompras;

public class IntencaoCompraConverter implements Converter<String, IntencaoCompra> {

    @Autowired
    private IntencaoCompras intencoes;

    @Override
    public IntencaoCompra convert(String source) {
        if (!StringUtils.isEmpty(source)) {
            return intencoes.getIntencaoCompraComCoresEAcessorios(Long.valueOf(source));
        } else {
            return null;
        }
    }

}