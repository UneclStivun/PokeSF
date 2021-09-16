//Websocket configuration
var webSocket = new WebSocket("ws://localhost:8080/PokeSF/websocketendpoint");
webSocket.onopen = function(){ wsOpen();};
webSocket.onclose = function(){ wsClose();};
webSocket.onmessage = function(message){ wsGetMessage(message);};
webSocket.onerror = function(){ wsError();};
	
function wsOpen(){
	out.println("websocket error\nCheck if websocket refers to project name in websocket.js.");
}

function wsClose(){
}

function wsCloseConnection(){
	webSocket.close();
}

function wsSendMessage(message){
	webSocket.send(message);
}

function wsGetMessage(message){
}

function wsError(){
}
