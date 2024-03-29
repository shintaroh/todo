/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.app.todo;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author shintaroh
 */
@ServerEndpoint("/todos/monitor")
public class TodoWebSocketEndpoint {

    public static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static final Logger logger = Logger.getLogger(TodoWebSocketEndpoint.class.getName());
    
    @OnOpen
    public void onOpen(Session session) {
        try {
            session.getBasicRemote().sendText("opend");
            logger.log(Level.INFO, "opened {0}", session);
            sessions.add(session);
            logger.log(Level.INFO, "{0} sessions", sessions.size());
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    @OnClose
    public void onClose(final Session session) {
        logger.log(Level.INFO, "closed {0}", session);
        sessions.remove(session);
        logger.log(Level.INFO, "{0} sessions", sessions.size());
    }
    
    @OnError
    public void onError(Throwable e) {
        logger.log(Level.SEVERE, "Unexcepted Exception happened!", e);
    }
    
    @OnMessage
    public String onMessage(String message) {
        return null;
    }
    
}
