package me.otho.customItems.block;

import javax.annotation.Nullable;

import me.otho.customItems.common.ITexture;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public interface ITextureBlock extends ITexture {
	String[] getTextureNamesRaw();
	
	default int getTextureCount() {
		int count = 0;
		for(String textureName: getTextureNamesRaw()) {
			if (textureName != null)
				count++;
		}
		return count;
	}
	
	default String getTextureName(@Nullable Direction side) {
		int count = getTextureCount();
		if (count == 1) {
			for(String textureName: getTextureNamesRaw()) {
				if (textureName != null)
					return textureName;
			}
		} else if (side != null){
			return getTextureNamesRaw()[side.ordinal()];
		}
		
		return null;
	}
	
	default ResourceLocation getTextureResLoc(@Nullable Direction side) {
		String defaultDomain = ((Block) this).getRegistryName().getNamespace();
		String textureStr = getTextureName(side);
		String path = "block/"+textureStr;
		return textureStr.contains(":") ? 
				new ResourceLocation(path) : 
				new ResourceLocation(defaultDomain, path);
	}
	
	@Override
	default ResourceLocation getTextureResLoc() {
		return getTextureResLoc(null);
	}
}
