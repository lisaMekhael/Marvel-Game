package views;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Controller.Controller;
import engine.Game;
import engine.Player;

public class championsChoice extends JFrame implements ActionListener {
	
	//setToolTip
	
	private ImageIcon icon;
	
	
	private JLabel Player1Label;
	private JLabel Player2Label;
	private JTextArea turnLabel;
	
	private JPanel champsPanel;
	private JButton[] champButtons;
	
	
	String player1;
	String player2;
	
	boolean firstPlayerTurn=true;
	
	private Game game;
	private Player firstPlayer;
	private Player secondPlayer;

	private static int noClicks =0;
	
	Font champsDetails = new Font("San Sirf" , Font.BOLD , 11);
	Font playerFont = new Font("MV Boli" , Font.BOLD , 15);
	Font Turn = new Font("Ink Free" , Font.BOLD , 30);
	
	
	
	
	public championsChoice(Game g) throws IOException  {
		this.game = g;
		Game.loadAbilities("Abilities.csv");
		Game.loadChampions("Champions.csv");
		
		firstPlayer = g.getFirstPlayer();
		secondPlayer = game.getSecondPlayer();
		
		this.setTitle("Marvel");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100 , 50 , 1000 , 600);
		icon = new ImageIcon("Avengerslogofront.png");
		this.setIconImage(icon.getImage());
		//this.setLayout(new BorderLayout());
		
		
		player1= game.getFirstPlayer().getName() + ": ";
		player2 = game.getSecondPlayer().getName() + ": ";
	
		
		turnLabel = new JTextArea("Now each player will choose a champion."+game.getSecondPlayer().getName() + "'s Turn:");
		turnLabel.setFont(Turn);
		turnLabel.setEditable(false);
		
		
		
		champsPanel = new JPanel();
		champsPanel.setLayout(new GridLayout(5,3));
		champButtons = new JButton[15];
		int j=0; 
		for(int i=0 ; i<Game.getAvailableChampions().size() ; i++) {
			
			champButtons[i] = new JButton();
			champButtons[i].setText(Game.getAvailableChampions().get(i).getName());
			champButtons[i].setActionCommand(Game.getAvailableChampions().get(i).getName());
			champButtons[i].addActionListener(this);
			champButtons[i].setToolTipText("<html>" + Game.getAvailableChampions().get(i).toString()+ "<br>"+
					Game.getAvailableAbilities().get(j).toString()+"<br>"+
					Game.getAvailableAbilities().get(j+1).toString()+ "<br>"+
					Game.getAvailableAbilities().get(j+2).toString());
			champButtons[i].setActionCommand("");
			champsPanel.add(champButtons[i]);
			j=j+3;
		}
		
        this.add(champsPanel ,BorderLayout.CENTER );
	
		
		this.add(turnLabel ,BorderLayout.NORTH);
		
	   
		
		this.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		boolean found = false;
		int j=0;
		
		if(noClicks==6) {
			
			Player2Label = new JLabel(player2);
			Player2Label.setFont(playerFont);
			Player2Label.setForeground(Color.blue);
			this.add(Player2Label ,BorderLayout.WEST);
			
			Player1Label = new JLabel(player1);
			Player1Label.setFont(playerFont);
			Player1Label.setForeground(Color.red);
			this.add(Player1Label ,BorderLayout.EAST);
			
			this.revalidate();
			this.repaint();
			
			
				try {
					new playerLeader(game);
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
			this.dispose();
			

		}else {
			if(!firstPlayerTurn) {
				turnLabel.setText(game.getSecondPlayer().getName() +"'s turn");
				turnLabel.setEditable(false);
				while(found==false) {
					if(e.getSource()==champButtons[j]) { 
						found = true;  
						noClicks++;               //copy champion chosen into player
						
						player1 = player1.concat(champButtons[j].getText());
						player1 = player1.concat(",");
						
						champButtons[j].setEnabled(false);
						
						firstPlayer.getTeam().add(Game.getAvailableChampions().get(j));
						
						firstPlayerTurn = true;
						
						
						
					}
					j++;
					
				}
				
			}else {
				turnLabel.setText(game.getFirstPlayer().getName() +"'s turn");
				turnLabel.setEditable(false);
				while(found==false) {
					if(e.getSource()==champButtons[j]) { 
						found = true;  
						noClicks++;                 //copy champion chosen into player
						
					   
						player2 = player2.concat(champButtons[j].getText());
						player2 = player2.concat(" , ");
						
						champButtons[j].setEnabled(false);
						
						secondPlayer.getTeam().add(Game.getAvailableChampions().get(j));
						
						
						firstPlayerTurn = false;
						
		            }
					j++;
				}
			}
		}


				
				
		}

			
		
			
			

		
	
	public static void main(String[]args) throws IOException {
		
	}
	
}



