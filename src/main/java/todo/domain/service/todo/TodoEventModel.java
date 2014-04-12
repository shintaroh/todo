/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.domain.service.todo;

import todo.domain.model.Todo;

/**
 *
 * @author shintaroh
 */
public class TodoEventModel {
    
    public static enum EventType {
        CREATE, UPDATE, DELETE
    }
    
    private final Todo todo;
    private final EventType type;
    
    public TodoEventModel(Todo todo, EventType type) {
        this.todo = todo;
        this.type = type;
    }

    public Todo getTodo() {
        return todo;
    }

    public EventType getType() {
        return type;
    }
    
    public String toString() {
        return "TodoEventModel{" + "todo=" + todo + ", type=" + type + "}";
    }
    
}
