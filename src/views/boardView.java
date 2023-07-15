package views;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import engine.Game;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;

public class boardView extends JFrame  implements ActionListener {
	
   
	
	private ImageIcon icon;
	                                   //bottom of board with action buttons
	private JButton[] actionsButtons = new JButton[14];       // 4 move , 4 attack , 3 cast ability(a) , 3*4 cast ability(a+m) , ?? castability(x,y)
	                                         // 1 leaderAbility , 1 endTurn
	private JButton[][] boardButtons = new JButton[5][5];
	private JButton[] player1Champs ;
	private JButton[] player2Champs;
	private Game game;
	private PriorityQueue champsTurnOrder;
	
	
	String champs = "Champions Turn:";
	String ability1;
	String ability2;
	String ability3;
	String effects;
	String leaderUse ="Leader Ability Used";
	String leaderNot ="Leader Ability Not Used";
	JLabel  label1 , label2;
	//JTextArea
	
	private JPanel actionPanel ,player1Info , player2Info;  //playerinfo are buttons
	private JPanel boardPanel;
	private Object[][] board;
	private JLabel player1Name , player2Name;
	private JTextArea leaderOne , leaderTwo , championOrder , currentChamp, 
	currentAbility1 , currentAbility2 , currentAbility3 , currentEffects ;
	
	
	String current="Current Champion:";
	Font playerFont = new Font("MV Boli" , Font.BOLD , 15);
	Font orderFont =  new Font("Ink Free" , Font.BOLD , 15);
	Font currentChampFont = new Font("MV Boli" , Font.BOLD , 10);
	Font start = new Font("Ink Free" , Font.ITALIC, 30);
	Font playerNameFont = new Font("Comic Sans MS\r\n"
			+ "" , Font.BOLD , 14);
	
	public boardView(Game g) throws CloneNotSupportedException {
		
		this.game =g;
		board = g.getBoard();
		g.placeChampions();
		g.prepareChampionTurns();
		this.getContentPane().setLayout(null);	
		this.setTitle("Marvel");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100 , 50 , 1200 , 625);
		icon = new ImageIcon("Avengerslogofront.png");
		this.setIconImage(icon.getImage()); 
		
		
		// TURN ORDER
		
		champsTurnOrder = (PriorityQueue) g.getTurnOrder().clone();
		while(!champsTurnOrder.isEmpty()) {
			champs = champs.concat(((Champion) champsTurnOrder.peekMin()).getName());
			champs = champs.concat(" , ");
			champsTurnOrder.remove();
		}
		
		
		championOrder = new JTextArea(champs);
		championOrder.setEditable(false);
		championOrder.setBounds(0,0,1200,10);    //BOUND
		championOrder.setFont(orderFont);
		championOrder.setForeground(Color.magenta);
		this.add(championOrder);
		championOrder.setOpaque(true);
		
		
		//CURRENT CHAMPION INFO
		
		current= current.concat(g.getCurrentChampion().toString());   
		currentChamp = new JTextArea(current);
		if(g.getFirstPlayer().getTeam().contains(g.getCurrentChampion()))
			currentChamp.setForeground(Color.red);
		if(g.getSecondPlayer().getTeam().contains(g.getCurrentChampion()))
			currentChamp.setForeground(Color.blue); 
		currentChamp.setBounds(0,10,1200,15);         
		currentChamp.setFont(currentChampFont);
		currentChamp.setEditable(false);
		this.add(currentChamp);
		
		ability1 ="First Ability: ";
		ability1 = ability1.concat(g.getCurrentChampion().getAbilities().get(0).toString());
		currentAbility1 = new JTextArea(ability1);
		currentAbility1.setEditable(false);
		if(g.getFirstPlayer().getTeam().contains(g.getCurrentChampion()))
			currentAbility1.setForeground(Color.red);
		if(g.getSecondPlayer().getTeam().contains(g.getCurrentChampion()))
			currentAbility1.setForeground(Color.blue); 
		currentAbility1.setBounds(0,25,1200,15);         
		currentAbility1.setFont(currentChampFont);
		this.add(currentAbility1);
		
		ability2="Second Ability: ";
		ability2 = ability2.concat(g.getCurrentChampion().getAbilities().get(1).toString());
		currentAbility2 = new JTextArea(ability2);
		currentAbility2.setEditable(false);
		if(g.getFirstPlayer().getTeam().contains(g.getCurrentChampion()))
			currentAbility2.setForeground(Color.red);
		if(g.getSecondPlayer().getTeam().contains(g.getCurrentChampion()))
			currentAbility2.setForeground(Color.blue); 
		currentAbility2.setBounds(0,40,1200,15);       
		currentAbility2.setFont(currentChampFont);
		this.add(currentAbility2);
		
		ability3="Third Ability: ";
		ability3 = ability3.concat(g.getCurrentChampion().getAbilities().get(2).toString());
		currentAbility3 = new JTextArea(ability3);
		currentAbility3.setEditable(false);
		if(g.getFirstPlayer().getTeam().contains(g.getCurrentChampion()))
			currentAbility3.setForeground(Color.red);
		if(g.getSecondPlayer().getTeam().contains(g.getCurrentChampion()))
			currentAbility3.setForeground(Color.blue); 
		currentAbility3.setBounds(0,55,1200,15);      
		currentAbility3.setFont(currentChampFont);
		this.add(currentAbility3);
		
		
		//APPLIED EFFECTS
		effects="Applied Effects: ";
		currentEffects = new JTextArea(effects);
		currentEffects.setEditable(false);
		if(g.getFirstPlayer().getTeam().contains(g.getCurrentChampion()))
			currentEffects.setForeground(Color.red);
		if(g.getSecondPlayer().getTeam().contains(g.getCurrentChampion()))
			currentEffects.setForeground(Color.blue); 
		currentEffects.setBounds(0,70,1200,15);      
		currentEffects.setFont(currentChampFont);
		this.add(currentEffects); 
		
		
		
		//PLAYER 1 INFO
		
		player1Name = new JLabel(g.getFirstPlayer().getName()+ " (leader: " + g.getFirstPlayer().getLeader().getName()+ ")");
		player1Name.setFont(playerNameFont);
		player1Name.setForeground(Color.red);
		player1Name.setBounds(920,85,250,20);
		this.add(player1Name); 
		
		player1Info = new JPanel();
		player1Info.setLayout(new GridLayout(g.getFirstPlayer().getTeam().size(),1));
		player1Champs = new JButton[g.getFirstPlayer().getTeam().size()];
		for(int i =0 ; i<g.getFirstPlayer().getTeam().size();i++) {
			if(g.getFirstPlayer().getTeam().get(i).equals(g.getFirstPlayer().getLeader())) {
			player1Champs[i] = new JButton(" (Leader");
			}
			player1Champs[i] = new JButton(g.getFirstPlayer().getTeam().get(i).getName());
			//if(g.getFirstPlayer().getTeam().get(i).equals(g.getFirstPlayer().getLeader()))
			player1Champs[i].setToolTipText(g.getFirstPlayer().getTeam().get(i).toString());
			player1Info.add(player1Champs[i]);
		}
		player1Info.setBounds(925,110,270,60);
		this.add(player1Info);
		
		
		leaderOne = new JTextArea(leaderNot);
		leaderOne.setEditable(false);
		leaderOne.setForeground(Color.red);
		leaderOne.setBounds(925,175,270,60);
		this.add(leaderOne);
		
		//PLAYER 2 INFO
		
		player2Name = new JLabel(g.getSecondPlayer().getName()+ "(leader: " + g.getSecondPlayer().getLeader().getName()+")");
		player2Name.setFont(playerNameFont);
		player2Name.setForeground(Color.blue);
		player2Name.setBounds(920,305,250,30);
		this.add(player2Name); 
		
		player2Info = new JPanel();
		player2Info.setLayout(new GridLayout(g.getSecondPlayer().getTeam().size(),1));
		player2Champs = new JButton[g.getSecondPlayer().getTeam().size()];
		for(int i =0 ; i<g.getSecondPlayer().getTeam().size();i++) {
			if(g.getSecondPlayer().getTeam().get(i).equals(g.getSecondPlayer().getLeader())) {
				player2Champs[i] = new JButton(g.getSecondPlayer().getTeam().get(i).getName()+" (Leader)");
			}
			player2Champs[i] = new JButton(g.getSecondPlayer().getTeam().get(i).getName());
			player2Champs[i].setToolTipText(g.getSecondPlayer().getTeam().get(i).toString());
			player2Info.add(player2Champs[i]);
		}
		player2Info.setBounds(925,330,270,60);
		this.add(player2Info);
		

		leaderTwo =  new JTextArea(leaderNot);
		leaderTwo.setEditable(false);
		leaderTwo.setForeground(Color.blue);
		leaderTwo.setBounds(925,390,270,60);
		this.add(leaderTwo);
		
		//GAME BOARD
		
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(5,5));

		for(int x=4 ; x >= 0 ; x--) {
			for(int y = 0 ; y < 5 ; y++) {
			   if(board[x][y] instanceof Champion) {
				   boardButtons[x][y] = new JButton(((Champion) board[x][y]).getName());
				   boardButtons[x][y].setToolTipText(((Champion) board[x][y]).toString()+((Champion) board[x][y]).getAppliedEffects().toString());
				   
				   if(g.getFirstPlayer().getTeam().contains(((Champion) board[x][y])))
					   boardButtons[x][y].setForeground(Color.red);
				   if(g.getSecondPlayer().getTeam().contains(((Champion) board[x][y])))
					   boardButtons[x][y].setForeground(Color.blue);
				   boardPanel.add(boardButtons[x][y]);
			   }else if(board[x][y] instanceof Cover) {
				   boardButtons[x][y] = new JButton("cover");
				   boardButtons[x][y].setBackground(Color.black);
				   boardButtons[x][y].setForeground(Color.white);
				   boardButtons[x][y].setToolTipText("HP:"+((Cover) board[x][y]).getCurrentHP());
				   boardPanel.add(boardButtons[x][y]);
			   }else {
				   boardButtons[x][y] = new JButton("");
				   boardPanel.add(boardButtons[x][y]); 
			   }
			}
		}
		
		this.add(boardPanel , BorderLayout.CENTER);
		boardPanel.setBounds(200 ,85,720 , 400);
		
		
		//ACTION BUTTONS		
		
		actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout(2,7));
		
		actionsButtons[0] = new JButton("move right");
		actionPanel.add(actionsButtons[0]);
		actionsButtons[0].addActionListener(this);
		
		actionsButtons[1] = new JButton("move up");
		actionPanel.add(actionsButtons[1]);
		actionsButtons[1].addActionListener(this);

		actionsButtons[2] = new JButton("move down");
		actionPanel.add(actionsButtons[2]);
		actionsButtons[2].addActionListener(this);

		actionsButtons[3] = new JButton("move left");
		actionPanel.add(actionsButtons[3]);
		actionsButtons[3].addActionListener(this);

		actionsButtons[4] = new JButton("attack up");
		actionPanel.add(actionsButtons[4]);
		actionsButtons[4].addActionListener(this);
		
		actionsButtons[5] = new JButton("attack down");
		actionPanel.add(actionsButtons[5]);
		actionsButtons[5].addActionListener(this);
		
		actionsButtons[6] = new JButton("attack left");
		actionPanel.add(actionsButtons[6]);
		actionsButtons[6].addActionListener(this);
		
		actionsButtons[7] = new JButton("attack right");
		actionPanel.add(actionsButtons[7]);
		actionsButtons[7].addActionListener(this);
		
		actionsButtons[8] = new JButton("CastAbility1");
		actionPanel.add(actionsButtons[8]);
		actionsButtons[8].addActionListener(this);
		
		actionsButtons[9] = new JButton("CastAbility2");
		actionPanel.add(actionsButtons[9]);
		actionsButtons[9].addActionListener(this);
		
		actionsButtons[10] = new JButton("CastAbility3");
		actionPanel.add(actionsButtons[10]);
		actionsButtons[10].addActionListener(this);
		
		actionsButtons[11] = new JButton("End Turn");
		actionPanel.add(actionsButtons[11]);
		actionsButtons[11].addActionListener(this);
		
		actionsButtons[12] = new JButton("Leader Ability");
		actionPanel.add(actionsButtons[12]);
		actionsButtons[12].addActionListener(this);
		
		actionsButtons[13] = new JButton();
		actionPanel.add(actionsButtons[13]);
		actionsButtons[13].addActionListener(this);
		
		
		
		
		this.add(actionPanel);
		actionPanel.setBounds(0,485,1200 ,100);
		

		
		
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	
	
		}

	public static void main(String[] args) {
		//new boardView();

	}
	
	
	public void updateTurnOrder() throws CloneNotSupportedException {
		PriorityQueue order = new PriorityQueue(game.getTurnOrder().size());
		champs="Champions Turn: ";
		order = (PriorityQueue) game.getTurnOrder().clone();
		while(!order.isEmpty()) {
			champs = champs.concat(((Champion) order.peekMin()).getName());
			champs = champs.concat(" , ");
			order.remove();
		}
		
		championOrder.setText(champs);
		championOrder.setEditable(false);
		championOrder.setBounds(0,0,1200,10);    //BOUND
		championOrder.setFont(orderFont);
		championOrder.setForeground(Color.magenta);
		this.add(championOrder);
		championOrder.setOpaque(true);
	}
	
	
    public void updateCurrentChamp() {
    	current="Current Champ:" + game.getCurrentChampion().toString(); 
		currentChamp.setText(current);
		currentChamp.setEditable(false);
		if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()))
			currentChamp.setForeground(Color.red);
		if(game.getSecondPlayer().getTeam().contains(game.getCurrentChampion()))
			currentChamp.setForeground(Color.blue); 
		currentChamp.setBounds(0,10,1200,15); 
		currentChamp.setFont(currentChampFont);
		this.add(currentChamp);
	}
    
    public void updateCurrentAbilities() {
		
		ability1 ="First Ability: ";
		ability1 = ability1.concat(game.getCurrentChampion().getAbilities().get(0).toString());
		currentAbility1.setText(ability1);
		currentAbility1.setEditable(false);
		if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()))
			currentAbility1.setForeground(Color.red);
		if(game.getSecondPlayer().getTeam().contains(game.getCurrentChampion()))
			currentAbility1.setForeground(Color.blue); 
		currentAbility1.setBounds(0,25,1200,15);         
		currentAbility1.setFont(currentChampFont);
		this.add(currentAbility1);
		
		ability2="Second Ability: ";
		ability2 = ability2.concat(game.getCurrentChampion().getAbilities().get(1).toString());
		currentAbility2.setText(ability2);
		currentAbility2.setEditable(false);
		if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()))
			currentAbility2.setForeground(Color.red);
		if(game.getSecondPlayer().getTeam().contains(game.getCurrentChampion()))
			currentAbility2.setForeground(Color.blue); 
		currentAbility2.setBounds(0,40,1200,15);       
		currentAbility2.setFont(currentChampFont);
		this.add(currentAbility2);
		
		ability3="Third Ability: ";
		ability3 = ability3.concat(game.getCurrentChampion().getAbilities().get(2).toString());
		currentAbility3.setText(ability3);
		currentAbility3.setEditable(false);
		if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()))
			currentAbility3.setForeground(Color.red);
		if(game.getSecondPlayer().getTeam().contains(game.getCurrentChampion()))
			currentAbility3.setForeground(Color.blue); 
		currentAbility3.setBounds(0,55,1200,15);      
		currentAbility3.setFont(currentChampFont);
		this.add(currentAbility3);
		
	}
	
    public void updateCurrentEffect() {
    	effects="Applied Effects: ";
		for(int i=0 ; i<game.getCurrentChampion().getAppliedEffects().size();i++) {
	    effects = effects.concat(game.getCurrentChampion().getAppliedEffects().get(i).toString());
		}
		currentEffects.setText(effects);
		currentEffects.setEditable(false);
		if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()))
			currentEffects.setForeground(Color.red);
		if(game.getSecondPlayer().getTeam().contains(game.getCurrentChampion()))
			currentEffects.setForeground(Color.blue); 
		currentEffects.setBounds(0,70,1200,15);      
		currentEffects.setFont(currentChampFont);
		this.add(currentEffects); 
	}
    
    
	
	public void updatePlayer1() {
		player1Info = new JPanel();
		player1Info.setLayout(new GridLayout(game.getFirstPlayer().getTeam().size(),1));
		player1Champs = new JButton[game.getFirstPlayer().getTeam().size()];
		for(int i =0 ; i<game.getFirstPlayer().getTeam().size();i++) {
			player1Champs[i] = new JButton(game.getFirstPlayer().getTeam().get(i).getName());
			//player1Champs[i].setToolTipText(game.getFirstPlayer().getTeam().get(i).toString());
			player1Info.add(player1Champs[i]);
		}
		player1Info.setBounds(925,110,270,60);
		this.add(player1Info);
		
		this.repaint();
		this.revalidate();
	}
	
	public void updatePlayer2() {
		player2Info = new JPanel();
		player2Info.setLayout(new GridLayout(game.getSecondPlayer().getTeam().size(),1));
		player2Champs = new JButton[game.getSecondPlayer().getTeam().size()];
		for(int i =0 ; i<game.getSecondPlayer().getTeam().size();i++) {
			player2Champs[i] = new JButton(game.getSecondPlayer().getTeam().get(i).getName());
			//player2Champs[i].setToolTipText(game.getSecondPlayer().getTeam().get(i).toString());
			player2Info.add(player2Champs[i]);
		}
		player2Info.setBounds(925,330,270,60);
		this.add(player2Info);
		
		this.repaint();
		this.revalidate();
	}
	
	
	
	public void updateLeader() {
		if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion())) {
			if(game.isFirstLeaderAbilityUsed()) {
			leaderOne.setText(leaderUse);
			leaderOne.setEditable(false);
			leaderOne.setForeground(Color.red);
			leaderOne.setBounds(925,175,270,60);
			this.add(leaderOne);
			}
		}else if(game.getSecondPlayer().getTeam().contains(game.getCurrentChampion())) {
			if(game.isSecondLeaderAbilityUsed()) {
			leaderTwo.setText(leaderUse);
			leaderTwo.setEditable(false);
			leaderTwo.setForeground(Color.blue);
			leaderTwo.setBounds(925,390,270,60);
			this.add(leaderTwo);
			}
		}
	}
	
	
	
	
	public void updateBoard() {
		for(int x=4 ; x >= 0 ; x--) {
			for(int y = 0 ; y < 5 ; y++) {
			   if(board[x][y] instanceof Champion) {
				   boardButtons[x][y] = new JButton(((Champion) board[x][y]).getName());
				   boardButtons[x][y].setToolTipText(((Champion) board[x][y]).toString()+((Champion) board[x][y]).getAppliedEffects().toString());
				   
				   if(game.getFirstPlayer().getTeam().contains(((Champion) board[x][y])))
					   boardButtons[x][y].setForeground(Color.red);
				   if(game.getSecondPlayer().getTeam().contains(((Champion) board[x][y])))
					   boardButtons[x][y].setForeground(Color.blue);
				   boardPanel.add(boardButtons[x][y]);
			   }else if(board[x][y] instanceof Cover) {
				   boardButtons[x][y] = new JButton("cover");
				   boardButtons[x][y].setBackground(Color.black);
				   boardButtons[x][y].setForeground(Color.white);
				   boardButtons[x][y].setToolTipText("HP:"+((Cover) board[x][y]).getCurrentHP());
				   boardPanel.add(boardButtons[x][y]);
			   }else {
				   boardButtons[x][y] = new JButton("");
				   boardPanel.add(boardButtons[x][y]); 
			   }
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {     
		if(e.getSource()==actionsButtons[0]) {      
			try {
				
				game.move(Direction.RIGHT);
				boardPanel.removeAll();
				player1Info.removeAll();
				player2Info.removeAll();
				updateCurrentChamp();
				updateCurrentAbilities();
				updateCurrentEffect();
				updateLeader();
				updatePlayer1();
				updatePlayer2();
				updateBoard();
				this.repaint();
				this.revalidate();
			} catch (UnallowedMovementException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Move Error", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			} catch (NotEnoughResourcesException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Move Error", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}
			
		}else if(e.getSource()== actionsButtons[1]) {
			try {
				game.move(Direction.UP);
				boardPanel.removeAll();
				player1Info.removeAll();
				player2Info.removeAll();
				updateCurrentChamp();
				updateCurrentAbilities();
				updateCurrentEffect();
				updateLeader();
				updatePlayer1();
				updatePlayer2();
				updateBoard();
				this.repaint();
				this.revalidate();
			} catch (UnallowedMovementException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Move Error", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			} catch (NotEnoughResourcesException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Move Error", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}
			
		}else if(e.getSource()== actionsButtons[2]) {
			
			try {
				game.move(Direction.DOWN);
				boardPanel.removeAll();
				player1Info.removeAll();
				player2Info.removeAll();
				updateCurrentChamp();
				updateCurrentAbilities();
				updateCurrentEffect();
				updateLeader();
				updatePlayer1();
				updatePlayer2();
				updateBoard();
				this.repaint();
				this.revalidate();
			} catch (UnallowedMovementException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Move Error", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			} catch (NotEnoughResourcesException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Move Error", JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}
		}else if(e.getSource()== actionsButtons[3]) {
			try {
				game.move(Direction.LEFT);
				boardPanel.removeAll();
				player1Info.removeAll();
				player2Info.removeAll();
				updateCurrentChamp();
				updateCurrentAbilities();
				updateCurrentEffect();
				updateLeader();
				updatePlayer1();
				updatePlayer2();
				updateBoard();
				this.repaint();
				this.revalidate();
			} catch (UnallowedMovementException | NotEnoughResourcesException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Move Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
			
		}else if(e.getSource()== actionsButtons[4]) {   //up , down , left , right
			try {
				game.attack(Direction.UP);
				
				if(game.checkGameOver()==game.getFirstPlayer()) {
					JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				}else if(game.checkGameOver() ==game.getSecondPlayer()) {
					JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				}
				
				
				boardPanel.removeAll();
				player1Info.removeAll();
				player2Info.removeAll();
				updateCurrentChamp();
				updateCurrentAbilities();
				updateCurrentEffect();
				updateLeader();
				updatePlayer1();
				updatePlayer2();
				updateBoard();
				this.repaint();
				this.revalidate();
				
				
				
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage() , "Attack Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Attack Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			
		}else if(e.getSource()== actionsButtons[5]) {
			try {
				game.attack(Direction.DOWN);
				if(game.checkGameOver()==game.getFirstPlayer()) {
					JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				}else if(game.checkGameOver() ==game.getSecondPlayer()) {
					JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				}
				boardPanel.removeAll();
				player1Info.removeAll();
				player2Info.removeAll();
				updateCurrentChamp();
				updateCurrentAbilities();
				updateCurrentEffect();
				updateLeader();
				updatePlayer1();
				updatePlayer2();
				updateBoard();
				this.repaint();
				this.revalidate();
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Attack Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(), "Attack Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			
		}else if(e.getSource()== actionsButtons[6]) {
			try {
				game.attack(Direction.LEFT);
				if(game.checkGameOver()==game.getFirstPlayer()) {
					JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				}else if(game.checkGameOver() ==game.getSecondPlayer()) {
					JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				}
				boardPanel.removeAll();
				player1Info.removeAll();
				player2Info.removeAll();
				updateCurrentChamp();
				updateCurrentAbilities();
				updateCurrentEffect();
				updateLeader();
				updatePlayer1();
				updatePlayer2();
				updateBoard();
				this.repaint();
				this.revalidate();
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Attack Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(), "Attack Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			
		}else if(e.getSource()== actionsButtons[7]) {
			try {
				game.attack(Direction.RIGHT);
				if(game.checkGameOver()==game.getFirstPlayer()) {
					JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				}else if(game.checkGameOver() ==game.getSecondPlayer()) {
					JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				}
				boardPanel.removeAll();
				player1Info.removeAll();
				player2Info.removeAll();
				updateCurrentChamp();
				updateCurrentAbilities();
				updateCurrentEffect();
				updateLeader();
				updatePlayer1();
				updatePlayer2();
				updateBoard();
				this.repaint();
				this.revalidate();
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Attack Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(), "Attack Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			
		}else if(e.getSource()== actionsButtons[8]) {  //ABILITY 1
			
			if(game.getCurrentChampion().getAbilities().get(0).getCastArea()==AreaOfEffect.DIRECTIONAL) {
				String[] responses = {"right" , "up" , "down" , "left"};
				
				int n =JOptionPane.showOptionDialog(null, "Choose cast ability direction: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				if(n==0) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(0),Direction.RIGHT );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(n==1) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(0),Direction.UP );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(n==2) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(0),Direction.DOWN );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(n==3) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(0),Direction.LEFT );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}else if(game.getCurrentChampion().getAbilities().get(0).getCastArea()==AreaOfEffect.SINGLETARGET) {
				
               String[] responses = {"0" , "1" , "2" , "3" , "4"};
				
				int x =JOptionPane.showOptionDialog(null, "Choose x-location: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				int y =JOptionPane.showOptionDialog(null, "Choose y-location: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				try {
					game.castAbility(game.getCurrentChampion().getAbilities().get(0), x, y);
					if(game.checkGameOver()==game.getFirstPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}else if(game.checkGameOver() ==game.getSecondPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}
					boardPanel.removeAll();
					player1Info.removeAll();
					player2Info.removeAll();
					updateCurrentChamp();
					updateCurrentAbilities();
					updateCurrentEffect();
					updateLeader();
					updatePlayer1();
					updatePlayer2();
					updateBoard();
					this.repaint();
					this.revalidate();
				} catch (AbilityUseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}else if(game.getCurrentChampion().getAbilities().get(0).getCastArea()==AreaOfEffect.TEAMTARGET
					|| game.getCurrentChampion().getAbilities().get(0).getCastArea()==AreaOfEffect.SURROUND
					|| game.getCurrentChampion().getAbilities().get(0).getCastArea()==AreaOfEffect.SELFTARGET) {
				
				
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(0));
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				
			}
			
		}else if(e.getSource()== actionsButtons[9]) {      //ABILITY 2
			
			if(game.getCurrentChampion().getAbilities().get(1).getCastArea()==AreaOfEffect.DIRECTIONAL) {
                String[] responses = {"right" , "up" , "down" , "left"};
				
				int n =JOptionPane.showOptionDialog(null, "Choose cast ability direction: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				if(n==0) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(1),Direction.RIGHT );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(n==1) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(1),Direction.UP );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						this.dispose();
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						this.dispose();
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(n==2) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(1),Direction.DOWN );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(n==3) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(1),Direction.LEFT );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}else if(game.getCurrentChampion().getAbilities().get(1).getCastArea()==AreaOfEffect.SINGLETARGET) {
				
				String[] responses = {"0" , "1" , "2" , "3" , "4"};
				
				int x =JOptionPane.showOptionDialog(null, "Choose x-location: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				int y =JOptionPane.showOptionDialog(null, "Choose y-location: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				try {
					game.castAbility(game.getCurrentChampion().getAbilities().get(1), x, y);
					if(game.checkGameOver()==game.getFirstPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}else if(game.checkGameOver() ==game.getSecondPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}
					boardPanel.removeAll();
					player1Info.removeAll();
					player2Info.removeAll();
					updateCurrentChamp();
					updateCurrentAbilities();
					updateCurrentEffect();
					updateLeader();
					updatePlayer1();
					updatePlayer2();
					updateBoard();
					this.repaint();
					this.revalidate();
				} catch (AbilityUseException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null, "Invalid Target.", "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(game.getCurrentChampion().getAbilities().get(1).getCastArea()==AreaOfEffect.TEAMTARGET
					|| game.getCurrentChampion().getAbilities().get(1).getCastArea()==AreaOfEffect.SURROUND
					|| game.getCurrentChampion().getAbilities().get(1).getCastArea()==AreaOfEffect.SELFTARGET) {
				
				try {
					game.castAbility(game.getCurrentChampion().getAbilities().get(1));
					if(game.checkGameOver()==game.getFirstPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}else if(game.checkGameOver() ==game.getSecondPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}
					boardPanel.removeAll();
					player1Info.removeAll();
					player2Info.removeAll();
					updateCurrentChamp();
					updateCurrentAbilities();
					updateCurrentEffect();
					updateLeader();
					updatePlayer1();
					updatePlayer2();
					updateBoard();
					this.repaint();
					this.revalidate();
				} catch (AbilityUseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
 
			
		}else if(e.getSource()== actionsButtons[10]) {            //ABILITY 3 
			
			if(game.getCurrentChampion().getAbilities().get(2).getCastArea()==AreaOfEffect.DIRECTIONAL) {
				
                String[] responses = {"right" , "up" , "down" , "left"};
				
				int n =JOptionPane.showOptionDialog(null, "Choose cast ability direction: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				if(n==0) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(2),Direction.RIGHT );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(n==1) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(2),Direction.UP );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(n==2) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(2),Direction.DOWN );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 
				}else if(n==3) {
					try {
						game.castAbility(game.getCurrentChampion().getAbilities().get(2),Direction.LEFT );
						if(game.checkGameOver()==game.getFirstPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}else if(game.checkGameOver() ==game.getSecondPlayer()) {
							JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
							this.dispose();
						}
						boardPanel.removeAll();
						player1Info.removeAll();
						player2Info.removeAll();
						updateCurrentChamp();
						updateCurrentAbilities();
						updateCurrentEffect();
						updateLeader();
						updatePlayer1();
						updatePlayer2();
						updateBoard();
						this.repaint();
						this.revalidate();
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}else if(game.getCurrentChampion().getAbilities().get(2).getCastArea()==AreaOfEffect.SINGLETARGET) {
				
				String[] responses = {"0" , "1" , "2" , "3" , "4"};
				
				int x =JOptionPane.showOptionDialog(null, "Choose x-location: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				int y =JOptionPane.showOptionDialog(null, "Choose y-location: ", "Cast Ability",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,1);
				
				try {
					game.castAbility(game.getCurrentChampion().getAbilities().get(2), x, y);
					if(game.checkGameOver()==game.getFirstPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}else if(game.checkGameOver() ==game.getSecondPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}
					boardPanel.removeAll();
					player1Info.removeAll();
					player2Info.removeAll();
					updateCurrentChamp();
					updateCurrentAbilities();
					updateCurrentEffect();
					updateLeader();
					updatePlayer1();
					updatePlayer2();
					updateBoard();
					this.repaint();
					this.revalidate();
				} catch (AbilityUseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(game.getCurrentChampion().getAbilities().get(2).getCastArea()==AreaOfEffect.TEAMTARGET
					|| game.getCurrentChampion().getAbilities().get(2).getCastArea()==AreaOfEffect.SURROUND
					|| game.getCurrentChampion().getAbilities().get(2).getCastArea()==AreaOfEffect.SELFTARGET) {
				try {
					game.castAbility(game.getCurrentChampion().getAbilities().get(2));
					if(game.checkGameOver()==game.getFirstPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}else if(game.checkGameOver() ==game.getSecondPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}
					boardPanel.removeAll();
					player1Info.removeAll();
					player2Info.removeAll();
					updateCurrentChamp();
					updateCurrentAbilities();
					updateCurrentEffect();
					updateLeader();
					updatePlayer1();
					updatePlayer2();
					updateBoard();
					this.repaint();
					this.revalidate();
				} catch (AbilityUseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Cast Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
 
			
		}else if(e.getSource()== actionsButtons[11]) {  //END TURN
			game.endTurn();
			
			updateCurrentChamp();
			updateCurrentAbilities();
			updateCurrentEffect();
			updateLeader();
			updatePlayer1();
			updatePlayer2();
			
			try {
				updateTurnOrder();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}else if(e.getSource()== actionsButtons[12]) {   //LEADER ABILITY
			
				try {
					game.useLeaderAbility();
					if(game.checkGameOver()==game.getFirstPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getFirstPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}else if(game.checkGameOver() ==game.getSecondPlayer()) {
						JOptionPane.showMessageDialog(null,"Congrats " +game.getSecondPlayer().getName() + ", you won!!", "Winner", JOptionPane.PLAIN_MESSAGE);
						this.dispose();
					}
					boardPanel.removeAll();
					player1Info.removeAll();
					player2Info.removeAll();
					updateCurrentChamp();
					updateCurrentAbilities();
					updateCurrentEffect();
					updateLeader();
					updatePlayer1();
					updatePlayer2();
					updateBoard();
					this.repaint();
					this.revalidate();
					
					this.repaint();
					this.revalidate();
				} catch (LeaderAbilityAlreadyUsedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Leader Ability Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (LeaderNotCurrentException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(), "Leader Ability Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
			
			
			
			
			
		}
		
	}



}
