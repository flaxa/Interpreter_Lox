/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loxInterpreter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Josh
 */
public class Enviroment {
    private final Map<String,Object> values = new HashMap<>();
    
    
    void define (String name, Object value){
        values.put(name, value);
    }
    
    Object get(Token name){
        if(values.containsKey(name.lexeme)){
            return values.get(name.lexeme);
        }
        throw new RuntimeError(name, "Undefined variable '"+name.lexeme + "'.");
    }
    
}
