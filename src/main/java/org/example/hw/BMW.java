package org.example.hw;

import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

@Timer (level = Level.INFO)
@Component
public class BMW implements Car {

    public void method1(String arg1, int arg2) {

    }

    public String method2() {
        return "faster";
    }

    public String method3() {
        throw new RuntimeException("runtimeexceptionmsg");
    }

}