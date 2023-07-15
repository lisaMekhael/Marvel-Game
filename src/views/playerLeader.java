package views;

import java.awt.*;
import Controller.Controller;

import java.awt.event.*;
import javax.swing.*;

import engine.Game;
import engine.Player;



public class playerLeader extends JFrame  implements ActionListener { 
	
	private Player p1 , p2; 
	private Game game;
	
	public playerLeader( Game g) throws CloneNotSupportedException {
		this.game = g;
	p1=	g.getFirstPlayer();
	p2=	g.getSecondPlayer();
		
		
		
		
		String[] options = { p1.getTeam().get(0).getName() , 
				p1.getTeam().get(1).getName() , 
				p1.getTeam().get(2).getName() };
		
		int num = JOptionPane.showOptionDialog(null, "Choose your leader:", p1.getName(),
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,1);	
		
		if(num==0) {
			p1.setLeader(p1.getTeam().get(0));
		}else if(num==1) {
			p1.setLeader(p1.getTeam().get(1));
		} else if(num==2) {
			p1.setLeader(p1.getTeam().get(2));
		}
		
		String[] options2 = { p2.getTeam().get(0).getName() , 
				p2.getTeam().get(1).getName() , 
				p2.getTeam().get(2).getName() };
		
		int num2 = JOptionPane.showOptionDialog(null, "Choose your leader:", p2.getName() ,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2,1);	
		
		if(num2==0) {
			p2.setLeader(p2.getTeam().get(0));
		}else if(num2==1) {
			p2.setLeader(p2.getTeam().get(1));
		} else if(num2==2) {
			p2.setLeader(p2.getTeam().get(2));
		}
		
		
		JOptionPane.showMessageDialog(null, p1.getName() + " your color will be red", "Marvel",JOptionPane.INFORMATION_MESSAGE);

		JOptionPane.showMessageDialog(null, p2.getName() + " your color will be blue", "Marvel",JOptionPane.INFORMATION_MESSAGE);
	
		 new boardView(game);
		
	}
	
	 
	
	

	public static void main(String[] args) {
		
		

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
