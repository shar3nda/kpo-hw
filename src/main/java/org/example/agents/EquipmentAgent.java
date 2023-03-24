package org.example.agents;

import jade.core.Agent;

public class EquipmentAgent extends Agent {

    private String name; // name of the equipment
    private String type; // type of the equipment
    private boolean inUse; // flag to indicate if the equipment is currently in use

    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 1) {
            name = (String) args[0];
            type = (String) args[1];
            inUse = false;
        } else {
            System.out.println("Error: equipment agent must be initialized with a name and type");
            doDelete();
        }
    }

    // get the name of the equipment
    public String getName() {
        return name;
    }

    // get the type of the equipment
    public String getType() {
        return type;
    }

    // check if the equipment is currently in use
    public boolean isInUse() {
        return inUse;
    }

    // set the process agent that controls this equipment
    public void setProcessAgent(ProcessAgent processAgent) {
        this.processAgent = processAgent;
    }

    // set the cook agent that is currently using this equipment
    public void setCookAgent(CookAgent cookAgent) {
        this.cookAgent = cookAgent;
    }

    // mark the equipment as in use by a cook agent
    public void markAsInUse() {
        inUse = true;
    }

    // mark the equipment as available for use
    public void markAsAvailable() {
        inUse = false;
    }

    // send a message to the cook agent to start using the equipment
    public void startUsing(CookAgent cookAgent) {
        this.cookAgent = cookAgent;
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(cookAgent.getAID());
        msg.setContent("Start using equipment " + name);
        send(msg);
    }

    // send a message to the cook agent to stop using the equipment
    public void stopUsing() {
        if (cookAgent != null) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(cookAgent.getAID());
            msg.setContent("Stop using equipment " + name);
            send(msg);
            cookAgent = null;
        }
    }
}
