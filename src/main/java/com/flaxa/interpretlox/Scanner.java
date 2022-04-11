/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flaxa.interpretlox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.flaxa.interpretlox.TokenType.*;

/**
 *
 * @author Josh
 */
public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<Token>();
    private int start = 0;
    private int current = 0;
    private int line = 0;
    private static final Map<String,TokenType> keywords;
    
    static{
        keywords = new HashMap<>();
        keywords.put("and",AND);
        keywords.put("class",CLASS);
        keywords.put("else",ELSE);
        keywords.put("false",FALSE);
        keywords.put("true",TRUE);
        keywords.put("for",FOR);
        keywords.put("while",WHILE);
        keywords.put("fun",FUN);
        keywords.put("if",IF);
        keywords.put("nil",NIL);
        keywords.put("or",OR);
        keywords.put("print",PRINT);
        keywords.put("return",RETURN);
        keywords.put("super",SUPER);
        keywords.put("this",THIS);
        keywords.put("var",VAR);
        
        
    }

    public Scanner(String source) {
        this.source = source;
    }
    
    List<Token> scanTokens(){
        while(!isAtEnd()){
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF,"",null,line));
        return tokens;
    }
    
    private boolean isAtEnd(){
        return current >=source.length();
    }
    
    private void scanToken(){
        
        char c = advance();
        switch(c){
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '!': 
                if(match('='))
                    addToken(BANG_EQUAL);
                else
                    addToken(BANG);
                break;
            case '=':
                if(match('='))
                    addToken(EQUAL_EQUAL);
                else
                    addToken(EQUAL);
                break;
            case '<': 
                if(match('='))
                    addToken(LESS_EQUAL);
                else
                    addToken(LESS);
                break;
            case '>': 
                if(match('='))
                    addToken(GREATER_EQUAL);
                else
                    addToken(GREATER);
                break;
            case '/':
                if(match('/')){
                    while(peek() != '\n' && !isAtEnd()){
                        current++;//may not work advance() instead
                    }
                }
                else if(match('*')){
                    blockComment();
                }
                else
                {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            case '"':
                string();
                break;
            default:
                if(isDigit(c)){
                    number();
                }
                else if(isAlpha(c)){
                    identifier();
                    
                    
                }
                else{
                    Lox.error(line, "Unexpected Character!");
                }
                break;

        }
    }
    
    private char advance(){
        
        return source.charAt(current++);
    }
    
    private void addToken(TokenType type){
        addToken(type,null);
    }
    
    private void addToken(TokenType type, Object literal){
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
        
    }
    
    private boolean match(char expected){
        if(isAtEnd()){
            return false;
        }
        if(source.charAt(current) != expected){
            return false;
        }
        current++;
        return true;
    }
    
    private char peek(){
        if(isAtEnd())
            return '\0';
        return source.charAt(current);
    }
    
    private void string(){
        while(peek() != '"' && !isAtEnd()){
            if(peek() == '\n'){
                line++;
            }
            advance();
        }
        
        if(isAtEnd()){
            Lox.error(line,"Unterminated string");
            return;
        }
        
        advance();
        
        String value = source.substring(start+1,current-1);
        addToken(STRING,value);
        
    }
    
    private boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }
    
    private void number(){
        while(isDigit(peek())){
            advance();
        }
        
        if(peek()=='.' && isDigit(peekNext())){
            advance();
            
            while(isDigit(peek())){
                advance();
            }
        }
        
        addToken(NUMBER,Double.parseDouble(source.substring(start,current)));
        
        
             
    }
    
    private char peekNext(){
        if(current+ 1 >= source.length()){
            return '\0';
        }
        return source.charAt(current+1);
    }
    
    private boolean isAlpha(char c){
        return ((c>='a' && c<='z')||(c>='A' && c<='Z') || (c=='_'));
    }
    
    private void identifier(){
        while(isAlphaNumeric(peek())){
            advance();
        }
        String text = source.substring(start,current);
        TokenType type = keywords.get(text);
        
        if(type == null){
            type = IDENTIFIER;
        }
        addToken(type);
    }
    
    private boolean isAlphaNumeric(char c){
        return ((c>='a' && c<='z')||(c>='A' && c<='Z') || (c=='_') || (c>='0' && c<='9') );
    }
    
    private void blockComment(){
        while(peek()!='*' && peekNext()!='/'){
            if(peek()=='\n'){
                line++;
            }
            current++;
        }
        current++;
        current++;
    }
    
    
}
