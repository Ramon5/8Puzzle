/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import base.Search;
import entity.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import base.Heuristic;

/**
 *
 * @author decom
 */
public class GreedySearch extends Search implements Heuristic {

    
    private int heuristic;
    private List<Node> visited;

    public GreedySearch() {
        solution = new ArrayList<>();
        visited = new ArrayList<>();
        queue = new ArrayList<>();
    }

    public void setHeuristica(int heuristica) {
        this.heuristic = heuristica;
    }

    /*
        Expande os estados
     */
    public void expand(Node node, int h) {
        counter++;
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
            newNode.setCost(node.getCost() + 1);
            setFactor(newNode);
            if (rateNode(newNode, visited) == -1) {
                int k = rateNode(newNode, queue);
                if (k != -1) {
                    if (queue.get(k).getFator() > newNode.getFator()) {
                        queue.remove(k);
                    } 
                } else {
                    queue.add(newNode);
                }
            }
        }

        if (i + 1 < target[j].length) {
            Node newNode = new Node();
            newNode.setParent(node);
            int states[][] = new int[3][3];
            copy(node.getStates(), states);
            int aux = states[i][j];
            states[i][j] = states[i + 1][j];
            states[i + 1][j] = aux;
            newNode.setStates(states);
            newNode.setCost(node.getCost() + 1);
            setFactor(newNode);
            if (rateNode(newNode, visited) == -1) {
                int k = rateNode(newNode, queue);
                if (k != -1) {
                    if (queue.get(k).getFator() > newNode.getFator()) {
                        queue.remove(k);
                    } 
                } else {
                    queue.add(newNode);
                }
            }
        }

        if (j - 1 >= 0) {
            Node newNode = new Node();
            newNode.setParent(node);
            int estado[][] = new int[3][3];
            copy(node.getStates(), estado);
            int aux = estado[i][j];
            estado[i][j] = estado[i][j - 1];
            estado[i][j - 1] = aux;
            newNode.setStates(estado);
            newNode.setCost(node.getCost() + 1);
            setFactor(newNode);
            if (rateNode(newNode, visited) == -1) {
                int k = rateNode(newNode, queue);
                if (k != -1) {
                    if (queue.get(k).getFator() > newNode.getFator()) {
                        queue.remove(k);
                    } 
                } else {
                    queue.add(newNode);
                }
            }
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
            setFactor(newNode);
            if (rateNode(newNode, visited) == -1) {
                int k = rateNode(newNode, queue);
                if (k != -1) {
                    if (queue.get(k).getFator() > newNode.getFator()) {
                        queue.remove(k);
                    } 
                } else {
                    queue.add(newNode);
                }
            }
        }
        Collections.sort(queue);
    }
    
    private int rateNode(Node node, List<Node> collection) {
        for (int k = 0; k < collection.size(); k++) {
            if (isEqual(collection.get(k), node)) {
                return k;
            }
        }
        return -1;
    }

    private boolean isEqual(Node node, Node newNode) {
        for (int i = 0; i < node.getStates().length; i++) {
            for (int j = 0; j < node.getStates()[i].length; j++) {
                if (node.getStates()[i][j] != newNode.getStates()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
        Define a heuristca para a resolução do problema (Informada pelo usuario)
     */
    private void setFactor(Node node) {
        if (heuristic == 0) {
            node.setFactor(partsOutOfPlace(node));
        } else if (heuristic == 1) {
            node.setFactor(manhantanDistance(node));
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
        Efetua a busca pela solução
     */
    @Override
    public void solve(Node node) {
        setFactor(node);
        queue.add(node);

        while (!queue.isEmpty()) {
            Node newNode = (Node) queue.remove(0);
            visited.add(newNode);
            if (isGoal(newNode)) {
                visited.clear();
                JOptionPane.showMessageDialog(null, "Solução encontrada! Profundidade: " + newNode.getCost());
                while (newNode.getParent()!= null) {
                    solution.add(newNode);
                    newNode = newNode.getParent();
                    motions++;
                }

                break;
            } else {
                expand(newNode, heuristic);
            }
        }
    }

    /*
        Heuristica para o melhor fator - peças fora do lugar
     */
    @Override
    public int partsOutOfPlace(Node node) {
        int peca = 0;
        for (int i = 0; i < node.getStates().length; i++) {
            for (int j = 0; j < node.getStates()[i].length; j++) {
                if (node.getStates()[i][j] != target[i][j] && node.getStates()[i][j] != 0) {
                    peca++;
                }
            }
        }
        return peca;
    }

    /*
        Heuristica para o melhor fator - peças fora do lugar
     */
    @Override
    public int manhantanDistance(Node node) {
        int distance = 0;
        for (int i = 0; i < node.getStates().length; i++) {
            for (int j = 0; j < node.getStates()[i].length; j++) {
                int[] coordinates = getCoordinates(node.getStates()[i][j]);
                distance += Math.abs(i - coordinates[0]) + Math.abs(j - coordinates[1]);
            }
        }
        return distance;
    }
    
    /*
        Obtem o valor da linha e da coluna para um valor no estado
    */
    private int[] getCoordinates(int value) {
        int[] coordinates = new int[2];
        for (int i = 0; i < target.length; i++) {
            for (int j = 0; j < target[i].length; j++) {
                if (value == target[i][j]) {
                    coordinates[0] = i;
                    coordinates[1] = j;
                    return coordinates;
                }
            }
        }
        return null;
    }

}
