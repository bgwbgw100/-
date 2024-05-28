package com.example.demo.util;

import lombok.Getter;

@Getter
public class CustomTwoReturn<T,V>{
    private final T type1;
    private final V type2;

    public CustomTwoReturn(T type1, V type2){
        this.type1 = type1;
        this.type2 = type2;
    }

}
