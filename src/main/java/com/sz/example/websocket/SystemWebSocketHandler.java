package com.sz.example.websocket;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.sz.common.util.CommonUtils;

@Component
public class SystemWebSocketHandler implements WebSocketHandler {
	
	Logger logger=Logger.getLogger(SystemWebSocketHandler.class);
	
	public static final Map<String,WebSocketSession> userMap=new HashMap<String,WebSocketSession>();

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
		logger.info("######执行了afterConnectionClosed");
		this.removeSession(session);
		
		String userid=(String)session.getAttributes().get("userid");
		MessageBean messageBean=new MessageBean();
        messageBean.setId(userid);
        messageBean.setMessage(userid+"退出");
        messageBean.setTime(CommonUtils.dateFormat(new Date(), null));
        messageBean.setMessageType("2");
        this.sendMessage(messageBean);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("######执行了afterConnectionEstablished");
		String userid=(String)session.getAttributes().get("userid");
        userMap.put(userid,session);
        
        MessageBean messageBean=new MessageBean();
        messageBean.setId(userid);
        messageBean.setMessage(userid+"连接成功");
        messageBean.setTime(CommonUtils.dateFormat(new Date(), null));
        messageBean.setMessageType("1");
        this.sendMessage(messageBean);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		logger.info("######执行了handleMessage");
		MessageBean messageBean=new MessageBean();
        messageBean.setMessage(message.toString());
        messageBean.setTime(CommonUtils.dateFormat(new Date(), null));
		this.sendMessage(messageBean);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable arg1) throws Exception {
		logger.info("######执行了handleTransportError");
		if(session.isOpen()){  
            session.close();  
        }  
        this.removeSession(session);

	}

	@Override
	public boolean supportsPartialMessages() {
		logger.info("######执行了supportsPartialMessages");
		return false;
	}
	
	
	private String removeSession(WebSocketSession session){
    	String userid="";
    	for (Entry<String, WebSocketSession> entry : userMap.entrySet()) {
    		String key=entry.getKey();
            if(session.getId().equals(userMap.get(key).getId())){
            	userid=key;
            	break;
            }
        }
    	userMap.remove(userid);
    	return userid;
    }
	
	public void sendMessage(MessageBean messageBean) throws IOException{
		String userid=messageBean.getUserid();
		if(userid==null || "".equals(userid)){
			Collection<WebSocketSession> allSession=userMap.values();
			for (WebSocketSession session : allSession) {
	            if(session.isOpen()){
	            	session.sendMessage(new TextMessage(JSON.toJSONString(messageBean)));
	            }
	        }
		}else{
			WebSocketSession session=userMap.get(userid);
			if(session!=null && session.isOpen()){
				session.sendMessage(new TextMessage(JSON.toJSONString(messageBean)));
			}
		}
	}
	

}
