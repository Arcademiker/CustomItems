package me.otho.customItems;

import java.io.IOException;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import me.otho.customItems.configuration.JsonConfigurationHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

public class CommandHandler {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("customitems")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> showHelp(context.getSource()));
	
		literalargumentbuilder.then(
			Commands.literal("generate").executes(context->showHelp(context.getSource()))
			.then(
				Commands.literal("respack").executes(context->runDataGenerator(context.getSource(), true, false))
			)
			.then(
				Commands.literal("datapack").executes(context->runDataGenerator(context.getSource(), false, true))
			)
			.then(
				Commands.literal("both").executes(context->runDataGenerator(context.getSource(), true, true))
			)
		);

		dispatcher.register(literalargumentbuilder);
	}
	
	private static int showHelp(CommandSource sender) {
		sender.sendFeedback(new StringTextComponent(
				"Syntax: customitems generate [respack, datapack, both]"
				), true);
		return 1;
	}
	
	private static int runDataGenerator(CommandSource sender, boolean resPack, boolean dataPack) {
		sender.sendFeedback(new StringTextComponent("Start data generators..."), true);
		
		try {
			JsonConfigurationHandler.updateExisting();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (resPack) {
			sender.sendFeedback(new StringTextComponent("Please press F3+T to reload resource packs."), false);
			Util.getServerExecutor().execute(CustomItems.proxy::runClientDataGenerators);
		}
		if (dataPack) {
			sender.sendFeedback(new StringTextComponent("Please restart the game (or server) to reload data packs."), false);
			Util.getServerExecutor().execute(CustomDataGenerator::runCommon);
		}

		return 1;
	}
}
