/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import entity.Node;

/**
 *
 * @author decom
 */
public interface Heuristic {
    
    public int partsOutOfPlace(Node node);
    public int manhantanDistance(Node node);
    
}
