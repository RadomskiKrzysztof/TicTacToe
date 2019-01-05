package com.radomskikrzysztof;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame{
	
	public TicTacToe() {
		super("Kó³ko i krzy¿yk");
		setBounds(400, 150, 600, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLookAndFeel();
		GamePanel tp = new GamePanel();
		add(tp);
		setVisible(true);
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception exc) {
			System.err.println("Nie wczyta³em wygl¹du systemu: " + exc);
		}
	}
	
	public static void main(String[] arguments) {
		TicTacToe ttt = new TicTacToe();
	}
}