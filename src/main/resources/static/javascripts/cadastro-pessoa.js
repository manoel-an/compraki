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
		this.botaoSalvarPessoa = $('.js-btn-salvar-pessoa');
		this.formulario = $('#formPessoa');
		this.nomeHelper = $('#nomeHelper');
		this.dataNascimentoHelper = $('#dataNascimentoHelper');
		this.apelidoHelper = $('#apelidoHelper');
		this.sexoHelper = $('#sexoHelper');
		this.razaoSocialHelper = $('#razaoSocialHelper');
		this.nomeFantasiaHelper = $('#nomeFantasiaHelper');
		this.cpfHelper = $('#cpfHelper');
		this.cnpjHelper = $('#cnpjHelper');
		this.senhaAux = $('#senhaAux');
		this.confirmaSenhaAux = $('#confirmaSenhaAux');
		this.divErro = $('.js-div-erro');
		this.divTextoErro = $('#js-div-texto-erro');
		this.divEmail = $('.js-div-email');
		this.divSenha = $('.js-div-senha');
		this.divConfirmaSenha = $('.js-div-confirma-senha');
		this.email = $('#email');
		this.senha = $('#senha');
		this.confirmaSenha = $('#confirmacaoSenha');
		this.topoPagina = $('.js-topo-page');
		this.hasErrorHelper = $('#hasErrorHelper');
		this.senhaHelper = $('#senhaHelper');
		this.confirmaSenhaHelper = $('#confirmaSenhaHelper');
	}
	
	CadastroUsuario.prototype.iniciar = function(event) {
		this.tipoPessoaFisica.on('click', onAtualizaFormFisico.bind(this));
		this.tipoPessoaJuridica.on('click', onAtualizaFormJuridico.bind(this));
		this.inputCep.on('blur', pesquisarEnderecoPorCep.bind(this));
		this.checkBoxStatus.bootstrapSwitch();
		this.botaoSalvarPessoa.on('click', onSalvarPessoa.bind(this));
		document.getElementById(this.inputTipoPessoa.val()).click();
		carregaSenhaConfirmaSenha.call(this);
	}
	
	function carregaSenhaConfirmaSenha(){
		if(this.hasErrorHelper.val() == 'true'){
			this.senha.val(this.senhaHelper.val());
			this.confirmaSenha.val(this.confirmaSenhaHelper.val());
		}
	}
	
	function onSalvarPessoa(event){
		event.preventDefault();
		validar.call(this);
	}	
	
	function validar(){
		if(this.email.val() == ""){
			this.divSenha.removeClass('has-error');
			this.divConfirmaSenha.removeClass('has-error');
			this.divEmail.addClass('has-error');
			this.divErro.removeClass('hidden');
			this.divTextoErro.html('<i class="fa  fa-exclamation-circle"></i> É necessário informar o email');
			this.email.focus();
			$(document).scrollTop($(this.topoPagina).offset().top);
			return;
		}
		if(this.senha.val() == ""){
			this.divEmail.removeClass('has-error');
			this.divConfirmaSenha.removeClass('has-error');
			this.divSenha.addClass('has-error');
			this.divErro.removeClass('hidden');
			this.divTextoErro.html('<i class="fa  fa-exclamation-circle"></i> É necessário informar a senha');
			this.senha.focus();
			$(document).scrollTop($(this.topoPagina).offset().top);		
			return;
		}
		if(this.confirmaSenha.val() == ""){
			this.divSenha.removeClass('has-error');
			this.divEmail.removeClass('has-error');
			this.divConfirmaSenha.addClass('has-error');
			this.divErro.removeClass('hidden');
			this.divTextoErro.html('<i class="fa  fa-exclamation-circle"></i> É necessário informar a confirmação de senha');
			this.confirmaSenha.focus();
			$(document).scrollTop($(this.topoPagina).offset().top);	
			return;
		}
		var validaCpfCnpj = new Compraki.ValidaCpfCpnj();
		var cpfCnpj = validaCpfCnpj.enable();
		if(cpfCnpj == false){
			var cpfOuCnpj = $('#cpfOuCnpj');
			if(this.inputTipoPessoa.val() == 'inputPessoaFISICA'){
				var divCpf = $('.js-div-cpf');
				divCpf.addClass('has-error');
				this.divEmail.removeClass('has-error');
				this.divSenha.removeClass('has-error');
				this.divConfirmaSenha.removeClass('has-error');
				this.divErro.removeClass('hidden');
				this.divTextoErro.html('<i class="fa  fa-exclamation-circle"></i> CPF Inválido');
				$(document).scrollTop($(this.topoPagina).offset().top);
			} else {
				var divCnpj = $('.js-div-cnpj');
				divCnpj.addClass('has-error');
				this.divEmail.removeClass('has-error');
				this.divSenha.removeClass('has-error');
				this.divConfirmaSenha.removeClass('has-error');				
				this.divErro.removeClass('hidden');
				this.divTextoErro.html('<i class="fa  fa-exclamation-circle"></i> CNPJ Inválido');	
				$(document).scrollTop($(this.topoPagina).offset().top);
			}
			cpfOuCnpj.focus();
		} else {
			this.formulario.submit();
		}
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
		if(this.nomeHelper.val()){
			var nomePessoa = $('#nomePessoa');
			nomePessoa.val(this.nomeHelper.val());
		}
		if(this.dataNascimentoHelper.val()){
			var dataNascimento = $('#dataNascimento');
			dataNascimento.val(this.dataNascimentoHelper.val());
		}
		if(this.apelidoHelper.val()){
			var apelido = $('#apelido');
			apelido.val(this.apelidoHelper.val());
		}
		if(this.cpfHelper.val()){
			var cpf = $('.js-cpf-cnpj');
			cpf.val(this.cpfHelper.val());
		}
		if(this.sexoHelper.val() == 'FEMININO'){
			var sexoFeminino = $('#sexo1');
			var labelSexoFeminino = $('#sexoFeminino');
			labelSexoFeminino.addClass('active');
			sexoFeminino.val(this.sexoHelper.val());
			var labelSexoMasculino = $('#sexoMasculino');
			labelSexoMasculino.removeClass('active');
		} 
		if(this.sexoHelper.val() == 'MASCULINO'){
			var sexoMasculino = $('#sexo2');
			var labelSexoMasculino = $('#sexoMasculino');
			labelSexoMasculino.addClass('active');
			sexoMasculino.val(this.sexoHelper.val());
			var labelSexoFeminino = $('#sexoFeminino');
			labelSexoFeminino.removeClass('active');
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
		if(this.razaoSocialHelper.val()){
			var nomeRazaoSocial = $('#nomeRazaoSocial');
			nomeRazaoSocial.val(this.razaoSocialHelper.val());
		}
		if(this.nomeFantasiaHelper.val() && this.nomeFantasiaHelper.val() != '-'){
			var nomeFantasia = $('#nomeFantasia');
			nomeFantasia.val(this.nomeFantasiaHelper.val());
		} else {
			$('#nomeFantasia').val('');
		}
		if(this.cnpjHelper.val()){
			var cnpj = $('.js-cpf-cnpj');
			cnpj.val(this.cnpjHelper.val());
		}
		if(this.apelidoHelper.val()){
			var apelido = $('#apelido');
			apelido.val(this.apelidoHelper.val());
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