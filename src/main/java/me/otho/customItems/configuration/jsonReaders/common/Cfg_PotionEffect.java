package me.otho.customItems.configuration.jsonReaders.common;

import me.otho.customItems.utility.LogHelper;
import me.otho.customItems.utility.Util;
import net.minecraft.item.Food;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class Cfg_PotionEffect {
	private String effect = "speed";
	private int potionDuration = 30;
	private int potionAmplifier = 0;
	public float potionEffectProbability = 1;

	public EffectInstance getEffect() {
		Effect effect = Util.get(Effect.class, this.effect);
		if (effect == null) {
			LogHelper.error("Invalid potion effect name: " + this.effect);
			return null;
		}

		return new EffectInstance(effect, potionDuration, potionAmplifier);
	}
	
	public boolean addEffectToFood(Food.Builder foodDefBuilder) {
		EffectInstance effectDef = getEffect();
		if (effectDef != null)
			foodDefBuilder.effect(() -> effectDef, this.potionEffectProbability);

		return effectDef != null;
	}
}
