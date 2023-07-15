package Controller;

import engine.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import engine.*;
import views.*;


public class Controller implements ActionListener {
	
	private Game game;
	public static Player player1;
	
	String p1 , p2;
	public static Player player2;
	
	public Controller() throws CloneNotSupportedException  {
		
		//p1 = Player1View().getName();
		//p2=  Player2View().getName();
		
		
		
		
		 player1 = new Player("faw");
		 player2 = new Player("paop");
		 
		 game = new Game(player1, player2);
		 
		 new StartView(game);
		
		
		
	}
	
	private Player2View Player2View() {
		// TODO Auto-generated method stub
		return null;
	}

	private Player1View Player1View() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Player getPlayer1() {
		return player1;
	}


	public static Player getPlayer2() {
		return player2;
	}
	

	public static void main(String[] args) throws CloneNotSupportedException {
		
		new Controller();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
