/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ramon
 */
public class Cell extends JLabel{
    
    private int code;
    private ImageIcon image;

    public Cell() {
    }

    public Cell(int code) {
        this.code = code;
    }

    public int getCodde() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
    
    
    
}
