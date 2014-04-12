/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.domain.service.todo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.websocket.Session;
import todo.app.todo.TodoWebSocketEndpoint;

/**
 *
 * @author shintaroh
*/
@ApplicationScoped
public class TodoEventObserver {
    
    private static final Logger logger = Logger.getLogger(TodoEventObserver.class.getName());
    
    public void onEventMessage(@Observes @TodoEvent TodoEventModel todoEventModel) {
        for (Session s: TodoWebSocketEndpoint.sessions) {
            try {
                s.getBasicRemote().sendText(todoEventModel.toString());
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
