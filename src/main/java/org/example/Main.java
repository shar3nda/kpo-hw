package org.example;

import jade.core.Agent;

public class Main extends Agent {
    protected void setup() {
        System.out.println("Hello World. My name is " + getLocalName());
    }
}