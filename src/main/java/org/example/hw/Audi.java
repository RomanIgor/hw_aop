package org.example.hw;

import org.slf4j.event.Level;
import org.springframework.stereotype.Component;


@Component
public class Audi implements Car {

    @Timer(level = Level.WARN)
    public void method1(String arg1, int arg2) {

    }

    @Timer (level = Level.WARN)
    public String method2() {
        return "value";
    }

    public String method3() {
        throw new RuntimeException("runtimeexceptionmsg");
    }

}