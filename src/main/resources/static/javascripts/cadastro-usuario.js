var Compraki = Compraki || {};

Compraki.CadastroUsuario = (function() {
	
	function CadastroUsuario() {
		this.tipoPessoaFisica = $('#tipoFISICA');
		this.tipoPessoaJuridica = $('#tipoJURIDICA');
		this.divDataNascimento = $('.js-div-data-nascimento');
		this.divSexo = $('.js-div-sexo');
		this.divNomeFantasiaFormFisico = $('.js-div-nome-fanatasia-cpf');
		this.divNomeFantasiaFormJuridico = $('.js-div-nome-fanatasia-juridico');
		this.nome = $('.js-nome');
		this.tituloTipoPessoa = $('#tituloTipoPessoa');
		this.divcpfCnpjJuridico = $('.div-js-cpf-cnpj');
		this.inputCpfCnpj = $('.js-cpf-cnpj');
		this.mascaraPadrao = "000.000.000-00";
	}
	
	CadastroUsuario.prototype.iniciar = function(event) {
		this.tipoPessoaFisica.on('click', onAtualizaFormFisico.bind(this));
		this.tipoPessoaJuridica.on('click', onAtualizaFormJuridico.bind(this));
		this.inputCpfCnpj.mask(this.mascaraPadrao);
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
	}
	
	return CadastroUsuario;
}());

$(function() {
	var cadastroUsuario = new Compraki.CadastroUsuario();
	cadastroUsuario.iniciar();
});