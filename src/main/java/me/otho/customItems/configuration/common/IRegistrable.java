package me.otho.customItems.configuration.common;

public interface IRegistrable {
	String getFriendlyName();

	/**
	 * Do not call this!
	 * @return the stored/parsed registry name, can be null
	 */
	String getRegistryNameRaw();

	void setRegistryName(String newRegName);

	  /**
	   * Backward compatibility <p>
	   * If registryName is not set, then generate one from getFriendlyName()
	   */
	default boolean checkRegistryName() {
		String friendlyName = getFriendlyName();
		boolean changed = false;

		if (getRegistryNameRaw() == null) {
			setRegistryName(genRegistryName(friendlyName));
			changed = true;
		}

		if (!getRegistryNameRaw().matches("[a-z._0-9]*"))
			throw new RuntimeException("RegistryName \"" + getRegistryNameRaw() + "\"(" + friendlyName + ") is invalid.");

		return changed;
	}
	
	default String getRegistryName() {
		checkRegistryName();
		return getRegistryNameRaw();
	}
	
	default String getDefaultTextureName() {
		return this.getRegistryName();
	}
	
	default boolean matches(IRegistrable in) {
		return this.getRegistryName().equals(in.getRegistryName());
	}
	
	public static String genRegistryName(String nameIn) {
		return nameIn.toLowerCase().replaceAll("\\s+", "_");
	}
}
