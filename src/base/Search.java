/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import entity.Node;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author ramon
 */
public abstract class Search {
    
    protected int[][] target = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    protected Stack<Node> stack;
    protected List<Node> queue;
    protected List solution;
    protected int motions;
    protected int counter;
    
    public abstract void solve(Node node);
    
    public List getSolution(){
        return solution;
    }

    public int getMotions() {
        return motions;
    }

    public void setMotions(int motions) {
        this.motions = motions;
    }
    
    public void flushSolution(){
        solution.clear();
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    /*
        ==== Fluxo das Buscas =====
        
        1 - solucionar()
        2 - expandir()
        3 - copiar()
        
        -----
        4 - definirFator()
        5 - avaliado()
    */
    
    
}
