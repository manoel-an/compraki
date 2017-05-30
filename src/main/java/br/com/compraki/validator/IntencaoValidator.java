package br.com.compraki.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.compraki.model.IntencaoCompra;

@Component
public class IntencaoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return IntencaoCompra.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "modelo.codigo", "", "É necessário selecionar um modelo");
        // ValidationUtils.rejectIfEmpty(errors, "acessorios.codigo", "", "É
        // necessario selecionar pelo menos um acessório");
        ValidationUtils.rejectIfEmpty(errors, "cores", "", "É necessário selecionar pelo menos uma cor");
        ValidationUtils.rejectIfEmpty(errors, "acessorios", "", "É necessário selecionar pelo menos um acessório");
    }

}
