/*
Script to manage the remote control with a EV3 Robot
*/

$(function() {
	$('.btUP').click(function() {
		var message = $('.btUP').val();
		doSend(message);
	});
});

$(function() {
	$('.btDOWN').click(function() {
		var message = $('.btDOWN').val();
		doSend(message);
	});
});

$(function() {
	$('.btLEFT').click(function() {
		var message = $('.btLEFT').val();
		doSend(message);
	});
});

$(function() {
	$('.btRIGHT').click(function() {
		var message = $('.btRIGHT').val();
		doSend(message);
	});
});
