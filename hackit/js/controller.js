var FormatFilter = {};
// 格式过滤器
var ValidateFilter = {};
// 验证过滤器
var _encoding = 'UTF-8';

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

	var url = ' http://infootav1.softwinners.com/hackit/encode';
	var params = {
		text : text,
		encoding : _encoding
	}

	$.post(url, params, function(data) {
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

	var url = ' http://infootav1.softwinners.com/hackit/decode';
	var params = {
		text : text,
		textType : textType,
		encoding : _encoding
	}

	$.post(url, params, function(data) {
		$('#plainText').val(data.plain);
		$('#hexText').val(data.hex);
		$('#binaryText').val(data.binary);
		$('#urlText').val(data.url);
		$('#base64Text').val(data.base64);
		$('#base64UrlSafeText').val(data.base64UrlSafe);
	}, 'json');
})
