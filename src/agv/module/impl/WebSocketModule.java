/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package agv.module.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import agv.pageModel.Msg;

@ServerEndpoint("/websocket")
public class WebSocketModule {
	
	private static final Set<Session> sessionSet = new HashSet<Session>();
	private static final  Logger systemLogger = Logger.getLogger("agv");
	private static final Logger userLogger = Logger.getLogger("agv.user");
	
	@OnOpen
    public void open(Session session) {
		sessionSet.add(session);
		systemLogger.info("建立Websocket连接");
	}
	
	@OnClose
	public void close(Session session) {
		sessionSet.remove(session);
		systemLogger.info("关闭Websocket连接");
	}
	
	@OnError
	public void onError(Throwable error) {
		systemLogger.error("Websocket连接错误："+error.getMessage());
	}
	
	@OnMessage
	public void handleMessage(String message, Session session) {
		systemLogger.info("收到Websocket信息："+message);
	}

	private static void sendToAllConnectedSessions(String message) {
        for (Session session : sessionSet) {
            sendToSession(session, message);
        }
    }

    private static void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
        	sessionSet.remove(session);
            systemLogger.error("发送Websocket信息错误：" + ex.getMessage());
        }
    }

	public static void sendMsg(Msg msg) {
		Gson gson = new Gson();
		sendToAllConnectedSessions(gson.toJson(msg));
		systemLogger.info("发送Websocket信息："+msg);
	}
}
