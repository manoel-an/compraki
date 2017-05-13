var Compraki = Compraki || {};

Compraki.IntencaoCompra = (function() {
	
	function IntencaoCompra() {
		this.inputMarca = $('.js-input-marca');
	}
	
	IntencaoCompra.prototype.enable = function(event) {
		this.inputMarca.on('blur', teste.bind(this));
		
	}
	
	function teste(event){
		alert('oi');
	}
	
	return IntencaoCompra;
}());

$(function() {
	var intencaoCompra = new Compraki.IntencaoCompra();
	intencaoCompra.enable();
});