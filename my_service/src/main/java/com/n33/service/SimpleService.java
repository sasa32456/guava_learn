package com.n33.service;

public class SimpleService implements Service {
    @Override
    public void show() {
        System.out.println("hi i come from the service loader.");
    }
}
