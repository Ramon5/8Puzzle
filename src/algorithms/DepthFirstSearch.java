/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import base.Search;
import entity.Node;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author ramon
 * Busca em profundidade
 */
public class DepthFirstSearch extends Search{

    
    private int max;
    private final int depth;
    private boolean solved;

    public DepthFirstSearch() {
        stack = new Stack();
        solution = new ArrayList<>();
        max = 0;
        motions = 0;
        depth = 0;
    }

    public void setMax(int max) {
        this.max = max;
    }  
    

    /*
        Copia o novo estado para um novo Nó
     */
    private void copy(int matrix[][], int states[][]) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                states[i][j] = matrix[i][j];
            }
        }
    }

    /*
        Expande os estados
     */
    public void expand(Node node) {
        counter++;
        if (node.getCost() < max) {
            int zero[] = zeroLocate(node);
            int i = zero[0];
            int j = zero[1];
            if (i - 1 >= 0) {
                Node newNode = new Node();
                newNode.setParent(node);
                int states[][] = new int[3][3];
                copy(node.getStates(), states);
                int aux = states[i][j];
                states[i][j] = states[i - 1][j];
                states[i - 1][j] = aux;
                newNode.setStates(states);
                newNode.setCost(node.getCost()+ 1);
                stack.push(newNode);
            }

            if (i + 1 < target[j].length) {
                Node newNode = new Node();
                newNode.setParent(node);
                int[][] states = new int[3][3];
                copy(node.getStates(), states);
                int aux = states[i][j];
                states[i][j] = states[i + 1][j];
                states[i + 1][j] = aux;
                newNode.setStates(states);
                newNode.setCost(node.getCost() + 1);
                stack.push(newNode);
            }
            
            if (j - 1 >= 0) {
                Node newNode = new Node();
                newNode.setParent(node);
                int states[][] = new int[3][3];
                copy(node.getStates(), states);
                int aux = states[i][j];
                states[i][j] = states[i][j - 1];
                states[i][j - 1] = aux;
                newNode.setStates(states);
                newNode.setCost(node.getCost() + 1);
                stack.push(newNode);
            }

            if (j + 1 < target[i].length) {
                Node newNode = new Node();
                newNode.setParent(node);
                int states[][] = new int[3][3];
                copy(node.getStates(), states);
                int aux = states[i][j];
                states[i][j] = states[i][j + 1];
                states[i][j + 1] = aux;
                newNode.setStates(states);
                newNode.setCost(node.getCost() + 1);
                stack.push(newNode);
            }
        }
        
    }

    /*
        Localiza a peça vazia no estado (0)
     */
    private int[] zeroLocate(Node node) {
        int zero[] = new int[2];
        for (int i = 0; i < node.getStates().length; i++) {
            for (int j = 0; j < node.getStates()[i].length; j++) {
                if (node.getStates()[i][j] == 0) {
                    zero[0] = i;
                    zero[1] = j;
                    return zero;
                }
            }
        }
        return zero;
    }


    /*
        verifica se o estado é o objetivo final
     */
    private boolean isGoal(Node node) {
        int states[][] = node.getStates();
        for (int i = 0; i < target.length; i++) {
            for (int j = 0; j < target[i].length; j++) {
                if (states[i][j] != target[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /*
        Aprofundamento Iterativo
    */
    private void iterationLimit(Node node){
        if(!solved){
            counter = 0;
            max++;
            solve(node);
        }
    }

    @Override
    public void solve(Node node) {
        node.setCost(depth);
        stack.push(node);
        while (!stack.isEmpty()) {
            Node newNode = stack.pop();
            if (isGoal(newNode)) {
                solved = true;
                JOptionPane.showMessageDialog(null, "Solução encontrada! Profundidade " + newNode.getCost());
                while (newNode.getParent()!= null) { 
                    solution.add(newNode);
                    newNode = newNode.getParent();                    
                    motions++;
                }                
                
                break;
                
            } else {
                expand(newNode);
            }
        }
        iterationLimit(node);
    }

    

}
