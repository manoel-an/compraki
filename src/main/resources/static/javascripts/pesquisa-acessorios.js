var Compraki = Compraki || {};

Compraki.ModalAcessorio = (function() {
	
	function ModalAcessorio() {
		this.modal = $('#modalCadastroAcessorio');
		this.botaoSalvar = this.modal.find('.js-modal-cadastro-acessorio-salvar-btn');
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.inputNomeAcessorio = $('#nomeAcessorio');
		this.containerMensagemErro = $('.js-mensagem-cadastro-acessorio');
		this.novoAcessorio = $('#novoAcessorio');
		this.botaoEditarAcessorio = $('.js-btn-editar-acessorio');
		this.botaoNovoAcessorio = $('.js-btn-novo-acessorio');
		this.codigo = $('#codigoHidden');
		this.acessorioHidden = $('#acessorioHidden');
		this.flagEdicao = $('#flagEdicao');
	}
	
	ModalAcessorio.prototype.enable = function(event) {
		this.form.on('submit', function(event) { event.preventDefault() });
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
		this.botaoEditarAcessorio.on('click', onInicializaAcessorioSelecionado.bind(this));
		this.botaoNovoAcessorio.on('click', onInicializaFlagEdicao.bind(this));
		if(this.novoAcessorio.val() == 'true'){
			showMessageSucess.call(this);
		}
	}
	
	function onInicializaFlagEdicao(event){
		this.flagEdicao.val(false);
	}
	
	function onInicializaAcessorioSelecionado(event){
		var op = $(event.currentTarget);
		var codigo = op.data('codigo');
		var acessorio = op.data('acessorio');
		this.codigo.val(codigo);
		this.acessorioHidden.val(acessorio);
		this.flagEdicao.val(true);
	}
	
	function showMessageSucess(){
		$("#success-alert-acessorio").removeClass('hidden');
        $("#success-alert-acessorio").alert();
        $("#success-alert-acessorio").fadeTo(2000, 2000).slideUp(2000, function(){
        	$("#success-alert-acessorio").slideUp(2000);
        });
        window.setTimeout(setGetParameter.bind(this, "novoAcessorio", false), 4000);        
	}
	
	function setGetParameter(paramName, paramValue){
	    var url = window.location.href;
	    var hash = location.hash;
	    url = url.replace(hash, '');
	    if (url.indexOf(paramName + "=") >= 0) {
	        var prefix = url.substring(0, url.indexOf(paramName));
	        var suffix = url.substring(url.indexOf(paramName));
	        suffix = suffix.substring(suffix.indexOf("=") + 1);
	        suffix = (suffix.indexOf("&") >= 0) ? suffix.substring(suffix.indexOf("&")) : "";
	        url = prefix + paramName + "=" + paramValue + suffix;
	    }
	    else {
	    if (url.indexOf("?") < 0)
	        url += "?" + paramName + "=" + paramValue;
	    else
	        url += "&" + paramName + "=" + paramValue;
	    }
	    window.location.href = url + hash;
	}
	
	
	function onModalShow(event) {
		this.inputNomeAcessorio.focus();
		if(this.acessorioHidden.val() && this.flagEdicao.val() == 'true'){
			this.inputNomeAcessorio.val(this.acessorioHidden.val());
		} else {
			this.inputNomeAcessorio.val('');
		}
	}
	
	function onModalClose() {
		this.inputNomeAcessorio.val('');
		this.containerMensagemErro.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick() {
		var nomeAcessorio = this.inputNomeAcessorio.val().trim();
		$.ajax({
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ codigo: this.codigo.val(), descricao: nomeAcessorio }),
			error: onErroSalvandoAcessorio.bind(this),
			success: onAcesorioSalvo.bind(this)
		});
	}
	
	function onErroSalvandoAcessorio(obj) {
		var mensagemErro = obj.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}
	
	function onAcesorioSalvo(acessorio) {
        var url_atual = window.location.href;
        var url = url_atual.split("compraki");
        window.location = url[0] + "compraki/acessorios?novoAcessorio=true";
	}
	
	function onAtualizaAcessorios(container){
		this.containerAcessorios.html(container);
	}
	
	function onError(erro){
		console.log(erro);
	}
	
	return ModalAcessorio;
}());

$(function() {
	var modalAcessorio = new Compraki.ModalAcessorio();
	modalAcessorio.enable();
});