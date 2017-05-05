var Compraki = Compraki || {};

Compraki.NovoCadastro = (function() {
	
	function NovoCadastro() {
		this.inputConfirmPassword = $('.js-confirm-password');
		
	}
	
	NovoCadastro.prototype.enable = function(event) {
		this.inputConfirmPassword.on('change', onTeste.bind(this));
	}
	
	function onTeste(event){
		alert('oi');
	}
	
	
	return NovoCadastro;
}());

$(function() {
	var novoCadastro = new Compraki.NovoCadastro();
	novoCadastro.enable();
});