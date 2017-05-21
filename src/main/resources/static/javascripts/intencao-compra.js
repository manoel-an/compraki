var Compraki = Compraki || {};

Compraki.IntencaoCompraComboMarca = (function() {
	
	function IntencaoCompraComboMarca() {
		this.comboMarca = $('#marca');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	IntencaoCompraComboMarca.prototype.enable = function(event) {
		this.comboMarca.on('change', onMarcaAlterada.bind(this));
	}
	
	function onMarcaAlterada(){
		this.emitter.trigger('alterada', this.comboMarca.val());
	}
	
	return IntencaoCompraComboMarca;
}());


Compraki.IntencaoCompraComboModelo = (function() {
	
	function IntencaoCompraComboModelo(comboMarca) {
		this.comboMarca = comboMarca;
		this.comboModelo = $('#modelo');
		this.inputHiddenModeloSelecionado = $('#inputHiddenModeloSelecionado');
	}
	
	IntencaoCompraComboModelo.prototype.enable = function(event) {
		limparComboModelo.call(this);
		this.comboMarca.on('alterada', onMarcaAlterada.bind(this));	
		var codigoMarca = this.comboMarca.comboMarca.val();
		inicializarModelos.call(this, codigoMarca);
	}
	
	function onMarcaAlterada(evento, codigoMarca) {
		this.inputHiddenModeloSelecionado.val('');
		inicializarModelos.call(this, codigoMarca);
	}	
	
	function inicializarModelos(codigoMarca) {
		if (codigoMarca) {
			var resposta = $.ajax({
				url: this.comboModelo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'marca': codigoMarca },
				complete: onEstilizaComboModelo.bind(this),
			});
			resposta.done(onBuscarModelos.bind(this));
		} else {
			limparComboModelo.call(this);
		}
	}
	
	function onEstilizaComboModelo(event){
		var selectSearch = new Compraki.SelectSearch();
		selectSearch.enable();
	}
	
	function onBuscarModelos(modelos) {
		var options = [];
		modelos.forEach(function(modelo) {
			options.push('<option value="' + modelo.codigo + '">' + modelo.descricao + '</option>');
		});
		
		this.comboModelo.html(options.join(''));
		this.comboModelo.removeAttr('disabled');
		
		var codigoModeloSelecionado = this.inputHiddenModeloSelecionado.val();
		if (codigoModeloSelecionado) {
			this.comboMarca.val(codigoModeloSelecionado);
		}
	}	
	
	function limparComboModelo() {
		this.comboModelo.attr('disabled', 'disabled');
	}	
	
	
	return IntencaoCompraComboModelo;
}());

$(function() {
	var comboMarca = new Compraki.IntencaoCompraComboMarca();
	comboMarca.enable();
	
	var intencaoCompraComboModelo = new Compraki.IntencaoCompraComboModelo(comboMarca);
	intencaoCompraComboModelo.enable();	
});