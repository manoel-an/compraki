package br.com.compraki.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.compraki.model.Pessoa;

@Component
public class PessoaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pessoa.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "usuario.grupos", "", "É necessário selecionar pelo menos um grupo");
        ValidationUtils.rejectIfEmpty(errors, "endereco.cep", "", "O CEP é obrigatório");
        ValidationUtils.rejectIfEmpty(errors, "endereco.rua", "", "A rua é obrigatória");
        ValidationUtils.rejectIfEmpty(errors, "endereco.cidade", "", "A cidade é obrigatória");
        ValidationUtils.rejectIfEmpty(errors, "endereco.bairro", "", "O bairro é obrigatório");
        ValidationUtils.rejectIfEmpty(errors, "endereco.estado", "", "O estado é obrigatório");
        ValidationUtils.rejectIfEmpty(errors, "telefone.numeroUm", "", "É necessário inserir pelo menos 1 telefone");
    }

}
