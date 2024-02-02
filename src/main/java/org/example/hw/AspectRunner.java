package org.example.hw;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AspectRunner {

    private final List<Car> cars;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println(cars);
        for (Car car : cars) {
            try {
                car.method1("fast", 2);
                car.method2();
                car.method3();
            } catch (Throwable e) {
                log.error("was executed");
            }
        }
    }

}