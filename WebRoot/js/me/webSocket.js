function getWebSocketRootPath() {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPath = curWwwPath.substring(0, pos);
    localhostPath = localhostPath.replace("https","ws");
    localhostPath = localhostPath.replace("http","ws");
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPath + projectName + "/");
}

var wsUri = getWebSocketRootPath() + "websocket";
var websocket;
var isWebSocketOpen= false;
function initWebSocket(){
	websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpenWebsockt(evt) };
    websocket.onclose = function(evt) { onCloseWebsockt(evt) };
    websocket.onmessage = function(evt) { onMessageWebsockt(evt) };
    websocket.onerror = function(evt) { onErrorWebsockt(evt) };
}

function onOpenWebsockt(evt)
{
  console.log("websocket:","connected");
  isWebSocketOpen = true;
}

function onCloseWebsockt(evt)
{
	console.log("websocket:","disconnected");
	isWebSocketOpen = false;
}


function onMessageWebsockt(evt)
{
	console.log("websocket msg:",evt.data);
	onGetWebsocketMsg(evt.data);
}

function onErrorWebsockt(evt)
{
	console.log("websocket error:",evt.data);
}

function doSendWebsockt(message)
{
	console.log("websocket send:",message);
	websocket.send(message);
}

//收到消息回调
function onGetWebsocketMsg(msg){
	
}

