var Compraki = Compraki || {};

Compraki.CadastroUsuario = (function() {
	
	function CadastroUsuario() {
		this.tipoPessoaFisica = $('#tipoFISICA');
		this.tipoPessoaJuridica = $('#tipoJURIDICA');
		this.mascara = $('#mask');
		this.inputCep = $('.js-cep');
		this.inputRua = $('#rua');
		this.inputCidade = $('#cidade');
		this.inputBairro = $('#bairro');
		this.inputEstado = $('#uf');
		this.inputComplemento = $('#estado');
		this.divDadosPessoais = $('#divDadosPessoais');
		this.divDadosEmpresa = $('#divDadosEmpresa');
		this.cpfCnpj = $('.js-cpf-cnpj');
		this.tipoFormPessoa = $('#tipoFormPessoa');
	}
	
	CadastroUsuario.prototype.iniciar = function(event) {
		this.tipoPessoaFisica.on('click', onAtualizaFormFisico.bind(this));
		this.tipoPessoaJuridica.on('click', onAtualizaFormJuridico.bind(this));
		this.inputCep.on('blur', pesquisarEnderecoPorCep.bind(this));
		$('.js-status').bootstrapSwitch();
		document.getElementById(this.tipoFormPessoa.val()).click();
		
	}
	
	function pesquisarEnderecoPorCep(){
		var cep = this.inputCep.val();
		if(cep.length >= 9){
			cep = cep.replace('.', '').replace('-', '');
			var url = "https://viacep.com.br/ws/"+cep+"/json/";
			var resposta = $.ajax({
				url: url,
				method: 'GET',
				dataType: "jsonp",
				success: preencherDadosEndereco.bind(this)
			});
		}
	}	
	
	function preencherDadosEndereco(endereco){
		this.inputRua.val(endereco.logradouro);
		this.inputComplemento.val(endereco.complemento);
		this.inputBairro.val(endereco.bairro);
		this.inputCidade.val(endereco.localidade);
		this.inputEstado.val(endereco.uf);
		this.inputEstado.change();
	}	
	
	function onAtualizaFormFisico(event){
		var op = $(event.currentTarget);
		var mascara = op.data('mascara');
		this.mascara.val(mascara);
		$.ajax({
			url: "/compraki/usuarios/atualizaFormularioPessoaFisica",
			method: 'POST',
			error: onError.bind(this),
			success: onSucessFormularioPessoaFisica.bind(this),
			complete: onAdicionaFormatacaoFormularioFisico.bind(this)
		});	
	}
	
	function onAdicionaFormatacaoFormularioFisico(event){
		this.cpfCnpj.mask(this.mascara.val());
		var maskDate = new Compraki.MaskDate();
		maskDate.enable();
	}
	
	function onSucessFormularioPessoaFisica(resultado){
		var parser = new DOMParser();
		var html = parser.parseFromString(resultado, "text/html"); 
		var htmlFinal = $(html).find('#targetContentFormFisico');
		this.divDadosPessoais.html(htmlFinal);
		this.divDadosEmpresa.html("");
	}
	
	function onError(erro){
		console.log(erro);
	}
	
	function onAtualizaFormJuridico(event){
		var op = $(event.currentTarget);
		var mascara = op.data('mascara');
		this.mascara.val(mascara);
		$.ajax({
			url: "/compraki/usuarios/atualizaFormularioPessoaJuridica",
			method: 'POST',
			error: onError.bind(this),
			success: onSucessFormularioPessoaJuridica.bind(this),
			complete: onAdicionaFormatacaoFormularioJuridico.bind(this)
		});			
	}
	
	function onAdicionaFormatacaoFormularioJuridico(event){
		this.cpfCnpj.mask(this.mascara.val());
	}
	
	function onSucessFormularioPessoaJuridica(resultado){
		var parser = new DOMParser();
		var html = parser.parseFromString(resultado, "text/html"); 
		var htmlFinal = $(html).find('#targetContentFormJuridico');
		this.divDadosEmpresa.html(htmlFinal);
		this.divDadosPessoais.html("");
	}
	
	return CadastroUsuario;
}());

$(function() {
	var cadastroUsuario = new Compraki.CadastroUsuario();
	cadastroUsuario.iniciar();
});