package me.otho.customItems.configuration.common;

public interface IReloadable<T> {
	void onJsonReload(T newVal);
}
