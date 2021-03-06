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
			this.comboModelo.val(codigoModeloSelecionado);
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
			options.push('<option value="' + cidade.codigo + '">' + cidade.nome + '</option>');
		});
		
		this.comboCidade.html(options.join(''));
		this.comboCidade.removeAttr('disabled');
		
		var codigoCidadeSelecionada = this.inputHiddenCidadeSelecionada.val();
		if (codigoCidadeSelecionada) {
			this.comboCidade.val(codigoCidadeSelecionada);
		}
	}	
	
	function limparComboCidade() {
		this.comboCidade.attr('disabled', 'disabled');
	}	
	
	
	return IntencaoCompraComboCidade;
}());

/* ***Função para popular select hidden de cores e acessórios; antes de salvar a intenção  
 * Claudio é aqui que você vai acrescentar as propriedades para o select de acessorios hidden
 * */
Compraki.SalvarIntencaoCompra = (function() {
	
	function SalvarIntencaoCompra() {
		this.selectAcessoriosHidden = $('#acessoriosEscolhidos');
		this.selectCoresHidden = $('#coresEscolhidas');
		this.botaoSalvarIntencao = $('.js-btn-salvar-intencao');
		this.formIntencaoCompra = $('#formIntencao');
	}
	
	SalvarIntencaoCompra.prototype.enable = function(event) {
		this.botaoSalvarIntencao.on('click', onSalvarIntencao.bind(this));
	}
	
	function onSalvarIntencao(event){
		event.preventDefault();
		var pickListCores = $('#selectCores').bootstrapDualListbox();
		var options = [];
		if(pickListCores.val() != null){
			pickListCores.val().forEach(function(cor) {
				var res = cor.split("-");
				options.push('<option id="cor'+ res[0] +'" value="' + res[0]+ '" selected="selected">' + res[1] + '</option>');
			});	
			this.selectCoresHidden.html(options.join(''));
		}
		var pickListAcessorios = $('#selectAcessorios').bootstrapDualListbox();
		options = [];
		if(pickListAcessorios.val() != null){
			pickListAcessorios.val().forEach(function(acessorio) {
				var res = acessorio.split("-");
				options.push('<option id="cor'+ res[0] +'" value="' + res[0]+ '" selected="selected">' + res[1] + '</option>');
			});	
			this.selectAcessoriosHidden.html(options.join(''));
		}		
		this.formIntencaoCompra.submit();		
	}
	
	
	return SalvarIntencaoCompra;
}());

Compraki.TipoVeiculo = (function() {
	
	function TipoVeiculo() {
		this.labelCarro = $('#tipoCARRO');
		this.opcaoCarro = $('#inputVeiculoCARRO');
		this.labelMoto = $('#tipoMOTO');
		this.opcaoMoto = $('#inputVeiculoMOTO');
		this.labelPesado = $('#tipoPESADO');
		this.opcaoPesado = $('#inputVeiculoPESADO');
		this.codigoIntencao = $('#idHidden');
		this.divDadosCarro = $('#divDadosCarro');
		this.divDadosMoto = $('#divDadosMoto');
		this.divDadosPesado = $('#divDadosPesado');
		this.acessoriosJSON = $('#acessoriosJSON');
		this.coresJSON = $('#coresJSON');
		this.tipoVeiculoHelper = $('#tipoVeiculoHelper');
		this.erroMarca = $('#erroMarca');
		this.marcaHelper = $('#marcaHelper');
		this.erroModelo = $('#erroModelo');
		this.modeloHelper = $('#modeloHelper');
		this.erroCores = $('#erroCores');
		this.erroAcessorios = $('#erroAcessorios');
		
	}
	
	TipoVeiculo.prototype.enable = function(event) {
		if(this.tipoVeiculoHelper.val()){
			adicionarActiveTipoVeiculo.call(this);
			onAtualizaFormulario.call(this, this.tipoVeiculoHelper.val(), true);
		}
		this.opcaoCarro.on('change', onAtualizaFormulario.bind(this, this.opcaoCarro.val(), false));
		this.opcaoMoto.on('change', onAtualizaFormulario.bind(this, this.opcaoMoto.val(), false));
		this.opcaoPesado.on('change', onAtualizaFormulario.bind(this, this.opcaoPesado.val(), false));
	}
	
	function adicionarActiveTipoVeiculo(){
		if(this.tipoVeiculoHelper.val() == 'CARRO'){
			this.labelCarro.addClass('active');
		} else if(this.tipoVeiculoHelper.val() == 'MOTO'){
			this.labelMoto.addClass('active');
		} else {
			this.labelPesado.addClass('active');
		}
	}
	
	function onAtualizaFormulario(tipoVeiculo, validacao){
		var codigo = this.codigoIntencao.val();
		var cores = new Array();
		if(this.coresJSON.val()){
			var obj = jQuery.parseJSON(this.coresJSON.val());
			obj.forEach(function(cor) {
				cores.push(cor.codigo);
			});				
		}		
		var acessorios = new Array();
		if(this.acessoriosJSON.val()){
			var obj = jQuery.parseJSON(this.acessoriosJSON.val());
			obj.forEach(function(acessorio) {
				acessorios.push(acessorio.codigo);
			});				
		}
		if(!validacao){
			retirarValidacaoFormVeiculo.call(this);
		}		
		$.ajax({
			url: "/compraki/intencoes/atualizaFormularioVeiculo",
			method : 'GET',
			contentType : 'application/json',
			data: {
				codigoIntencao: codigo, tipoVeiculo: tipoVeiculo, cores: cores, acessorios: acessorios
			},	
			error: onError.bind(this),
			success: onSucessFormulario.bind(this, tipoVeiculo),
			complete: onAdicionaValidacoesFormulario.bind(this, tipoVeiculo)
		});			
	}
	
	function onSucessFormulario(tipoVeiculo, resultado){
		var parser = new DOMParser();
		var html = parser.parseFromString(resultado, "text/html"); 
		if(tipoVeiculo == 'CARRO'){
			var htmlFinal = $(html).find('#targetContentTipoVeiculoCarro');
			this.divDadosCarro.html(htmlFinal);
			this.divDadosMoto.html("");
			this.divDadosPesado.html("");
		} else if (tipoVeiculo == 'MOTO'){
			var htmlFinal = $(html).find('#targetContentTipoVeiculoMoto');
			this.divDadosCarro.html("");
			this.divDadosMoto.html(htmlFinal);
			this.divDadosPesado.html("")
		} else {
			var htmlFinal = $(html).find('#targetContentTipoVeiculoPesado');
			this.divDadosCarro.html("");
			this.divDadosMoto.html("");
			this.divDadosPesado.html(htmlFinal);
		}
	}
	
	function onError(erro){
		console.log(erro);
	}
	
	function onAdicionaValidacoesFormulario(tipoVeiculo){
		if(tipoVeiculo == 'CARRO'){
			adicionarValidacaoCarro.call(this);
		} else if(tipoVeiculo == 'MOTO'){
			adicionarValidacaoMoto.call(this);
		} else {
			adicionarValidacaoPesado.call(this);
		}		
		/*chamada às combos marca/modelo*/
		var comboMarca = new Compraki.IntencaoCompraComboMarca();
		comboMarca.enable();
		var intencaoCompraComboModelo = new Compraki.IntencaoCompraComboModelo(comboMarca);
		intencaoCompraComboModelo.enable();			
		var selectSearch = new Compraki.SelectSearch();
		selectSearch.enable();
		var pickList = new Compraki.PickList();
		pickList.enable();		
	}
	
	function retirarValidacaoFormVeiculo(){
		this.erroAcessorios.val(false);
		this.erroMarca.val(false);
		this.erroModelo.val(false);
		this.erroCores.val(false);		
	}	
	
	
	function adicionarValidacaoCarro(){
		var hasErrors = $('#hasErrors');
		var divMarca = $('.js-div-marca');
		var divModelo = $('.js-div-modelo');
		var divAcessorios = $('.js-div-acessorios');
		var divCores = $('.js-div-cores');
		var marca = $('.js-combo-marca');
		var modelo = $('.js-combo-modelo');

		
		if(this.erroCores.val() == 'true'){
			divCores.addClass('has-error');
		}
		if(this.erroAcessorios.val() == 'true'){
			divAcessorios.addClass('has-error');
		}
		if(this.erroMarca.val() == 'true'){
			divMarca.addClass('has-error');
			marca.val('');
		} else {
			if(hasErrors.val() == 'true'){
				marca.val(this.marcaHelper.val());
			} else {
				if(!marca.val()){
					marca.val('');
				}
			}
		}
		if(this.erroModelo.val() == 'true'){
			divModelo.addClass('has-error');
			modelo.val('');
		} else {
			if(hasErrors.val() == 'true'){
				modelo.val(this.modeloHelper.val());
			} else {
				if(!modelo.val()){
					modelo.val('');
				}
			}
		}
	}
	
	function adicionarValidacaoMoto(){
		var hasErrors = $('#hasErrors');
		var divMarca = $('.js-div-marca-moto');
		var divModelo = $('.js-div-modelo-moto');
		var divAcessorios = $('.js-div-acessorios-moto');
		var divCores = $('.js-div-cores-moto');
		var marca = $('.js-combo-marca-moto');
		var modelo = $('.js-combo-modelo-moto');

		
		if(this.erroCores.val() == 'true'){
			divCores.addClass('has-error');
		}
		if(this.erroAcessorios.val() == 'true'){
			divAcessorios.addClass('has-error');
		}
		if(this.erroMarca.val() == 'true'){
			divMarca.addClass('has-error');
			marca.val('');
		} else {
			if(hasErrors.val() == 'true'){
				marca.val(this.marcaHelper.val());
			} else {
				if(!marca.val()){
					marca.val('');
				}
			}
		}
		if(this.erroModelo.val() == 'true'){
			divModelo.addClass('has-error');
			modelo.val('');
		} else {
			if(hasErrors.val() == 'true'){
				modelo.val(this.modeloHelper.val());
			} else {
				if(!modelo.val()){
					modelo.val('');
				}
			}
		}
	}	
	
	function adicionarValidacaoPesado(){
		var hasErrors = $('#hasErrors');
		var divMarca = $('.js-div-marca-pesado');
		var divModelo = $('.js-div-modelo-pesado');
		var divAcessorios = $('.js-div-acessorios-pesado');
		var divCores = $('.js-div-cores-pesado');
		var marca = $('.js-combo-marca-pesado');
		var modelo = $('.js-combo-modelo-pesado');

		
		if(this.erroCores.val() == 'true'){
			divCores.addClass('has-error');
		}
		if(this.erroAcessorios.val() == 'true'){
			divAcessorios.addClass('has-error');
		}
		if(this.erroMarca.val() == 'true'){
			divMarca.addClass('has-error');
			marca.val('');
		} else {
			if(hasErrors.val() == 'true'){
				marca.val(this.marcaHelper.val());
			} else {
				if(!marca.val()){
					marca.val('');
				}
			}
		}
		if(this.erroModelo.val() == 'true'){
			divModelo.addClass('has-error');
			modelo.val('');
		} else {
			if(hasErrors.val() == 'true'){
				modelo.val(this.modeloHelper.val());
			} else {
				if(!modelo.val()){
					modelo.val('');
				}
			}
		}
	}	
	
	
	return TipoVeiculo;
}());

/*Tipo um método main*/
$(function() {
	/*chamada às combos marca/modelo*/
	var comboUf = new Compraki.IntencaoCompraComboUf();
	comboUf.enable();
	
	var intencaoCompraComboCidade = new Compraki.IntencaoCompraComboCidade(comboUf);
	intencaoCompraComboCidade.enable();	
	
	var salvarIntencao = new Compraki.SalvarIntencaoCompra();
	salvarIntencao.enable();	
	
	var tipoVeiculo = new Compraki.TipoVeiculo();
	tipoVeiculo.enable();		
	
});