var FormatFilter = {};
// 格式过滤器
var ValidateFilter = {};
// 验证过滤器
var _encoding = 'UTF-8';

//var url = {
//	encode : 'http://infootav1.softwinners.com/hackit/encode',
//	decode : 'http://infootav1.softwinners.com/hackit/decode'
//}

var url = {
	encode : 'http://localhost:8080/hackit/encode',
	decode : 'http://localhost:8080/hackit/decode'
}

$('.nav a').click(function(e) {
	e.preventDefault()
	$(this).tab('show')
})
/**
 * 常用编解码之下拉菜单
 */
$('#encode-decode-tool-menu .dropdown-menu a').click(function(e) {
	window._encoding = $(this).context.innerText;
	$('#choosed-encoding').text(_encoding);
})
/**
 * 点击编码
 */
$('.encode-trigger').click(function(e) {
	var textType = $(this).attr('data-textType');
	var text = $('#' + textType + 'Text').val();

	var params = {
		text : text,
		encoding : _encoding
	}

	$.post(url.encode, params, function(data) {
		$('#plainText').val(data.plain);
		$('#hexText').val(data.hex);
		$('#binaryText').val(data.binary);
		$('#urlText').val(data.url);
		$('#base64Text').val(data.base64);
		$('#base64UrlSafeText').val(data.base64UrlSafe);
	}, 'json');
})
/**
 * 点击解码按钮
 */
$('.decode-trigger').click(function(e) {
	var textType = $(this).attr('data-textType');
	var text = $('#' + textType + 'Text').val();

	var params = {
		text : text,
		textType : textType,
		encoding : _encoding
	}

	$.post(url.decode, params, function(data) {
		$('#plainText').val(data.plain);
		$('#hexText').val(data.hex);
		$('#binaryText').val(data.binary);
		$('#urlText').val(data.url);
		$('#base64Text').val(data.base64);
		$('#base64UrlSafeText').val(data.base64UrlSafe);
	}, 'json');
})
/**
 * 全局ajax错误
 *
 */
$(document).ajaxError(function(event, jqxhr, settings, exception) {
	$('.encode-decode-tool-error-dialog').show();
	$('.encode-decode-tool-error-dialog').find('span').text(exception);
});
