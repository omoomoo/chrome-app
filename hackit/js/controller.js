var FormatFilter = {}; // 格式过滤器
var ValidateFilter = {}; // 验证过滤器

$('.nav a').click(function(e) {
	e.preventDefault()
	$(this).tab('show')
})

$('#plaintext-encode-trigger').click(function(e) {
	var text = $('#plainText').val();

	console.log(text);
	$('#text').val(text);

	var $form = $('#encode-plaintext-form');
	var url = $form.attr('action');
	var params = $form.serialize();

	$.post(url, params, function(data) {
		$('#hexText').val(data.hex);
		$('#binaryText').val(data.binary);
		$('#urlText').val(data.url);
		$('#base64Text').val(data.base64);
		$('#base64UrlSafeText').val(data.base64UrlSafe);
	}, 'json');
})