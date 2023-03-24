package org.example.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;

public class DishAgent extends Agent {
    private String dishName;
    private ArrayList<ProcessAgent> processAgents;
    private ArrayList<OperationAgent> operationAgents;
    private ArrayList<ProductAgent> productAgents;

    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            dishName = args[0].toString();
            processAgents = new ArrayList<>();
            operationAgents = new ArrayList<>();
            productAgents = new ArrayList<>();
            System.out.println("Dish agent " + getAID().getName() + " for " + dishName + " is ready.");
            addBehaviour(new ProcessRequestBehaviour());
        } else {
            System.out.println("No dish name specified.");
            doDelete();
        }
    }

    protected void takeDown() {
        System.out.println("Dish agent " + getAID().getName() + " for " + dishName + " is terminating.");
    }

    private class ProcessRequestBehaviour extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String content = msg.getContent();
                ACLMessage reply = msg.createReply();
                if (content.equals("process")) {
                    ProcessAgent processAgent = new ProcessAgent(dishName);
                    processAgents.add(processAgent);
                    getAgent().getContainerController().acceptNewAgent(processAgent.getAID().getName(), processAgent);
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("process created");
                } else if (content.equals("operation")) {
                    OperationAgent operationAgent = new OperationAgent(dishName);
                    operationAgents.add(operationAgent);
                    getAgent().getContainerController().acceptNewAgent(operationAgent.getAID().getName(), operationAgent);
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("operation created");
                } else if (content.equals("product")) {
                    ProductAgent productAgent = new ProductAgent(dishName);
                    productAgents.add(productAgent);
                    getAgent().getContainerController().acceptNewAgent(productAgent.getAID().getName(), productAgent);
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("product created");
                } else {
                    reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                    reply.setContent("unknown request");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
