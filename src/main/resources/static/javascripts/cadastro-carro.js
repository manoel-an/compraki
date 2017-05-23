var Compraki = Compraki || {};

Compraki.CadastroCarro = (function() {
	
	function CadastroCarro() {
		this.botaoSalvar = $('.js-btn-salvar-carro');
		this.formulario = $('#formCarro');
		this.selectAcessoriosEscolhidos = $('#acessoriosEscolhidos');
		this.checkBoxIpva = $('.js-ipva');
	}
	
	CadastroCarro.prototype.iniciar = function(event) {
		this.botaoSalvar.on('click', onSalvarCarro.bind(this));
		this.checkBoxIpva.bootstrapSwitch();
	}
	
	function onSalvarCarro(event){
		event.preventDefault();
		var pickList = new Compraki.PickList();
		var pickListAcessorios = pickList.enable();
		var options = [];
		if(pickListAcessorios.val() != null){
			pickListAcessorios.val().forEach(function(acessorio) {
				var res = acessorio.split("-");
				options.push('<option id="acessorio'+ res[0] +'" value="' + res[0]+ '" selected="selected">' + res[1] + '</option>');
			});	
			this.selectAcessoriosEscolhidos.html(options.join(''));
		}
		this.formulario.submit();
	}	
	
	return CadastroCarro;
}());

Compraki.UploadFoto = (function() {
	
	function UploadFoto() {
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.novaFoto = $('input[name=novaFoto]');
		this.htmlFotoVeiculoTemplate = $('#foto-veiculo').html();
		this.template = Handlebars.compile(this.htmlFotoVeiculoTemplate);
		this.containerFotoVeiculo = $('.js-container-foto-carro');
		this.uploadDrop = $('#upload-drop');
		
	}
	
	UploadFoto.prototype.iniciar = function () {
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.containerFotoVeiculo.data('url-fotos'),
				complete: onUploadCompleto.bind(this),
				beforeSend: adicionarCsrfToken
			}
			
			UIkit.uploadSelect($('#upload-select'), settings);
			UIkit.uploadDrop(this.uploadDrop, settings);
			
			if (this.inputNomeFoto.val()) {
				renderizarFoto.call(this, { nome:  this.inputNomeFoto.val(), contentType: this.inputContentType.val()});
			}
			
			function renderizarFoto(resposta) {
				this.inputNomeFoto.val(resposta.nome);
				this.inputContentType.val(resposta.contentType);
				
				this.uploadDrop.addClass('hidden');
				
				var htmlFotoVeiculo = this.template({nomeFoto: resposta.nome});
				this.containerFotoVeiculo.append(htmlFotoVeiculo);
				
				$('.js-remove-foto').on('click', onRemoverFoto.bind(this));
			}
			
			
			function onRemoverFoto() {
				$('.js-foto-veiculo').remove();
				this.uploadDrop.removeClass('hidden');
				this.inputNomeFoto.val('');
				this.inputContentType.val('');
				this.novaFoto.val('false');
			}			
			
			function onUploadCompleto(resposta) {
				this.novaFoto.val('true');
				renderizarFoto.call(this, resposta);
			}
			
			function adicionarCsrfToken(xhr) {
				var token = $('input[name=_csrf]').val();
				var header = $('input[name=_csrf_header]').val();
				xhr.setRequestHeader(header, token);
			}			
	}
	
	return UploadFoto;
	
})();

$(function() {
	var cadastroCarro = new Compraki.CadastroCarro();
	cadastroCarro.iniciar();
	
	var uploadFoto = new Compraki.UploadFoto();
	uploadFoto.iniciar();
});