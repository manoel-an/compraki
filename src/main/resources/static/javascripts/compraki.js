var Compraki = Compraki || {};

Compraki.MaskMoney = (function() {
	
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
		this.simpleDecimal = $('.js-simple-decimal');
		this.integer = $('.js-integer');
	}
	
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
		this.plain.maskMoney({ precision: 0, thousands: '.' });
		this.simpleDecimal.numeric({ decimal : ".",  negative : false, scale: 5 });
		this.integer.numeric({ decimal : false,  negative : false });
	}
	
	return MaskMoney;
	
}());

Compraki.MaskPhoneNumber = (function() {
	
	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function() {
		var maskBehavior = function (val) {
		  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
		
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
		    }
		};
		
		this.inputPhoneNumber.mask(maskBehavior, options);
	}
	
	return MaskPhoneNumber;
	
}());

Compraki.MaskCep = (function() {
	
	function MaskCep() {
		this.inputCep = $('.js-cep');
	}
	
	MaskCep.prototype.enable = function() {
		this.inputCep.mask('00.000-000');
	}
	
	return MaskCep;
	
}());


Compraki.PickList = (function() {
	function PickList() {
		this.selectPickList = $('select[name="js-duallistbox"]');
	}
	
	PickList.prototype.enable = function() {
		this.selectPickList.bootstrapDualListbox({
			  nonSelectedListLabel: 'Disponíveis',
			  selectedListLabel: 'Itens Selecionados',
			  preserveSelectionOnMove: 'moved',
			  moveOnSelect: false,
			  infoText: 'Itens Disponíveis {0}',
			  infoTextEmpty: 'Lista Vazia',
			  moveAllLabel: 'Mover Todos',
			  moveSelectedLabel: 'Mover item Selecionado',
			  removeSelectedLabel: 'Remover item selecionado',
			  filterPlaceHolder: '',
			  infoTextFiltered: 'Filtrados {0} de {1}',
			  filterTextClear: 'Mostrar Todos',
			  removeAllLabel: 'Remover todos'			
		});
	}
	return PickList;
	
}());

Compraki.MaskDate = (function() {
	
	function MaskDate() {
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		});
	}
	
	return MaskDate;
	
}());

Compraki.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

numeral.language('pt-br');

Compraki.formatarMoeda = function(valor) {
	return numeral(valor).format('0,0.00');
}

Compraki.recuperarValor = function(valorFormatado) {
	return numeral().unformat(valorFormatado);
}

Compraki.SelectSearch = (function(){
	
	function SelectSearch(){
		this.selectsearch = $('.js-select-search');
	}
	
	SelectSearch.prototype.enable = function() {
		this.selectsearch.select2({
			placeholder: "Selecione uma opção"
		});
	}
	
	return SelectSearch;
	
}());

Compraki.GeneralSearch = (function(){
	
	function GeneralSearch(){
		this.inputSearch = $('.js-general-search');
		var htmlTemplateAutocomplete = $('#template-autocomplete-search').html();
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
		
	}
	
	GeneralSearch.prototype.enable = function() {
		var options = {
				url: function(modeloCidade){
					return this.inputSearch.data('url') + '?modeloCidade=' + modeloCidade;
				}.bind(this),
				getValue: 'modelo',
				minCharNumber: 3,
				requestDelay: 300,
				ajaxSettings:{
					contentType: 'application/json'
				}, template:{
					type: 'custom',
					method: template.bind(this)
				},
				list: {
					onChooseEvent: onItemSelecionado.bind(this)
				}
			};
			
			this.inputSearch.easyAutocomplete(options);		
	}
	

	
	function onItemSelecionado(){
		var codigoIntencao = $(".js-general-search").getSelectedItemData().codigo;
		var url = window.location.href+"/interacao/"+codigoIntencao;
		window.location.href = url;
	}
	
	function template(modelo, intencao){
		intencao.valorFormatado = Compraki.formatarMoeda(intencao.valor);
		return this.template(intencao);
	}	
	
	return GeneralSearch;
	
}());


$(function() {
	var maskMoney = new Compraki.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new Compraki.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskCep = new Compraki.MaskCep();
	maskCep.enable();
	
	var pickList = new Compraki.PickList();
	pickList.enable();
	
	var maskDate = new Compraki.MaskDate();
	maskDate.enable();
	
	var security = new Compraki.Security();
	security.enable();
	
	var selectSearch = new Compraki.SelectSearch();
	selectSearch.enable();
	
	var generalSearch = new Compraki.GeneralSearch();
	generalSearch.enable();
	
});