package com.mas.user.domain.entity.chat.vo;


public enum MsgType {
    TXT(0,""),
    IMAGE(1,""),
    VOICE(2,""),
    VIDEO(3,""),
    LOCATION(4,""),
    FILE(5,""),
    DEFINE(6,"");

    
    public int value() { return this.value; }
    public String label() { return this.label; }
    
    @Override public String toString() { return String.valueOf( value() ); } 
    
    private final int value;
    private final String label;
    private MsgType(int value, String label)
    {
        this.value = value;
        this.label = label;
    }
}