package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public class HealingAbility extends Ability {

	private int healAmount;

	public HealingAbility(String name, int manaCost, int baseCooldown, int castRange, AreaOfEffect castArea,
			int actionsRequired, int healAmount) {
		super(name, manaCost, baseCooldown, castRange, castArea, actionsRequired);
		this.healAmount = healAmount;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}
	
	public String toString() {
		return super.toString() + "ability type: Healing Ability ," + "heal amount: " + getHealAmount();
	}

	@Override
	public void execute(ArrayList<Damageable> targets) {
		for (Damageable d : targets) {
			d.setCurrentHP(d.getCurrentHP() + this.healAmount);
		}
	}

}
