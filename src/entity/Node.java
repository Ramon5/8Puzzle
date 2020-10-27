/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


/**
 *
 * @author ramon
 */
public class Node implements Comparable<Node>{
    
    private Node parent;
    private int[][] states;
    private int factor;
    private int cost;
    

    public Node() {
        cost = 0;
        factor = 0;
        cost = 0;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int[][] getStates() {
        return states;
    }

    public void setStates(int[][] states) {
        this.states = states;
    }

    public int getFator() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }
    

    @Override
    public int compareTo(Node t) {
        if(this.factor < t.getFator()){
            return -1;
        }else if (this.factor > t.getFator()){
            return 1;
        }
        return 0;
    }

    
 
    
    
    
}
