/**
Script to manage a EV3 connection using WebSockets
*/

var websocket = null;

$(function() {
	if ("WebSocket" in window){
	
	}else{
		var message = "Your Web Browser doesn't support WebSocket API";
		writeToScreen(message)
	}
});

$(function() {
	$('.connectWS').click(function() {
		var ip = $('#ip').val();
		var port = $('#port').val(); 
		var wsUri = "ws://" + ip + ":" + port;
		websocket = new WebSocket(wsUri);
		websocket.binaryType = "arraybuffer";
		websocket.onopen = function(evt) { onOpen(evt) };
		websocket.onclose = function(evt) { onClose(evt) };
		websocket.onmessage = function(evt) { onMessage(evt) };
		websocket.onerror = function(evt) { onError(evt) };
	});
});

$(function() {
	$('.disconnectWS').click(function() {
		websocket.close();
	});
});

function onOpen(evt){
	writeToScreen("CONNECTED");
}

function onMessage(evt){
    writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
}

function onClose(evt){
    writeToScreen("DISCONNECTED");
}

function onError(evt){
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function doSend(message){
    writeToScreen("SENT: " + message); 
    websocket.send(message);
}

function writeToScreen(message){
	var output = document.getElementById("output");
	var pre = document.createElement("p");
	pre.style.wordWrap = "break-word";
	pre.innerHTML = message;
	output.appendChild(pre);
}
