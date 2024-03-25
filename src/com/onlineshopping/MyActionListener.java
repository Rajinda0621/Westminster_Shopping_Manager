package com.onlineshopping;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton shoppingCartBtn = (JButton) e.getSource();

         if (e.getSource() == shoppingCartBtn){
             ShoppingCartGUI shoppingCartFrame = ShoppingCartGUI.getInstance();
             shoppingCartFrame.setTitle("Shopping Cart");
             shoppingCartFrame.setSize(700,600);
             shoppingCartFrame.setVisible(true);

         }
    }
}
