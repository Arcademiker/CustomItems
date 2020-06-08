package me.otho.customItems.configuration.common;

import javax.annotation.Nullable;

import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public interface ITextureProvider {
	boolean generateModel();
	boolean hasSidedTexture();
	ResourceLocation getTextureResLoc(@Nullable Direction side);
}
