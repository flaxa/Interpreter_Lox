/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loxInterpreter;

/**
 *
 * @author Josh
 */
public enum TokenType {
    //single character tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, COMMA,
    DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,
    
    //one or two character tokens
    BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
    
    //Literals
    IDENTIFIER, STRING, NUMBER,
    
    //Keywords
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR, PRINT, RETURN,
    SUPER, THIS, TRUE, VAR, WHILE,
    
    EOF
    
}
