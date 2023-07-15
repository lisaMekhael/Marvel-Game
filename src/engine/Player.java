package engine;

import java.util.ArrayList;


import model.world.Champion;
import model.world.championListener;

public class Player{
	
	private String name;
	private ArrayList<Champion> team;
	private Champion leader;
	

	
	// have to update constructor
	
	public Player(String n) {
		this.name = n;
		this.team = new ArrayList<Champion>();
		
	}
	
	
	public Champion getLeader() {
		return leader;
	}

	public void setLeader(Champion leader) {
		this.leader = leader;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Champion> getTeam() {
		return team;
	}

	

}
