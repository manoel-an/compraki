var Compraki = Compraki || {};

Compraki.CadastroCarro = (function() {
	
	function CadastroCarro() {
		this.botaoSalvar = $('.js-btn-salvar-carro');
		this.formulario = $('#formCarro');
		this.selectAcessoriosEscolhidos = $('#acessoriosEscolhidos');
	}
	
	CadastroCarro.prototype.iniciar = function(event) {
		this.botaoSalvar.on('click', onSalvarCarro.bind(this));
	}
	
	function onSalvarCarro(event){
		event.preventDefault();
		var pickList = new Compraki.PickList();
		var pickListAcessorios = pickList.enable();
		var options = [];
        pickListAcessorios.val().forEach(function(acessorio) {
        	var res = acessorio.split("-");
			options.push('<option id="acessorio'+ res[0] +'" value="' + res[0]+ '" selected="selected">' + res[1] + '</option>');
        });	
		this.selectAcessoriosEscolhidos.html(options.join(''));
		this.formulario.submit();
	}	
	
	return CadastroCarro;
}());

$(function() {
	var cadastroCarro = new Compraki.CadastroCarro();
	cadastroCarro.iniciar();
});