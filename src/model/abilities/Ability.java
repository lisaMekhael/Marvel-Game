package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public abstract class Ability {

	private String name;
	private int manaCost;
	private int requiredActionPoints;
	private int castRange;
	private int baseCooldown;
	private int currentCooldown;
	private AreaOfEffect castArea;
	

	public Ability(String name, int manaCost, int baseCooldown, int castRange, AreaOfEffect castArea,
			int actionsRequired) {
		this.name = name;
		this.manaCost = manaCost;
		this.requiredActionPoints = actionsRequired;
		this.castRange = castRange;
		this.baseCooldown = baseCooldown;
		this.castArea = castArea;
	}

	public int getCurrentCooldown() {
		return currentCooldown;
	}

	public void setCurrentCooldown(int currentCooldown) {
		this.currentCooldown = currentCooldown;
		if (currentCooldown > baseCooldown)
			this.currentCooldown = baseCooldown;
	}

	public String getName() {
		return name;
	}

	public int getManaCost() {
		return manaCost;
	}

	public int getRequiredActionPoints() {
		return requiredActionPoints;
	}

	public int getCastRange() {
		return castRange;
	}

	public int getBaseCooldown() {
		return baseCooldown;
	}

	public AreaOfEffect getCastArea() {
		return castArea;
	}
	
	public String toString() {
		return ". Ability Name: " + getName() +", mana: "+ getManaCost() +" , area of effect:  " + getCastArea() + " , cast range: "+getCastRange()+", action points:" 
			+getRequiredActionPoints() + " current cooldown: " + getCurrentCooldown() +" , base cooldown:" + getBaseCooldown()
			;
	}
	
	public abstract void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException;

}
