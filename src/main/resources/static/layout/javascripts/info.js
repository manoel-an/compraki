var Compraki = Compraki || {};

Compraki.LoadCompraki = (function() {
	
	function LoadCompraki() {
		this.botaoTeste = $('.js-btn-teste');
		this.input = $('#idVeiculo');
		
	}
	
	LoadCompraki.prototype.enable = function(event) {
		this.botaoTeste.on('click',  onClicarBotao.bind(this));
	}
	
	function onClicarBotao(event){
		alert(this.input.val());
	}
	
	return LoadCompraki;
}());

$(function() {
	var loadCompraki = new Compraki.LoadCompraki();
	loadCompraki.enable();
});