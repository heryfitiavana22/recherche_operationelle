package com.heryfitiavana.R_O;

public class Node {
    private String name;
    private String label = ""; // etiquette
    private int delta = 0;
    private Boolean canPass = true;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setCanPass(Boolean canPass) {
        this.canPass = canPass;
    }

    public Boolean getCanPass() {
        return canPass;
    }
}
