package br.com.compraki.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.compraki.model.carro.Carro;

@Component
public class CarroValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Carro.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "modelo.fabricante", "", "É necesário informar a marca");
        ValidationUtils.rejectIfEmpty(errors, "modelo.descricao", "", "É necesário informar o modelo do véiculo");
        ValidationUtils.rejectIfEmpty(errors, "modelo.categoria", "", "É necesário informar a categoria do veículo");
        ValidationUtils.rejectIfEmpty(errors, "cor", "", "É necesário informar uma cor para o veículo");
        ValidationUtils.rejectIfEmpty(errors, "acessorios", "", "É necessário selecionar pelo menos um acessório");
    }
}
