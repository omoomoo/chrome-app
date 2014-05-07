var FormatFilter = {}; // 格式过滤器
var ValidateFilter = {}; // 验证过滤器

$('.nav a').click(function(e) {
	e.preventDefault()
	$(this).tab('show')
})

$.post('http://localhost:8080/hackit/encode/md5', {
	text : 'this is test text!这是测试文档！',
	encoding : 'UTF-8'
}, function(data) {
	console.log(data);
}, 'json')

$('#plaintext-encode-trigger').click(function(e) {
	var text = $('#plainText').val();

	console.log(text);
	$('#text').val(text);

	var $form = $('#encode-plaintext-form');
	var url = $form.attr('action');
	var params = $form.serialize();

	$.post(url, params, function(data) {
		$('#hexText').val(data.hexText);
		$('#binaryText').val(data.binaryText);
		$('#urlText').val(data.urlText);
		$('#base64Text').val(data.base64Text);
		$('#base64UrlSafeText').val(data.base64UrlSafeText);
	}, 'json');
})