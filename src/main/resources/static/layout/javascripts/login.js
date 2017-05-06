var Compraki = Compraki || {};

Compraki.NovoCadastro = (function() {
	function NovoCadastro() {
		this.email = $('.js-email');
		this.divMsgEmail = $('.js-div-msg-email');
		this.divMsgEmailInvalido = $('.js-div-email-invalido');
		this.divMsgConfereSenha = $('.js-senha-confere');
		this.divInputEmail = $('.js-div-input-email');
		this.errorEmail = $('#email');
		this.errorEmailInvalido = $('#invalido');
		this.errorSenha = $('#senha');
		this.errorLimite = $('#limite');
		this.errorConfere = $('#confere');
		this.divMsgErrorLimiteSenha = $('.js-senha-limite');
		this.divMsgErrorSenha = $('.js-senha-nao-confere');
		this.divInputSenha = $('.js-div-input-password');
		this.divInputConfirmSenha = $('.js-confirm-password');
		this.emailAuxiliar = $('#aux');
		this.inputEmailNovoCadastro = $('.js-input-email');
		this.senhaNovoCadastro = $('.js-password-confirm');
		
	}
	
	NovoCadastro.prototype.enable = function(event) {
		this.email.focus();
		if(this.errorSenha.val() == "true"){
			this.divMsgErrorSenha.removeClass('hidden');
		}
		if(this.errorLimite.val() == "true"){
			this.divMsgErrorLimiteSenha.removeClass('hidden');
			this.divInputSenha.addClass('has-error');
			this.divInputConfirmSenha.addClass('has-error');
			this.inputEmailNovoCadastro.val(this.emailAuxiliar.val());
			this.senhaNovoCadastro.focus();
		}	
		if(this.errorEmail.val() == "true"){
			this.divMsgEmail.removeClass('hidden');
			this.divInputEmail.addClass('has-error');
			this.inputEmailNovoCadastro.focus();
		}
		if(this.errorEmailInvalido.val() == "true"){
			this.divMsgEmailInvalido.removeClass('hidden');
			this.divInputEmail.addClass('has-error');
			this.inputEmailNovoCadastro.val(this.emailAuxiliar.val());
			this.inputEmailNovoCadastro.focus();
		}
		if(this.errorConfere.val() == "true"){
			this.divMsgConfereSenha.removeClass('hidden');
			this.divInputSenha.addClass('has-error');
			this.divInputConfirmSenha.addClass('has-error');
			this.inputEmailNovoCadastro.val(this.emailAuxiliar.val());
			this.senhaNovoCadastro.focus();
			
		}			
	}
	
	return NovoCadastro;
}());

$(function() {
	var novoCadastro = new Compraki.NovoCadastro();
	novoCadastro.enable();
});