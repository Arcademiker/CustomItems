package me.otho.customItems.block;

import java.util.HashMap;
import java.util.Map;

import me.otho.customItems.common.LogHelper;
import net.minecraft.block.SoundType;

public class SoundTypeLookUp {
	private final static Map<String, SoundType> soundTypes = new HashMap<>();
	public final static SoundType defaultVal = SoundType.STONE;
	static {
		soundTypes.put("wood", SoundType.WOOD);
		soundTypes.put("gravel", SoundType.GROUND);
		soundTypes.put("grass", SoundType.PLANT);
		soundTypes.put("stone", SoundType.STONE);
		soundTypes.put("metal", SoundType.METAL);
		soundTypes.put("glass", SoundType.GLASS);
		soundTypes.put("cloth", SoundType.CLOTH);
		soundTypes.put("sand", SoundType.SAND);
		soundTypes.put("snow", SoundType.SNOW);
		soundTypes.put("ladder", SoundType.LADDER);
		soundTypes.put("anvil", SoundType.ANVIL);
		soundTypes.put("slime", SoundType.SLIME);
		soundTypes.put("honey", SoundType.field_226947_m_);
		soundTypes.put("grass_wet", SoundType.WET_GRASS);
		soundTypes.put("coral", SoundType.CORAL);
		soundTypes.put("bamboo", SoundType.BAMBOO);
		soundTypes.put("bamboo_sapling", SoundType.BAMBOO_SAPLING);
		soundTypes.put("scaffolding", SoundType.SCAFFOLDING);
		soundTypes.put("sweet_berry_bush", SoundType.SWEET_BERRY_BUSH);
		soundTypes.put("crop", SoundType.CROP);
		soundTypes.put("stem", SoundType.STEM);
		soundTypes.put("nether_wart", SoundType.NETHER_WART);
		soundTypes.put("lantern", SoundType.LANTERN);
		
//		soundTypes.put("piston", SoundType.METAL);
//		soundTypes.put("dragonegg", SoundType.METAL);
	}


	public static SoundType get(String soundName) {
		String key = soundName.toLowerCase();
		if (soundTypes.containsKey(key)) {
			return soundTypes.get(key);
		} else {
			LogHelper.warn("Unable to find block sound type: " + soundName + "(" + key + ")");
			return defaultVal;
		}
	}
}
