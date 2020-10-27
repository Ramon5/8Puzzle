/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import gui.MainView;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ramon
 */
public class SystemInitialization {

    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.put("logoString", "Sandman");
            HiFiLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            MainView mainView = new MainView();
            mainView.setLocationRelativeTo(null);
            mainView.setVisible(true);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SystemInitialization.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
