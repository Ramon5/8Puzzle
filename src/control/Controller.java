/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import algorithms.AStar;
import algorithms.GreedySearch;
import algorithms.BreathFirstSearch;
import algorithms.DepthFirstSearch;
import base.Search;
import entity.Cell;
import entity.Node;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author ramon
 */
public class Controller {

    private List<Cell> list;
    private List<Node> solution;
    private JPanel panel;
    private int states[][];
    private JLabel time;
    private JLabel totalMotions;
    private JLabel step;
    private JLabel amountNodes;
    private JButton solve;
    private JButton exchange;
    private JButton nextStep;
    private JProgressBar progress;

    private int next;
    private Search search;
    private Node node;

    public Controller(JPanel panel) {
        this.panel = panel;
        list = new ArrayList<>();
        node = new Node();
        next = 0;
    }

    public Controller() {
        list = new ArrayList<>();
        list = new ArrayList<>();
        node = new Node();
        next = 0;
    }

    /*
        Configura os possíveis estados teste
     */
    public void setTest(int num) {
        switch (num) {
            case 0: {
                int[][] test = {{7, 1, 3}, {2, 6, 0}, {5, 4, 8}};
                states = test;
                break;
            }
            case 1: {
                int[][] test = {{4, 3, 5}, {2, 0, 8}, {7, 6, 1}};
                states = test;
                break;
            }
            case 2: {
                int[][] test = {{7, 2, 4}, {5, 0, 6}, {8, 3, 1}};
                states = test;
                break;
            }
            default:
                break;
        }
        next = 0;
        node.setParent(null);
        node.setStates(states);
        buildImage(node);
        populateGrid();
    }

    /*
        Gera um novo estado aleatório até que este seja solúvel
     */
    private void generateState() {
        states = new int[3][3];

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        while (!isSolvable(list)) {
            Collections.shuffle(list);
        }

        int k = 0;
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[i].length; j++) {
                states[i][j] = list.get(k);
                k++;
            }
        }
        node.setParent(null);
        node.setStates(states);
        buildImage(node);
        populateGrid();
    }

    /*
        Verifica se um estado é solúvel
     */
    private boolean isSolvable(List<Integer> lista) {
        int inversoes = 0;

        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                if (lista.get(i) > lista.get(j)) {
                    inversoes++;
                }
                if (lista.get(i) == 0 && i % 2 == 1) {
                    inversoes++;
                }
            }
        }

        if (inversoes % 2 == 1) {
            return false;
        } else {
            return true;
        }
    }

    /*
        Mapeia as imagens no painel de acordo com as posições de cada peça
     */
    private void buildImage(Node no) {

        List<Integer> list = new ArrayList<>();
        int states[][] = no.getStates();
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[i].length; j++) {
                list.add(states[i][j]);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Cell cel = new Cell(i);
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + list.get(i) + ".jpg"));
            Image img = icon.getImage().getScaledInstance(152, 120, Image.SCALE_DEFAULT);
            cel.setIcon(new ImageIcon(img));
            this.list.add(cel);
        }

    }

    /*
        Preenche o painel com as imagens de acordo com a posição das peças em cada estado
     */
    private void populateGrid() {
        panel.removeAll();
        GridBagConstraints grid = new GridBagConstraints();
        int k = 0;
        for (int i = 0; i < 3; i++) {
            grid.gridy = i;
            for (int j = 0; j < 3; j++) {
                grid.gridx = j;
                panel.add(list.get(k), grid);
                k++;
            }
        }
        list.clear();
        panel.updateUI();
    }

    /*
        Realiza a busca pelo estado objetivo de acordo com o algoritmo escolhido
     */
    public void searchSolution(int modo, int h) {

        switch (modo) {
            case 0:
                new Thread() {
                    @Override
                    public void run() {
                        search = new DepthFirstSearch();
                        disable();
                        progress.setIndeterminate(true);
                        Instant startP = Instant.now();

                        search.solve(node);

                        Instant endP = Instant.now();
                        progress.setIndeterminate(false);
                        Duration decorrido = Duration.between(startP, endP);
                        amountNodes.setText(String.valueOf(search.getCounter()));
                        time.setText(String.valueOf(decorrido.toMillis()) + " milisegundos");
                        totalMotions.setText(String.valueOf(search.getMotions()));
                        solution = search.getSolution();
                        search.setCounter(0);
                        Collections.reverse(solution);
                        enable();
                    }
                }.start();
                break;
            case 1:
                new Thread() {
                    @Override
                    public void run() {
                        search = new BreathFirstSearch();
                        disable();
                        progress.setIndeterminate(true);
                        Instant startL = Instant.now();

                        search.solve(node);

                        Instant endL = Instant.now();
                        progress.setIndeterminate(false);
                        Duration decorrido = Duration.between(startL, endL);
                        amountNodes.setText(String.valueOf(search.getCounter()));
                        time.setText(String.valueOf(decorrido.toMillis()) + " milisegundos");
                        totalMotions.setText(String.valueOf(search.getMotions()));
                        solution = search.getSolution();
                        search.setCounter(0);
                        Collections.reverse(solution);
                        enable();
                    }
                }.start();
                break;
            case 2:
                new Thread() {
                    @Override
                    public void run() {
                        GreedySearch greedySearch = new GreedySearch();
                        disable();
                        greedySearch.setHeuristica(h);
                        progress.setIndeterminate(true);
                        Instant startG = Instant.now();
                        greedySearch.solve(node);
                        Instant endG = Instant.now();
                        progress.setIndeterminate(false);
                        Duration elapsedTime = Duration.between(startG, endG);
                        amountNodes.setText(String.valueOf(greedySearch.getCounter()));
                        time.setText(String.valueOf(elapsedTime.toMillis()) + " milisegundos");
                        totalMotions.setText(String.valueOf(greedySearch.getMotions()));
                        solution = greedySearch.getSolution();
                        greedySearch.setMotions(0);
                        greedySearch.setCounter(0);
                        Collections.reverse(solution);
                        enable();
                    }
                }.start();
                break;
            case 3:
                new Thread() {
                    @Override
                    public void run() {
                        AStar astar = new AStar();
                        disable();
                        astar.setHeuristic(h);
                        progress.setIndeterminate(true);
                        Instant startA = Instant.now();
                        astar.solve(node);
                        Instant endA = Instant.now();
                        progress.setIndeterminate(false);
                        Duration elapsedTime = Duration.between(startA, endA);
                        amountNodes.setText(String.valueOf(astar.getCounter()));
                        time.setText(String.valueOf(elapsedTime.toMillis()) + " milisegundos");
                        totalMotions.setText(String.valueOf(astar.getMotions()));
                        solution = astar.getSolution();
                        astar.setMotions(0);
                        astar.setCounter(0);
                        Collections.reverse(solution);
                        enable();
                    }

                }.start();
                break;
        }
    }

    /*
        Exibe os movimentos necessários para solucionar o desafio (Por cliques)
     */
    public void showSteps() {
        if (solution != null) {
            if (!solution.isEmpty()) {
                Node node = (Node) solution.remove(0);
                next++;
                step.setText(String.valueOf(next));
                buildImage(node);
                populateGrid();

            } else {
                nextStep.setEnabled(false);
                next = 0;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma solução encontrada!");
        }
    }


    /*
        Gera um novo estado aleatório
     */
    public void shuffle() {
        generateState();
        
        if (solution != null) {
            solution.clear();
        }
        solve.setEnabled(true);
        exchange.setEnabled(false);
        step.setEnabled(false);
        next = 0;
    }

    /* Funcoes Set  */
    public void setButtons(JButton select, JButton exchange, JButton next) {
        this.solve = select;
        this.exchange = exchange;
        this.nextStep = next;
    }

    private void enable() {
        exchange.setEnabled(true);
        nextStep.setEnabled(true);
    }

    private void disable() {
        solve.setEnabled(false);
        exchange.setEnabled(false);
        nextStep.setEnabled(false);
    }

    public List<Cell> getList() {
        return list;
    }

    public void setTime(JLabel time) {
        this.time = time;
    }

    public void setTotalMotions(JLabel totalMotions) {
        this.totalMotions = totalMotions;
    }

    public void setStep(JLabel step) {
        this.step = step;
    }

    public void setAmountNodes(JLabel amountNodes) {
        this.amountNodes = amountNodes;
    }

    public void setProgress(JProgressBar progress) {
        this.progress = progress;
    }

}
