/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package todo.app.common.exception;

import java.util.List;

/**
 *
 * @author shintaroh
 */
public class ErrorModel {
    
    private final List<String> errorMessages;
    
    public ErrorModel(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
    
    public List<String> getErrorMessages() {
        return errorMessages;
    }
    
    public String toString() {
        return errorMessages.toString();
    }
}
