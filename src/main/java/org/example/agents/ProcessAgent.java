package org.example.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ProcessAgent extends Agent {

    protected void setup() {
        // Add behaviour to receive and process messages
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    // Process message
                    String content = msg.getContent();
                    String[] parts = content.split(":");
                    String operation = parts[0];
                    String product = parts[1];
                    int time = Integer.parseInt(parts[2]);
                    switch (operation) {
                        case "start":
                            System.out.println(getLocalName() + ": Starting process for " + product);
                            // TODO: Perform the process for the given product
                            break;
                        case "stop":
                            System.out.println(getLocalName() + ": Stopping process for " + product);
                            // TODO: Stop the process for the given product
                            break;
                        case "time":
                            System.out.println(getLocalName() + ": Estimated time for " + product + " is " + time + " minutes");
                            // TODO: Provide estimated time for the process of the given product
                            break;
                    }
                }
                else {
                    block();
                }
            }
        });
    }

}
