var Compraki = Compraki || {};

Compraki.CadastroUsuario = (function() {
	
	function CadastroUsuario() {
		this.tipoPessoaFisica = $('#tipoFISICA');
		this.tipoPessoaJuridica = $('#tipoJURIDICA');
		this.divDataNascimento = $('.js-div-data-nascimento');
		this.divSexo = $('.js-div-sexo');
		this.divNomeFantasiaFormFisico = $('.js-div-nome-fanatasia-cpf');
		this.divNomeFantasiaFormJuridico = $('.js-div-nome-fantasia-juridico');
		this.nome = $('.js-nome');
		this.tituloTipoPessoa = $('#tituloTipoPessoa');
		this.divcpfCnpjJuridico = $('.div-js-cpf-cnpj');
		this.inputCpfCnpj = $('.js-cpf-cnpj');
		this.mascaraPadrao = "000.000.000-00";
		this.mascaraCep = "99999-999";
		this.nomeRazaoSocial = $('#nomePessoaRazaoSocial');
		this.inputCep = $('.js-cep');
		this.inputRua = $('#rua');
		this.inputCidade = $('#cidade');
		this.inputBairro = $('#bairro');
		this.inputEstado = $('#uf');
		this.inputComplemento = $('#estado');
	}
	
	CadastroUsuario.prototype.iniciar = function(event) {
		this.tipoPessoaFisica.on('click', onAtualizaFormFisico.bind(this));
		this.tipoPessoaJuridica.on('click', onAtualizaFormJuridico.bind(this));
		this.inputCpfCnpj.mask(this.mascaraPadrao);
		this.inputCep.mask(this.mascaraCep);
		this.inputCep.on('blur', pesquisarEnderecoPorCep.bind(this));
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
		this.divDataNascimento.removeClass('hidden');
		this.divSexo.removeClass('hidden');
		this.divNomeFantasiaFormJuridico.addClass('hidden');
		this.divNomeFantasiaFormFisico.removeClass('hidden');
		this.nome.text('Nome');
		this.tituloTipoPessoa.text('Dados Pessoais');
		this.divcpfCnpjJuridico.addClass('hidden');
		this.inputCpfCnpj.mask(mascara);
		this.nomeRazaoSocial.attr("placeholder", "Nome");
	}
	
	function onAtualizaFormJuridico(event){
		var op = $(event.currentTarget);
		var mascara = op.data('mascara');
		this.divDataNascimento.addClass('hidden');
		this.divSexo.addClass('hidden');
		this.divNomeFantasiaFormFisico.addClass('hidden');
		this.divNomeFantasiaFormJuridico.removeClass('hidden');
		this.nome.text('Razão Social');
		this.tituloTipoPessoa.text('Dados Empresa');
		this.divcpfCnpjJuridico.removeClass('hidden');
		this.inputCpfCnpj.mask(mascara);
		this.nomeRazaoSocial.attr("placeholder", "Razão Social");
	}
	
	return CadastroUsuario;
}());

$(function() {
	var cadastroUsuario = new Compraki.CadastroUsuario();
	cadastroUsuario.iniciar();
});