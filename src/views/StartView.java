package views;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

import engine.Game;

public class StartView extends JFrame  implements ActionListener {
	
    private Game game;
	private JButton startButton;
	private JLabel mainLabel;
	private ImageIcon icon;
	
	
	
	Font start = new Font("Ink Free" , Font.ITALIC, 30);
	
	public StartView(Game g) throws CloneNotSupportedException  {
		this.game=g;
		
		this.setTitle("Marvel");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100 , 50 , 1000 , 600);
		icon = new ImageIcon("Avengerslogofront.png");
		this.setIconImage(icon.getImage());
		
		
		
		mainLabel = new JLabel(new ImageIcon("MarvelCharacters.jpg"),SwingConstants.CENTER);
		
		startButton = new JButton();
		startButton.setIcon(( new ImageIcon("Start.png")));
		startButton.setText("Click here to start");
		startButton.setBounds( 410, 450 , 180 ,60);   //x , y , , 
		startButton.addActionListener(this);
	
		
		
		
		
		this.add(startButton);
		this.add(mainLabel);  
		
		
		this.setVisible(true);
	   
		
	}
	
	
	
	
	public static void main(String[]args) {
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==startButton) {
			this.dispose();
		    try {
				new Player1View(game);
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
	

}
