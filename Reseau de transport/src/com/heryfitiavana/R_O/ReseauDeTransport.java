package com.heryfitiavana.R_O;

import java.util.ArrayList;

public class ReseauDeTransport {
    private Edge[][] graph = new Edge[10][10];
    private ArrayList<Node> nodes = new ArrayList<Node>();

    void initNetWork() {
        this.nodes.add(new Node("a"));
        this.nodes.add(new Node("b"));
        this.nodes.add(new Node("d"));
        this.nodes.add(new Node("e"));
        this.nodes.add(new Node("g"));
        this.nodes.add(new Node("h"));
        this.nodes.add(new Node("s"));
        this.nodes.add(new Node("z"));

        this.graph[0][1] = new Edge(7);
        this.graph[0][6] = new Edge(2);
        this.graph[1][2] = new Edge(5);
        this.graph[1][6] = new Edge(1);
        this.graph[2][3] = new Edge(2);
        this.graph[2][5] = new Edge(4);
        this.graph[2][6] = new Edge(3);
        this.graph[3][5] = new Edge(5);
        this.graph[4][5] = new Edge(3);
        this.graph[4][7] = new Edge(3);
        this.graph[5][7] = new Edge(6);
        this.graph[6][3] = new Edge(4);
    }

    public void resolve() {
        initNetWork();
        initSource();
        initNodeLabels();
        sentFlow(2);
        sentFlow(3);
        sentFlow(1);

        displayResult();
    }

    void initSource() {
        this.nodes.get(0).setLabel("-");
        this.nodes.get(0).setDelta(Integer.MAX_VALUE); // INFINI
    }

    void initNodeLabels() {
        // initialisation des etiquettes pour chaque noeud
        for (int i = 0; i < this.nodes.size(); i++) {
            Node sourceNode = this.nodes.get(i);
            Edge[] adjacentToCurrentNode = this.graph[i];
            for (int j = 0; j < nodes.size(); j++) {
                Edge edgeToNextNode = adjacentToCurrentNode[j];
                if (edgeToNextNode != null) {
                    int restCapacity = edgeToNextNode.getCapacity() - (edgeToNextNode.getFlow());
                    if (restCapacity > 0) {
                        Node nextNode = this.nodes.get(j);
                        // pas d'etiquete
                        if (nextNode.getLabel().isEmpty()) {
                            int deltaX = Math.min(restCapacity, sourceNode.getDelta());
                            nextNode.setDelta(deltaX);
                            nextNode.setLabel(sourceNode.getName() + "+");
                            edgeToNextNode.setFlow(0);
                        }
                    }
                }
            }

        }
    }

    void sentFlow(int flowSent) {
        if (!canSentFlow(flowSent)) {
            System.out.println("Ne peut pas envoyer le flot : " + flowSent);
            System.out.println("Flot maximal déjà atteint");
            return;
        }
        int indexCurrentNode = 0;
        Node currentNode = this.nodes.get(indexCurrentNode);
        Node evier = this.nodes.get(nodes.size() - 1);
        while (currentNode.getName() != evier.getName()) {
            Edge[] adjacentToCurrentNode = this.graph[indexCurrentNode];
            for (int i = 0; i < nodes.size(); i++) {
                Edge edgeToNextNode = adjacentToCurrentNode[i];
                if (edgeToNextNode != null) {
                    Node nextNode = this.nodes.get(i);
                    int restCapacityEdge = edgeToNextNode.getCapacity() - (edgeToNextNode.getFlow() + flowSent);
                    if (restCapacityEdge >= 0) {
                        edgeToNextNode.setFlow(flowSent);
                        if (restCapacityEdge > 0) {
                            int deltaY = Math.min(restCapacityEdge, nextNode.getDelta());
                            nextNode.setDelta(deltaY);
                            nextNode.setLabel(currentNode.getName() + "+");
                        }
                        if (restCapacityEdge == 0) {
                            updateLabelNode(i);
                        }
                        currentNode = this.nodes.get(i);
                        indexCurrentNode = i;
                        break; // on sort quand on a mis une etiquette sur un noeud adjcent
                    }

                }
            }
        }
        System.out.println("flowsent " + flowSent + "\n");
    }

    boolean canSentFlow(int flow) {
        int indexCurrentNode = 0;
        Node currentNode = this.nodes.get(indexCurrentNode);
        Node evier = this.nodes.get(nodes.size() - 1);
        int count = 0;
        while (currentNode.getName() != evier.getName() && count < this.nodes.size()) {
            Edge[] adjacentToCurrentNode = this.graph[indexCurrentNode];
            for (int i = 0; i < nodes.size(); i++) {
                Edge edgeToNextNode = adjacentToCurrentNode[i];
                if (edgeToNextNode != null) {
                    int restCapacityEdge = edgeToNextNode.getCapacity() - (edgeToNextNode.getFlow() + flow);
                    if (restCapacityEdge >= 0) {
                        currentNode = this.nodes.get(i);
                        indexCurrentNode = i;
                        break;
                    }

                }
            }
            count++;
        }
        return currentNode.getName() == evier.getName();
    }

    void updateLabelNode(int indexNode) {
        Node node = this.nodes.get(indexNode);
        Boolean hasLabel = false;
        for (int i = 0; i < nodes.size(); i++) {
            if (hasLabel)
                break;
            for (int j = 0; j < nodes.size(); j++) {
                // prendre les successeurs de indexNode
                // (le colonne dans l'indexNode)
                if (j == indexNode) {
                    Edge edge = this.graph[i][j];
                    if (edge != null) {
                        int restCapacity = edge.getCapacity() - edge.getFlow();
                        if (restCapacity > 0) {
                            Node previousNode = this.nodes.get(i);
                            int deltaY = Math.min(restCapacity, node.getDelta());
                            node.setDelta(deltaY);
                            node.setLabel(previousNode.getName() + "+");
                            hasLabel = true; // on sort quand le noeud a une etiquette
                            break;
                        }
                    }
                }
            }
        }
    }

    void displayResult() {
        System.out.println("\nResultat final : ");
        int totalNode = nodes.size();
        for (int i = 0; i < totalNode; i++) {
            Edge[] rows = this.graph[i];
            Node currentNode = this.nodes.get(i);
            System.out
                    .println(currentNode.getName() + "(" + currentNode.getLabel() + "," + currentNode.getDelta() + ")");
            for (int j = 0; j < totalNode; j++) {
                Edge edgeToNextNode = rows[j];
                Node nextNode = this.nodes.get(j);
                if (edgeToNextNode != null) {
                    System.out.println(
                            currentNode.getName() + "=>" + edgeToNextNode.getCapacity() + ","
                                    + edgeToNextNode.getFlow() + "=>" + nextNode.getName());
                }
            }
            System.out.println("");
        }
    }

}
