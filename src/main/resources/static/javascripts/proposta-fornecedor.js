var Compraki = Compraki || {};

Compraki.PropostaFornecedor = (function() {

	function PropostaFornecedor() {
		this.modal = $('#modalCadastroIntermediarioVeiculo');
		this.modalTelefone = $('#modalCadastroRapidoTelefone');
		this.inputTelefone = $('#nomeTelefone');
		this.botaoSalvarTelefoneModal = $('.js-btn-salvar-telefone');
		this.botaoSalvarInteracao = $('.js-btn-salvar-proposta');
		this.formularioInteracao = $('#formInteracao');
		this.formTelefone = this.modalTelefone.find('form');
		this.url = this.formTelefone.attr('action');
		this.codigoPessoa = $('#codigoPessoa');
		this.containerMensagemErro = $('.js-mensagem-cadastro-rapido-telefone');
	}

	PropostaFornecedor.prototype.enable = function(event) {
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modalTelefone.on('shown.bs.modal', onModalTelefoneShow.bind(this));
		this.botaoSalvarTelefoneModal.on('click', onSalvarTelefone.bind(this));
		this.botaoSalvarInteracao.on('click', onSalvarInteracao.bind(this));
	}

	function onSalvarInteracao(event) {
		event.preventDefault();
		if (validaCadastroInteracao.call(this)) {
			$.ajax({
				url : "/compraki/detalhesIntencao/salvarInteracao",
				method : 'GET',
				contentType : 'application/json',
				data : this.formularioInteracao.serialize(),
				error : onError.bind(this),
				success : onInteracaoSalva.bind(this)
			});
		} 
	}

	function validaCadastroInteracao() {
		var validacao = true;
		var divErroGeral = $('.js-div-erro-geral-interacao');
		divErroGeral.removeClass('hidden');
		var divVeiculo = $('.js-div-select-veiculo');
		var divValor = $('.js-div-input-veiculo');
		var divDescricao = $('.js-div-descricao-interacao');

		if ($('.js-combo-veiculo').val() == '') {
			divVeiculo.addClass('has-error');
			$('#js-msg-erro-veiculo').removeClass('hidden');
			validacao = false;
		} else {
			divVeiculo.removeClass('has-error');
			$('#js-msg-erro-veiculo').addClass('hidden');
		}

		if ($('.js-input-valor').val() == '') {
			divValor.addClass('has-error');
			$('#js-msg-erro-valor').removeClass('hidden');
			validacao = false;
		} else {
			divValor.removeClass('has-error');
			$('#js-msg-erro-valor').addClass('hidden');
		}

		if ($('.js-descricao-interacao').val() == '') {
			divDescricao.addClass('has-error');
			$('#js-msg-erro-descricao').removeClass('hidden');
			validacao = false;
		} else {
			divDescricao.removeClass('has-error');
			$('#js-msg-erro-descricao').addClass('hidden');
		}
		return validacao;
	}

	function onInteracaoSalva(data) {

	}

	function onError(error) {
		console.log(error);
	}

	function onErrorSalvandoTelefone(obj) {
		var mensagemErro = obj.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		this.formTelefone.find('.form-group').addClass('has-error');
	}

	function onSalvarTelefone(event) {
		var numeroTelefone = this.inputTelefone.val();
		$.ajax({
			url : this.url,
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({
				numeroUm : numeroTelefone,
				codigoPessoa : this.codigoPessoa.val()
			}),
			error : onErrorSalvandoTelefone.bind(this),
			success : onTelefoneSalve.bind(this)
		});
	}

	function onTelefoneSalve(telefone) {
		var inputTelefone = $('#telefone');
		inputTelefone.val(telefone.numeroUm);
		this.modalTelefone.modal('hide');
	}

	function onModalShow(event) {
		var cadastroCarro = new Compraki.CadastroCarro();
		cadastroCarro.iniciar();

		var uploadFoto = new Compraki.UploadFoto();
		uploadFoto.iniciar();

	}

	function onModalTelefoneShow(event) {
		this.inputTelefone.val($('#telefone').val());
	}

	return PropostaFornecedor;
}());

Compraki.CadastroCarro = (function() {

	function CadastroCarro() {
		this.botaoSalvarCarroIntermediario = $('.js-btn-salvar-carro-intermediario');
		this.selectAcessoriosEscolhidos = $('#acessoriosEscolhidos');
		this.checkBoxIpva = $('.js-ipva');
		this.comboTipoVeiculo = $('#tipoVeiculo');
		this.divDadosCarro = $('#divDadosCarro');
		this.divDadosMoto = $('#divDadosMoto');
		this.divDadosPesado = $('#divDadosPesado');
		this.codigoCarro = $('#codigoHidden');
		this.topoPagina = $('.js-topo-page');
		this.modal = $('#modalCadastroIntermediarioVeiculo');
	}

	CadastroCarro.prototype.iniciar = function(event) {
		if (this.comboTipoVeiculo.val()) {
			onAtualizaFormVeiculo.call(this, true);
		}
		this.comboTipoVeiculo.on('change', onAtualizaFormVeiculo.bind(this,
				false));
		this.botaoSalvarCarroIntermediario.on('click',
				onSalvarCarroIntermediario.bind(this));
		this.checkBoxIpva.bootstrapSwitch();
	}

	function onAtualizaFormVeiculo(validacao) {
		var codigo = this.codigoCarro.val();
		var acessorios = new Array();
		$.ajax({
			url : "/compraki/carros/atualizaFormularioVeiculo",
			method : 'GET',
			contentType : 'application/json',
			data : {
				codigoCarro : codigo,
				tipoVeiculo : this.comboTipoVeiculo.val(),
				acessorios : acessorios,
				cadastroRapidoMarca : false
			},
			error : onError.bind(this),
			success : onSucessFormulario.bind(this)
		});
	}

	function onSucessFormulario(resultado) {
		var parser = new DOMParser();
		var html = parser.parseFromString(resultado, "text/html");
		if (this.comboTipoVeiculo.val() == 'CARRO') {
			var htmlFinal = $(html).find('#targetContentTipoVeiculoCarro');
			this.divDadosCarro.html(htmlFinal);
			this.divDadosMoto.html("");
			this.divDadosPesado.html("");
		} else if (this.comboTipoVeiculo.val() == 'MOTO') {
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
		var selectSearch = new Compraki.SelectSearch();
		selectSearch.enable();

		var pickList = new Compraki.PickList();
		pickList.enable();

		$('.js-div-erro-geral').addClass('hidden');

	}

	function onError(erro) {
		console.log(erro);
	}

	function onErrorSalvandoCarro(erro) {
		console.log(erro);
	}

	function onSalvarCarroIntermediario(event) {
		event.preventDefault();
		if (validaCadastroIntermediario.call(this, getAcessoriosEscolhidos
				.call(this))) {
			onSalvar.call(this);
		} else {
			this.modal.animate({
				scrollTop : 0
			}, 'slow');
		}
	}

	function onSalvar() {
		$.ajax({
			url : "/compraki/carros/salvarCarroIntermediario",
			method : 'GET',
			contentType : 'application/json',
			data : $('.js-form-carro').serialize(),
			error : onErrorSalvandoCarro.bind(this),
			success : onCarroIntermediarioSalvo.bind(this),
		});

	}

	function onCarroIntermediarioSalvo(carroIntermediario) {
		var parser = new DOMParser();
		var html = parser.parseFromString(carroIntermediario, "text/html");
		var htmlFinal = $(html).find('#carroIntermediario');
		var comboCarro = $('.js-combo-veiculo');
		comboCarro.append(htmlFinal);
		this.modal.modal('hide');
	}

	function getAcessoriosEscolhidos() {
		var pickListAcessorios = null;
		if (this.comboTipoVeiculo.val() == 'CARRO') {
			pickListAcessorios = $('#selectAcessoriosCarro')
					.bootstrapDualListbox();
		} else if (this.comboTipoVeiculo.val() == 'MOTO') {
			pickListAcessorios = $('#selectAcessoriosMoto')
					.bootstrapDualListbox();
		} else {
			pickListAcessorios = $('#selectAcessoriosPesado')
					.bootstrapDualListbox();
		}
		var options = [];
		if (pickListAcessorios.val() != null) {
			pickListAcessorios.val().forEach(
					function(acessorio) {
						var res = acessorio.split("-");
						options.push('<option id="acessorio' + res[0]
								+ '" value="' + res[0]
								+ '" selected="selected">' + res[1]
								+ '</option>');
					});
			this.selectAcessoriosEscolhidos.html(options.join(''));
		} else {
			this.selectAcessoriosEscolhidos.html('');
		}
		return this.selectAcessoriosEscolhidos;
	}

	function validaCadastroIntermediario(acessoriosEscolhidos) {
		var validacao = true;
		var divErroGeral = $('.js-div-erro-geral');
		divErroGeral.removeClass('hidden');
		if (this.comboTipoVeiculo.val() == 'CARRO') {
			var divMarca = $('.js-div-marca');
			var divModelo = $('.js-div-modelo');
			var divCategoria = $('.js-div-categoria');
			var divAcessorios = $('.js-div-acessorios');
			var divCor = $('.js-div-cor');
			if ($('.js-combo-marca').val() == '') {
				divMarca.addClass('has-error');
				$('#js-msg-erro-marca').removeClass('hidden');
				validacao = false;
			} else {
				divMarca.removeClass('has-error');
				$('#js-msg-erro-marca').addClass('hidden');
			}
			if ($('.js-modelo').val() == '') {
				divModelo.addClass('has-error');
				$('#js-msg-erro-modelo').removeClass('hidden');
				validacao = false;
			} else {
				divModelo.removeClass('has-error');
				$('#js-msg-erro-modelo').addClass('hidden');
			}
			if ($('.js-combo-categoria').val() == '') {
				divCategoria.addClass('has-error');
				$('#js-msg-erro-categoria').removeClass('hidden');
				validacao = false;
			} else {
				divCategoria.removeClass('has-error');
				$('#js-msg-erro-categoria').addClass('hidden');
			}
			if ($('.js-combo-cor').val() == '') {
				divCor.addClass('has-error');
				$('#js-msg-erro-cor').removeClass('hidden');
				validacao = false;
			} else {
				divCor.removeClass('has-error');
				$('#js-msg-erro-cor').addClass('hidden');
			}
			if (acessoriosEscolhidos.val() == null) {
				divAcessorios.addClass('has-error');
				$('#js-msg-erro-acessorio').removeClass('hidden');
				validacao = false;
			} else {
				divAcessorios.removeClass('has-error');
				$('#js-msg-erro-acessorio').addClass('hidden');
			}

		} else if (this.comboTipoVeiculo.val() == 'MOTO') {
			var divMarca = $('.js-div-marca-moto');
			var divModelo = $('.js-div-modelo-moto');
			var divCategoria = $('.js-div-categoria-moto');
			var divAcessorios = $('.js-div-acessorios-moto');
			var divCor = $('.js-div-cor-moto');
			if ($('.js-combo-marca-moto').val() == '') {
				divMarca.addClass('has-error');
				$('#js-msg-erro-marca').removeClass('hidden');
				validacao = false;
			} else {
				$('#js-msg-erro-marca').addClass('hidden');
				divMarca.removeClass('has-error');
			}
			if ($('.js-modelo-moto').val() == '') {
				divModelo.addClass('has-error');
				$('#js-msg-erro-modelo').removeClass('hidden');
				validacao = false;
			} else {
				divModelo.removeClass('has-error');
				$('#js-msg-erro-modelo').addClass('hidden');
			}
			if ($('.js-combo-categoria-moto').val() == '') {
				divCategoria.addClass('has-error');
				$('#js-msg-erro-categoria').removeClass('hidden');
				validacao = false;
			} else {
				divCategoria.removeClass('has-error');
				$('#js-msg-erro-categoria').addClass('hidden');
			}
			if ($('.js-combo-cor-moto').val() == '') {
				divCor.addClass('has-error');
				$('#js-msg-erro-cor').removeClass('hidden');
				validacao = false;
			} else {
				divCor.removeClass('has-error');
				$('#js-msg-erro-cor').addClass('hidden');
			}
			if (acessoriosEscolhidos.val() == null) {
				divAcessorios.addClass('has-error');
				$('#js-msg-erro-acessorio').removeClass('hidden');
				validacao = false;
			} else {
				divAcessorios.removeClass('has-error');
				$('#js-msg-erro-acessorio').addClass('hidden');
			}

		} else {
			var divMarca = $('.js-div-marca-pesado');
			var divModelo = $('.js-div-modelo-pesado');
			var divCategoria = $('.js-div-categoria-pesado');
			var divAcessorios = $('.js-div-acessorios-pesado');
			var divCor = $('.js-div-cor-pesado');
			if ($('.js-combo-marca-pesado').val() == '') {
				divMarca.addClass('has-error');
				$('#js-msg-erro-marca').removeClass('hidden');
				validacao = false;
			} else {
				divMarca.removeClass('has-error');
				$('#js-msg-erro-marca').addClass('hidden');
			}
			if ($('.js-modelo-pesado').val() == '') {
				divModelo.addClass('has-error');
				$('#js-msg-erro-modelo').removeClass('hidden');
				validacao = false;
			} else {
				divModelo.removeClass('has-error');
				$('#js-msg-erro-modelo').addClass('hidden');
			}
			if ($('.js-combo-categoria-pesado').val() == '') {
				divCategoria.addClass('has-error');
				$('#js-msg-erro-categoria').removeClass('hidden');
				validacao = false;
			} else {
				divCategoria.removeClass('has-error');
				$('#js-msg-erro-categoria').addClass('hidden');
			}
			if ($('.js-combo-cor-pesado').val() == '') {
				divCor.addClass('has-error');
				$('#js-msg-erro-cor').removeClass('hidden');
				validacao = false;
			} else {
				divCor.removeClass('has-error');
				$('#js-msg-erro-cor').addClass('hidden');
			}
			if (acessoriosEscolhidos.val() == null) {
				divAcessorios.addClass('has-error');
				$('#js-msg-erro-acessorio').removeClass('hidden');
				validacao = false;
			} else {
				divAcessorios.removeClass('has-error');
				$('#js-msg-erro-acessorio').addClass('hidden');
			}
		}
		if (validacao) {
			divErroGeral.addClass('hidden');
		}
		return validacao;
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

	UploadFoto.prototype.iniciar = function() {
		var settings = {
			type : 'json',
			filelimit : 1,
			allow : '*.(jpg|jpeg|png)',
			action : this.containerFotoVeiculo.data('url-fotos'),
			complete : onUploadCompleto.bind(this),
			beforeSend : adicionarCsrfToken
		}

		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);

		if (this.inputNomeFoto.val()) {
			renderizarFoto.call(this, {
				nome : this.inputNomeFoto.val(),
				contentType : this.inputContentType.val()
			});
		}

		function renderizarFoto(resposta) {
			this.inputNomeFoto.val(resposta.nome);
			this.inputContentType.val(resposta.contentType);

			this.uploadDrop.addClass('hidden');

			var htmlFotoVeiculo = this.template({
				nomeFoto : resposta.nome
			});
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
	var propostaFornecedor = new Compraki.PropostaFornecedor();
	propostaFornecedor.enable();
});