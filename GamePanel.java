package com.radomskikrzysztof;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {
	CardLayout cards = new CardLayout();
	JButton onePlayer = new JButton("1 gracz");
	JButton twoPlayers = new JButton("2 graczy");
	ImageIcon x = new ImageIcon("X.png");
	ImageIcon o = new ImageIcon("O.png");
	JButton buttonX = new JButton(x);
	JButton buttonO = new JButton(o);
	SecondPanel[] panels = new SecondPanel[4];
	String response;
	String response2;
	JButton player = new JButton("Gracz");
	JButton computer = new JButton("Komputer");
	
	int currentCard = 0;
	int count = 0;
	
	Player user = new Player(count, buttonX, response);
	Player secondUser = new Player(count, buttonO, response);
	public GamePanel() {
		super();
		setSize(500, 450);
		setLayout(cards);
		panels[0] = new SecondPanel("Wybierz tryb:", onePlayer, twoPlayers);
		add(panels[0]);
		panels[1] = new SecondPanel("Kto zaczyna?", player, computer);
		add(panels[1]);
		panels[2] = new SecondPanel("Graczu rozpoczynaj¹cy, wybierz znak:", buttonO, buttonX);
		add(panels[2]);
		panels[3] = new SecondPanel();
		add(panels[3]);
		addListeners();
		onePlayer.setEnabled(false); //czasowo
	}
	
	public void actionPerformed(ActionEvent event) {
		currentCard++;
		Object source = event.getSource();
		//if (source == onePlayer) {
			
		//}
		if (source == twoPlayers) {
			response = JOptionPane.showInputDialog(null, "Graczu 1, podaj swoje imiê");
			response2 = JOptionPane.showInputDialog(null, "Graczu 2, podaj swoje imiê");
			player.setText(response);
			computer.setText(response2);
			user.name = response;
			secondUser.name = response2;
			panels[3].name1.setText(response);
			panels[3].name2.setText(response2);
			}
		if (source == player) {
			user.counter = 1;
			secondUser.counter = 2;
			user.counter2 = 1;
			secondUser.counter2 = 2;
			}
		if (source == computer) {
			user.counter = 2;
			secondUser.counter = 1;
			user.counter2 = 2;
			secondUser.counter2 = 1;
			}
		if (source == buttonO) {
			if (user.counter2 < secondUser.counter2) {
				user.button.setIcon(o);
				secondUser.button.setIcon(x);
			} else {
				user.button.setIcon(x);
				secondUser.button.setIcon(o);
			}
			}
		if (source == buttonX) {
			if (user.counter2 < secondUser.counter2) {
				user.button.setIcon(x);
				secondUser.button.setIcon(o);
			} else {
				user.button.setIcon(o);
				secondUser.button.setIcon(x);
			}
			}
			
		for (int i =0; i < panels[3].buttons.length; i++) {
		if (source == panels[3].buttons[i]) {
			if (user.counter < secondUser.counter) {
			panels[3].buttons[i].setIcon(user.button.getIcon());
			panels[3].buttons[i].repaint();
			panels[3].buttons[i].removeActionListener(this);
			checkWin(panels[3].buttons);
			user.counter +=2;
			} else {
				panels[3].buttons[i].setIcon(secondUser.button.getIcon());
				panels[3].buttons[i].repaint();
				panels[3].buttons[i].removeActionListener(this);
				checkWin(panels[3].buttons); 
				secondUser.counter +=2;
			}
		}
		}
		if (source == panels[3].exit) {
			System.exit(0);
		}
		//dziala,ale czasami przestawia w menu wyboru ikony - trzeba
		//pamietac ze po lewej jest O a po prawej X
		if (source == panels[3].zero) {
			currentCard = 0;
			user.score = 0;
			secondUser.score = 0;
			movingOrder();
			resetFencingFloor(panels[3].buttons);
			panels[3].wins1.setText(Integer.toString(user.score));
			panels[3].wins2.setText(Integer.toString(secondUser.score));
			buttonO.setIcon(o);
			buttonX.setIcon(x);
			cards.show(this, "Karta" + currentCard); 
		}
		if (source == panels[3].nextRound) {
			movingOrder();
			resetFencingFloor(panels[3].buttons);
		}
		
		cards.show(this, "Karta" + currentCard);
	}
	private void movingOrder() {
		if (user.counter2 < secondUser.counter2) {
			user.counter = 2;
			secondUser.counter = 1;
			int temp;
			temp = user.counter2;
			user.counter2 = secondUser.counter2;
			secondUser.counter2 = temp;
		} else {
			user.counter = 1;
			secondUser.counter = 2;
			int temp;
			temp = user.counter2;
			user.counter2 = secondUser.counter2;
			secondUser.counter2 = temp;
		}
		for (int i = 0; i < panels[3].buttons.length; i++) {
			panels[3].buttons[i].addActionListener(this);
		}
	}
	
	private void addListeners() {
		onePlayer.addActionListener(this);
		twoPlayers.addActionListener(this);
		buttonO.addActionListener(this);
		buttonX.addActionListener(this);
		player.addActionListener(this);
		computer.addActionListener(this);
		
		panels[3].nextRound.addActionListener(this);
		panels[3].zero.addActionListener(this);
		panels[3].exit.addActionListener(this);
		for (int i =0; i < panels.length; i++) {
			add(panels[i], "Karta" + i);
		}
		for (int i = 0; i < panels[3].buttons.length; i++) {
			panels[3].buttons[i].addActionListener(this);
		}
	}
	public void resetFencingFloor(JButton[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i].setIcon(null);
			array[i].setBackground(null);
		}
	}
	
	public void checkWin(JButton[] button) {
	
		if ((button[2].getIcon() == x || button[2].getIcon() == o) && button[2].getIcon() == (button[4].getIcon()) && button[2].getIcon() == (button[6].getIcon())) {
			button[2].setBackground(new Color(51,255,0));
			button[4].setBackground(new Color(51,255,0));
			button[6].setBackground(new Color(51,255,0));
			for ( int q =0; q < panels[3].buttons.length; q++) {
				panels[3].buttons[q].removeActionListener(this);
			}
			if (button[2].getIcon() == user.button.getIcon()) {
			JOptionPane.showMessageDialog(null, "Wygrana " + user.name + "!");
			user.score++;
			panels[3].wins1.setText(Integer.toString(user.score));
			} else {
				JOptionPane.showMessageDialog(null, "Wygrana " + secondUser.name + "!");
				secondUser.score++;
				panels[3].wins2.setText(Integer.toString(secondUser.score));
			}
		}
		if ((button[0].getIcon() == x || button[0].getIcon() == o) && button[0].getIcon() == (button[4].getIcon()) && button[0].getIcon() == (button[8].getIcon())) {
			button[0].setBackground(new Color(51,255,0));
			button[4].setBackground(new Color(51,255,0));
			button[8].setBackground(new Color(51,255,0));
			for ( int q =0; q < panels[3].buttons.length; q++) {
				panels[3].buttons[q].removeActionListener(this);
			}
			if (button[0].getIcon() == user.button.getIcon()) {
				JOptionPane.showMessageDialog(null, "Wygrana " + user.name + "!");
				user.score++;
				panels[3].wins1.setText(Integer.toString(user.score));
				} else {
					JOptionPane.showMessageDialog(null, "Wygrana " + secondUser.name + "!");
					secondUser.score++;
					panels[3].wins2.setText(Integer.toString(secondUser.score));
				}
		}
		if ((button[2].getIcon() == x || button[2].getIcon() == o) && button[2].getIcon() == (button[5].getIcon()) && button[2].getIcon() == (button[8].getIcon())) {
			button[2].setBackground(new Color(51,255,0));
			button[5].setBackground(new Color(51,255,0));
			button[8].setBackground(new Color(51,255,0));
			for ( int q =0; q < panels[3].buttons.length; q++) {
				panels[3].buttons[q].removeActionListener(this);
			}
			if (button[2].getIcon() == user.button.getIcon()) {
				JOptionPane.showMessageDialog(null, "Wygrana " + user.name + "!");
				user.score++;
				panels[3].wins1.setText(Integer.toString(user.score));
				} else {
					JOptionPane.showMessageDialog(null, "Wygrana " + secondUser.name + "!");
					secondUser.score++;
					panels[3].wins2.setText(Integer.toString(secondUser.score));
				}
		}
		if ((button[1].getIcon() == x || button[1].getIcon() == o) && button[1].getIcon() == (button[4].getIcon()) && button[1].getIcon() == (button[7].getIcon())) {
			button[1].setBackground(new Color(51,255,0));
			button[4].setBackground(new Color(51,255,0));
			button[7].setBackground(new Color(51,255,0));
			for ( int q =0; q < panels[3].buttons.length; q++) {
				panels[3].buttons[q].removeActionListener(this);
			}
			if (button[1].getIcon() == user.button.getIcon()) {
				JOptionPane.showMessageDialog(null, "Wygrana " + user.name + "!");
				user.score++;
				panels[3].wins1.setText(Integer.toString(user.score));
				} else {
					JOptionPane.showMessageDialog(null, "Wygrana " + secondUser.name + "!");
					secondUser.score++;
					panels[3].wins2.setText(Integer.toString(secondUser.score));
				}
		}
		if ((button[0].getIcon() == x || button[0].getIcon() == o) && button[0].getIcon() == (button[3].getIcon()) && button[0].getIcon() == (button[6].getIcon())) {
			button[0].setBackground(new Color(51,255,0));
			button[3].setBackground(new Color(51,255,0));
			button[6].setBackground(new Color(51,255,0));
			for ( int q =0; q < panels[3].buttons.length; q++) {
				panels[3].buttons[q].removeActionListener(this);
			}
			if (button[0].getIcon() == user.button.getIcon()) {
				JOptionPane.showMessageDialog(null, "Wygrana " + user.name + "!");
				user.score++;
				panels[3].wins1.setText(Integer.toString(user.score));
				} else {
					JOptionPane.showMessageDialog(null, "Wygrana " + secondUser.name + "!");
					secondUser.score++;
					panels[3].wins2.setText(Integer.toString(secondUser.score));
				}
		}
		if ((button[6].getIcon() == x || button[6].getIcon() == o) && button[6].getIcon() == (button[7].getIcon()) && button[6].getIcon() == (button[8].getIcon())) {
			button[6].setBackground(new Color(51,255,0));
			button[7].setBackground(new Color(51,255,0));
			button[8].setBackground(new Color(51,255,0));
			for ( int q =0; q < panels[3].buttons.length; q++) {
				panels[3].buttons[q].removeActionListener(this);
			}
			if (button[6].getIcon() == user.button.getIcon()) {
				JOptionPane.showMessageDialog(null, "Wygrana " + user.name + "!");
				user.score++;
				panels[3].wins1.setText(Integer.toString(user.score));
				} else {
					JOptionPane.showMessageDialog(null, "Wygrana " + secondUser.name + "!");
					secondUser.score++;
					panels[3].wins2.setText(Integer.toString(secondUser.score));
				}
		}
		if ((button[3].getIcon() == x || button[3].getIcon() == o) && button[3].getIcon() == (button[4].getIcon()) && button[3].getIcon() == (button[5].getIcon())) {
			button[3].setBackground(new Color(51,255,0));
			button[4].setBackground(new Color(51,255,0));
			button[5].setBackground(new Color(51,255,0));
			for ( int q =0; q < panels[3].buttons.length; q++) {
				panels[3].buttons[q].removeActionListener(this);
			}
			if (button[3].getIcon() == user.button.getIcon()) {
				JOptionPane.showMessageDialog(null, "Wygrana " + user.name + "!");
				user.score++;
				panels[3].wins1.setText(Integer.toString(user.score));
				} else {
					JOptionPane.showMessageDialog(null, "Wygrana " + secondUser.name + "!");
					secondUser.score++;
					panels[3].wins2.setText(Integer.toString(secondUser.score));
				}
		}
		if ((button[0].getIcon() == x || button[0].getIcon() == o) && button[0].getIcon() == (button[1].getIcon()) && button[0].getIcon() == (button[2].getIcon())) {
			button[0].setBackground(new Color(51,255,0));
			button[1].setBackground(new Color(51,255,0));
			button[2].setBackground(new Color(51,255,0));
			for ( int q =0; q < panels[3].buttons.length; q++) {
				panels[3].buttons[q].removeActionListener(this);
			}
			if (button[0].getIcon() == user.button.getIcon()) {
				JOptionPane.showMessageDialog(null, "Wygrana " + user.name + "!");
				user.score++;
				panels[3].wins1.setText(Integer.toString(user.score));
				} else {
					JOptionPane.showMessageDialog(null, "Wygrana " + secondUser.name + "!");
					secondUser.score++;
					panels[3].wins2.setText(Integer.toString(secondUser.score));
				}
		}
		
}


class SecondPanel extends JPanel {
	JButton[] buttons = new JButton[9];
	JLabel name1;
	JLabel name2;
	JLabel wins1;
	JLabel wins2;
	JButton nextRound;
	JButton zero;
	JButton exit;
	
	SecondPanel(String labels, JButton onePlayer, JButton twoPlayers) {
		super();
		setSize(300, 250);
		setLayout(new BorderLayout());
		JPanel pane1 = new JPanel();
		JLabel label1 = new JLabel(labels);
		pane1.add(label1);
		JPanel pane2 = new JPanel();
		pane2.add(onePlayer);
		pane2.add(twoPlayers);
		add(pane1, BorderLayout.NORTH);
		add(pane2, BorderLayout.CENTER);
	}
	
	SecondPanel() {
		super();
		setSize(300, 250);
		setLayout(new BorderLayout());
		JPanel fencingFloor = new JPanel();
		fencingFloor.setLayout(new GridLayout(3,3));
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			fencingFloor.add(buttons[i]);
		}
		add(fencingFloor, BorderLayout.CENTER);
		nextRound = new JButton("Kolejna tura");
		zero = new JButton("Od nowa"); // cofa do 1 karty
		exit = new JButton("WyjdŸ");
		JPanel threeButtons = new JPanel();
		threeButtons.add(nextRound);
		threeButtons.add(zero);
		threeButtons.add(exit);
		add(threeButtons, BorderLayout.EAST);
		JPanel displayScore = new JPanel();
		name1 = new JLabel(user.name);
		name2 = new JLabel(secondUser.name);
		wins1 = new JLabel(Integer.toString(user.score));
		wins2 = new JLabel(Integer.toString(secondUser.score));
		name1.setFont(new Font("Arial Narrow", Font.BOLD + Font.ITALIC, 20));
		name2.setFont(new Font("Arial Narrow", Font.BOLD + Font.ITALIC, 20));
		wins1.setFont(new Font("Arial Narrow", Font.BOLD, 20));
		wins2.setFont(new Font("Arial Narrow", Font.BOLD, 20));
		wins1.setForeground(new Color(255,0,0));
		wins2.setForeground(new Color(255,0,0));
		displayScore.add(name1);
		displayScore.add(wins1);
		displayScore.add(name2);
		displayScore.add(wins2);
		add(displayScore, BorderLayout.NORTH);
	}
}

class Player {
	JButton button;
	int counter;
	String name;
	int score;
	int counter2;
	Player(int counter, JButton button, String name) {
		this.counter = counter;
		this.button = button;
		this.name = name;
		score = 0;
	}
}
}
