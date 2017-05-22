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
		var jsontext = '{"firstname":"Jesper","surname":"Aaberg","phone":["555-0100","555-0120"]}';
		var contact = JSON.parse(jsontext);
		var options = [];
        var things = [
                      { id: 1, color: 'yellow' },
                      { id: 2, color: 'blue' },
                      { id: 3, color: 'red' }
                  ];
        var teste = "[" + pickListAcessorios.val() + "];";
        console.log(teste);
        things.forEach(function(acessorio) {
			options.push('<option value="' + acessorio.id + '">' + acessorio.color + '</option>');
		});	
		this.selectAcessoriosEscolhidos.html(options.join(''));
		//this.formulario.submit();
	}	
	
	return CadastroCarro;
}());

$(function() {
	var cadastroCarro = new Compraki.CadastroCarro();
	cadastroCarro.iniciar();
});