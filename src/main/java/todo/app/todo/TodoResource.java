/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.app.todo;

import java.net.URI;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import todo.domain.model.Todo;
import todo.domain.service.todo.TodoService;

/**
 *
 * @author shintaroh
 */
@Path("todos")
public class TodoResource {
    
    @EJB
    protected TodoService todoService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Todo> getTodos() {
        return todoService.findAll();
    }
    
    @GET
    @Path("{todoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Todo getTodo(@PathParam("todoId") Integer todoId) {
        return todoService.findOne(todoId);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postTodos(Todo todo, @Context UriInfo uriInfo) {
        Todo createdTodo = todoService.create(todo);
        Integer todoId = createdTodo.getTodoId();
        URI newUri = uriInfo.getRequestUriBuilder().path(todoId.toString()).build();
        return Response.created(newUri).entity(createdTodo).build();
    }
}
