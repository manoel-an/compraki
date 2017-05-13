package br.com.compraki.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.compraki.model.Usuario;

@Component
public class UsuarioValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "endereco.cep", "", "Informe o CEP do estabelecimento");
	}

}
