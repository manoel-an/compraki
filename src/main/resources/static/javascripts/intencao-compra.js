var Compraki = Compraki || {};

/* ***Corpo para função das combos marca/modelo  */
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

/* ***Corpo para função das combos uf/cidades  */
Compraki.IntencaoCompraComboUf = (function() {
	
	function IntencaoCompraComboUf() {
		this.comboUf = $('#uf');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	IntencaoCompraComboUf.prototype.enable = function(event) {
		this.comboUf.on('change', onUfAlterado.bind(this));
	}
	
	function onUfAlterado(){
		this.emitter.trigger('alterada', this.comboUf.val());
	}
	
	return IntencaoCompraComboUf;
}());


Compraki.IntencaoCompraComboCidade = (function() {
	
	function IntencaoCompraComboCidade(comboUf) {
		this.comboUf = comboUf;
		this.comboCidade = $('#cidade');
		this.inputHiddenCidadeSelecionada = $('#inputHiddenCidadeSelecionada');
	}
	
	IntencaoCompraComboCidade.prototype.enable = function(event) {
		limparComboCidade.call(this);
		this.comboUf.on('alterada', onUfAlterado.bind(this));	
		var siglaUf = this.comboUf.comboUf.val();
		inicializarCidades.call(this, siglaUf);
	}
	
	function onUfAlterado(evento, siglaUf) {
		this.inputHiddenCidadeSelecionada.val('');
		inicializarCidades.call(this, siglaUf);
	}	
	
	function inicializarCidades(siglaUf) {
		if (siglaUf) {
			var resposta = $.ajax({
				url: this.comboCidade.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'uf': siglaUf },
				complete: onEstilizaComboCidade.bind(this),
			});
			resposta.done(onBuscarCidades.bind(this));
		} else {
			limparComboCidade.call(this);
		}
	}
	
	function onEstilizaComboCidade(event){
		var selectSearch = new Compraki.SelectSearch();
		selectSearch.enable();
	}
	
	function onBuscarCidades(cidades) {
		var options = [];
		cidades.forEach(function(cidade) {
			options.push('<option value="' + cidade.codigo + '">' + cidade.descricao + '</option>');
		});
		
		this.comboCidade.html(options.join(''));
		this.comboCidade.removeAttr('disabled');
		
		var codigoCidadeSelecionada = this.inputHiddenCidadeSelecionada.val();
		if (codigoCidadeSelecionada) {
			this.comboUf.val(codigoCidadeSelecionada);
		}
	}	
	
	function limparComboCidade() {
		this.comboCidade.attr('disabled', 'disabled');
	}	
	
	
	return IntencaoCompraComboCidade;
}());

/*Tipo um método main*/
$(function() {
	/*chamada às combos marca/modelo*/
	var comboMarca = new Compraki.IntencaoCompraComboMarca();
	comboMarca.enable();
	
	var intencaoCompraComboModelo = new Compraki.IntencaoCompraComboModelo(comboMarca);
	intencaoCompraComboModelo.enable();	
	
	/*chamada às combos marca/modelo*/
	var comboUf = new Compraki.IntencaoCompraComboUf();
	comboUf.enable();
	
	var intencaoCompraComboCidade = new Compraki.IntencaoCompraComboCidade(comboCidade);
	intencaoCompraComboCidade.enable();	
});