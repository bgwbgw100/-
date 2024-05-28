package com.example.demo.util;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomMultiReturn<A,B,C,D,E> {
    private A a;
    private B b;
    private C c;
    private D d;
    private E e;

}
