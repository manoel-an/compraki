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
		this.inputTipoPessoa = $('#inputTipoPessoa');
		this.erroNomePessoa = $('#erroNomePessoa');
		this.erroRazaoSocial = $('#erroRazaoSocial');
		this.checkBoxStatus = $('.js-status');
		this.nomeFantasiaHidden = $('#nomeFantasiaHidden');
		this.erroDataNascimento = $('#erroDataNascimento');
		this.dataNascimentoHidden = $('#dataNascimentoHidden');
		this.erroCpf = $('#erroCpf');
		this.erroCnpj = $('#erroCnpj');
		this.erroNomeFantasia = $('#erroNomeFantasia');
	}
	
	CadastroUsuario.prototype.iniciar = function(event) {
		this.tipoPessoaFisica.on('click', onAtualizaFormFisico.bind(this));
		this.tipoPessoaJuridica.on('click', onAtualizaFormJuridico.bind(this));
		this.inputCep.on('blur', pesquisarEnderecoPorCep.bind(this));
		this.checkBoxStatus.bootstrapSwitch();
		document.getElementById(this.inputTipoPessoa.val()).click();
		
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
			complete: onAdicionaValidacoesFormularioFisico.bind(this)
		});	
	}
	
	function onAdicionaValidacoesFormularioFisico(event){
		this.cpfCnpj.mask(this.mascara.val());
		var maskDate = new Compraki.MaskDate();
		maskDate.enable();
		var divNomePessoa = $('.js-div-nome-pessoa');
		this.inputTipoPessoa.val('inputPessoaFISICA');
		this.nomeFantasiaHidden.attr('name', 'nomeFantasia');
		this.nomeFantasiaHidden.val('-');
		if(this.erroNomePessoa.val() == 'true'){
			divNomePessoa.addClass('has-error');
		}
		var divDataNascimento = $('.js-div-data-nascimento');
		if(this.erroDataNascimento.val() == 'true'){
			divDataNascimento.addClass('has-error');
		}
		this.dataNascimentoHidden.attr('name', 'dataNascimentoHidden');	
		var divCpf = $('.js-div-cpf');
		if(this.erroCpf.val() == 'true'){
			divCpf.addClass('has-error');
		}		
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
			complete: onAdicionaValidacoesFormularioJuridico.bind(this)
		});			
	}
	
	function onAdicionaValidacoesFormularioJuridico(event){
		this.cpfCnpj.mask(this.mascara.val());
		var divNomeRazaoSocial = $('.js-div-nome-razao-social');
		this.inputTipoPessoa.val('inputPessoaJURIDICA');
		this.nomeFantasiaHidden.attr('name', 'nomeFantasiaHidden');
		if(this.erroRazaoSocial.val() == 'true'){
			divNomeRazaoSocial.addClass('has-error');
		}
		this.dataNascimentoHidden.attr('name', 'dataNascimento');
		this.dataNascimentoHidden.val("01/01/1000");
		var divCnpj = $('.js-div-cnpj');
		if(this.erroCnpj.val() == 'true'){
			divCnpj.addClass('has-error');
		}
		var divNomeFantasia = $('.js-div-nome-fantasia');
		if(this.erroNomeFantasia.val() == 'true'){
			divNomeFantasia.addClass('has-error');
		}
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