package me.otho.customItems.configuration;

import org.apache.commons.lang3.ArrayUtils;

import me.otho.customItems.configuration.block.JsBlock;
import me.otho.customItems.configuration.common.JsItemGroup;
import me.otho.customItems.configuration.item.JsFood;
import me.otho.customItems.configuration.item.JsItem;
import me.otho.customItems.configuration.item.armor.JsBoot;
import me.otho.customItems.configuration.item.armor.JsChestplate;
import me.otho.customItems.configuration.item.armor.JsHelmet;
import me.otho.customItems.configuration.item.armor.JsLeggings;
import me.otho.customItems.configuration.item.tool.JsAxe;
import me.otho.customItems.configuration.item.tool.JsHoe;
import me.otho.customItems.configuration.item.tool.JsPickaxe;
import me.otho.customItems.configuration.item.tool.JsShovel;
import me.otho.customItems.configuration.item.tool.JsSword;

public class JsonSchema {
	public JsItemGroup[] creativeTabs;
	public JsBlock[] blocks;
	public JsItem[] items;
	public JsFood[] foods;

	public JsHelmet[] helmets;
	public JsChestplate[] chestplates;
	public JsLeggings[] leggings;
	public JsBoot[] boots;

	public JsAxe[] axes;
	public JsHoe[] hoes;
	public JsPickaxe[] pickaxes;
	public JsShovel[] shovels;
	public JsSword[] swords;

	JsonSchema() {
	};

	public void mergeTo(JsonSchema mergeTo) {
		mergeTo.creativeTabs = ArrayUtils.addAll(this.creativeTabs, mergeTo.creativeTabs);
		mergeTo.blocks = ArrayUtils.addAll(this.blocks, mergeTo.blocks);
		mergeTo.items = ArrayUtils.addAll(this.items, mergeTo.items);
		mergeTo.foods = ArrayUtils.addAll(this.foods, mergeTo.foods);

		mergeTo.helmets = ArrayUtils.addAll(this.helmets, mergeTo.helmets);
		mergeTo.chestplates = ArrayUtils.addAll(this.chestplates, mergeTo.chestplates);
		mergeTo.leggings = ArrayUtils.addAll(this.leggings, mergeTo.leggings);
		mergeTo.boots = ArrayUtils.addAll(this.boots, mergeTo.boots);

		mergeTo.axes = ArrayUtils.addAll(this.axes, mergeTo.axes);
		mergeTo.hoes = ArrayUtils.addAll(this.hoes, mergeTo.hoes);
		mergeTo.pickaxes = ArrayUtils.addAll(this.pickaxes, mergeTo.pickaxes);
		mergeTo.shovels = ArrayUtils.addAll(this.shovels, mergeTo.shovels);
		mergeTo.swords = ArrayUtils.addAll(this.swords, mergeTo.swords);
	}
}
