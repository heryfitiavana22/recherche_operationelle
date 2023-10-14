package com.heryfitiavana.R_O;

public class Edge {
    private int flow = 0;
    private int capacity;

    public Edge(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFlow(int flow) {
        this.flow += flow;
    }

    public int getFlow() {
        return flow;
    }
}
