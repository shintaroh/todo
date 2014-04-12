/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.domain.service.todo;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import todo.domain.common.exception.BusinessException;
import todo.domain.common.exception.ResourceNotFoundException;
import todo.domain.model.Todo;

/**
 *
 * @author shintaroh
 */
@Stateless
public class TodoService {

    private static final long MAX_UNFINISHED_COUNT = 5;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    protected EntityManager em;
    @Inject
    @TodoEvent
    protected Event<TodoEventModel> todoEvent;
    
    public List<Todo> findAll() {
        TypedQuery<Todo> q = em.createNamedQuery("Todo.findAll", Todo.class);
        return q.getResultList();
    }
    
    public Todo findOne(Integer todoId) {
        Todo todo = em.find(Todo.class, todoId);
        if (todo == null) {
            throw new ResourceNotFoundException("[E404] The requested Todo is not found. (id=" + todoId + ")");
        }
        return todo;
    }
    
    public Todo create(Todo todo) {
        TypedQuery<Long> q = 
                em.createNamedQuery("Todo.findCountByFinished", Long.class).setParameter("finished", false);
        long unfinishedCount = q.getSingleResult();
        System.out.println(unfinishedCount);
        if (unfinishedCount >= MAX_UNFINISHED_COUNT) {
            throw new BusinessException("[E001] The count of un-finished Todo must not be over " + MAX_UNFINISHED_COUNT + ".");
        }
        todo.setFinished(false);
        todo.setCreatedAt(new Date());
        em.persist(todo);
        em.flush();
        todoEvent.fire(new TodoEventModel(todo, TodoEventModel.EventType.CREATE));
        return todo;
    }
    
    public Todo finish(Integer todoId) {
        Todo todo = findOne(todoId);
        
        if (todo.isFinished()) {
            throw new BusinessException("[E002] The requested Todo is already finished. (id=" + todoId + ")");
        }
        todo.setFinished(true);
        em.merge(todo);
        todoEvent.fire(new TodoEventModel(todo, TodoEventModel.EventType.UPDATE));
        return todo;
    }
    
    public void delete(Integer todoId) {
        Todo todo = findOne(todoId);
        em.remove(todo);
        todoEvent.fire(new TodoEventModel(todo, TodoEventModel.EventType.DELETE));
    }
}
