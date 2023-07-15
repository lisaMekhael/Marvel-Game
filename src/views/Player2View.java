package views;

import java.awt.*;
import javax.swing.JOptionPane;

import Controller.Controller;
import engine.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;


public class Player2View extends JFrame implements ActionListener {
	
	private Game game;
	private JLabel PlayerLabel;
	private ImageIcon icon;
	private JTextArea title;
	private JButton ok , rename;
	
	public String name;
	
	
	
	public Player2View(Game g) throws CloneNotSupportedException  {
		this.game=g;
		 name = JOptionPane.showInputDialog("Enter Second Player Name:");
		
		 g.getSecondPlayer().setName(name);
		
		
		this.setTitle("Marvel");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(450 , 270 , 400 , 200);
		icon = new ImageIcon("Avengerslogofront.png");
		this.setIconImage(icon.getImage());
		
		
		title = new JTextArea("Welcome " + game.getSecondPlayer().getName());
		title.setBounds(20,36,150,20);
		
		title.setForeground(Color.blue);
		
		
		this.add(title);
		
		PlayerLabel = new JLabel("Are you sure you want your name to be "+ name +"?");
		PlayerLabel.setForeground(Color.blue);
		PlayerLabel.setFont(new Font("San Sirf" , Font.BOLD , 12));
		PlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PlayerLabel.setVerticalAlignment(SwingConstants.CENTER);  
		
		
		
		ok = new JButton("yes");
		rename = new JButton("No , I want to rename");
		

		ok.setBounds(50,100,100,30);
		
		rename.setBounds(200,100,170,30);
		
		ok.addActionListener(this);
		rename.addActionListener(this);
		
		PlayerLabel.add(ok);
		PlayerLabel.add(rename);
		//this.add(rename);
		
		this.add(PlayerLabel);
		this.setVisible(true);
		
		
	}
	

	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==ok) {
			this.dispose();
			try {
				new championsChoice(game);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else {
			this.dispose();
			try {
				new Player2View(game);
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}
	
	
	public static void main (String [] args) {
		
	}

}
