(function($){
	$.initWebsocket = function(opts) {
		var def={
			wsPath:"127.0.0.1:8080/basetest",
			userid:"",
			onopen:function(event){},
			onmessage:function(event){},
			onerror:function(event){},
			onclose:function(event){}
		};
		var options=$.extend(def,opts);
		
		var websocket;
		if ('WebSocket' in window) {
			websocket = new WebSocket("ws://" + options.wsPath + "/wsServer?userid="+options.userid);
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://" + options.wsPath + "/wsServer?userid"+options.userid);
		} else {
			websocket = new SockJS("http://" + options.wsPath + "/wsServer/sockjs?userid"+options.userid);
		}
		websocket.onopen = function(event) {
			options.onopen(event);
		};
		websocket.onmessage = function(event) {
			options.onmessage(event);
		};
		websocket.onerror = function(event) {
			options.onerror(event);
		};
		websocket.onclose = function(event) {
			options.onclose(event);
		}
	};
	

})(jQuery);

