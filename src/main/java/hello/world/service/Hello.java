package hello.world.service;

import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Service
public class Hello {

    private String location = ManagementFactory.getRuntimeMXBean().getName();

    public String hello(String message) {
        return "hello " + message + ", welcome to " + location;
    }
}
