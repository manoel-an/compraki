package br.com.compraki.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.repository.Cidades;
import br.com.compraki.repository.IntencaoCompras;

public class IntencaoCompraConverter implements Converter<String, IntencaoCompra> {

    @Autowired
    private IntencaoCompras intencoes;

    @Autowired
    private Cidades cidades;

    @Override
    public IntencaoCompra convert(String source) {
        if (!StringUtils.isEmpty(source)) {
            IntencaoCompra intencaoCompra = intencoes.getIntencaoCompraCompleto(Long.valueOf(source));
            intencaoCompra.setCidade(
                    this.cidades.findByCodigo(new Long(intencaoCompra.getCidadePreferencia())).get().getNome());
            return intencaoCompra;
        } else {
            return null;
        }
    }

}