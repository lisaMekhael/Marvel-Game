package model.abilities;

import java.util.ArrayList;

import model.effects.Effect;
import model.world.Champion;
import model.world.Damageable;

public class CrowdControlAbility extends Ability {

	private Effect effect;

	public CrowdControlAbility(String name, int manaCost, int baseCooldown, int castRange, AreaOfEffect castArea,
			int actionsRequired, Effect effect) {
		super(name, manaCost, baseCooldown, castRange, castArea, actionsRequired);
		this.effect = effect;
	}

	public Effect getEffect() {
		return effect;
	}
	
	public String toString() {
		return super.toString() + "ability type: " + "crowd control ability" + "effect name: " + getEffect().getName() 
				+ ",effect duration:" +getEffect().getDuration();
	}

	@Override
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException {
		for (Damageable d : targets) {
			Champion c = (Champion) d;
			Effect e = (Effect) effect.clone();
			e.apply(c);
			c.getAppliedEffects().add(e);
		}
	}

}
