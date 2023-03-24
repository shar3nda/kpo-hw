package org.example.agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class SupervisorAgent extends Agent {
    private ContainerController containerController;
    private ArrayList<AgentController> orderAgents = new ArrayList<>();

    protected void setup() {
        System.out.println("Supervisor agent " + getAID().getName() + " is ready.");
        containerController = getContainerController();
        addBehaviour(new ReceiveOrderBehaviour());
    }

    protected void takeDown() {
        System.out.println("Supervisor agent " + getAID().getName() + " is terminating.");
    }

    private class ReceiveOrderBehaviour extends Behaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    // Create a new order agent for the received order
                    String orderName = "order-" + new Date().getTime();
                    try {
                        Object[] args = {msg.getContent()};
                        AgentController orderAgent = containerController.createNewAgent(
                                orderName, OrderAgent.class.getName(), args);
                        orderAgents.add(orderAgent);
                        orderAgent.start();
                        System.out.println("Order agent " + orderName + " is created.");
                    } catch (Exception e) {
                        System.out.println("Error creating order agent " + orderName);
                        e.printStackTrace();
                    }

                    // Send a CFP message to the warehouse agent to reserve products for the order
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    cfp.addReceiver(getWarehouseAgent());
                    cfp.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
                    cfp.setContent(msg.getContent());
                    cfp.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
                    addBehaviour(new ReserveProductsBehaviour(cfp));
                } else {
                    System.out.println("Unexpected message received: " + msg.getContent());
                }
            } else {
                block();
            }
        }

        public boolean done() {
            return false;
        }
    }

    private class ReserveProductsBehaviour extends ContractNetInitiator {
        private ACLMessage request;

        public ReserveProductsBehaviour(ACLMessage cfp) {
            super(SupervisorAgent.this, cfp);
            this.request = cfp;
        }

        protected Vector prepareCfps(ACLMessage cfp) {
            Vector cfpVector = new Vector();
            cfpVector.add(cfp);
            return cfpVector;
        }

        protected void handlePropose(ACLMessage propose, Vector acceptances) {
            System.out.println("Received propose from warehouse agent: " + propose.getContent());
        }

        protected void handleRefuse(ACLMessage refuse) {
            System.out.println("Received refuse from warehouse agent: " + refuse.getContent());
        }

        protected void handleAllResponses(Vector responses, Vector acceptances) {
            // Check if all responses are received
            if (responses.size() < 1) {
                System.out.println("No responses received for CFP from warehouse agent.");
                return;
            }

            // Check if all responses are acceptances
            boolean allAcceptances = true;
            for (Object response : responses) {
                ACLMessage msg = (ACLMessage) response;
                if (msg.getPerformative() != ACLMessage.ACCEPT_PROPOSAL) {
                    allAcceptances
