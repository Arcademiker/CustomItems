package me.otho.customItems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourcePaths {
	public final static String generated = CustomItems.MOD_ID + "_generated";
	public final static Path respack_src = Paths.get("resourcepacks" + File.separator + CustomItems.MOD_ID + "_resources");
	public final static Path respack_generated = Paths.get("resourcepacks" + File.separator + generated);
	public final static Path datapacks = Paths.get("datapacks");
	public final static Path datapack_generated = Paths.get("datapacks" + File.separator + generated);
	
	public static void pack_mcmeta(Path path, String description) {
		String format = "5";
		File metaFile = Paths.get(path.toString(), "pack.mcmeta").toFile();
		if (!metaFile.exists()) {
			try {
				FileWriter fileWriter = new FileWriter(metaFile);
				PrintWriter writer = new PrintWriter(fileWriter);
				writer.println("{");
				writer.println("\t\"pack\": {");
				writer.println("\t\t\"description\": \"" + description + "\",");
				writer.println("\t\t\"pack_format\": " + format);
				writer.println("\t}");
				writer.println("}");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
