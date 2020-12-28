/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author chand
 */
@ServerEndpoint("/wsep")
public class WebSocketEP {
    private static int msgCounter=0;
    @OnMessage
    public String onMessage(Session session, String message) {
        //System.out.println(session.getMaxIdleTimeout());
//        session.setMaxIdleTimeout(300000);
        System.out.println("Sesion Ping Interval " + LocalDateTime.now() + " : " + session.getMaxIdleTimeout());

        return message.toUpperCase() + ":" + msgCounter++;
    }
    
}
