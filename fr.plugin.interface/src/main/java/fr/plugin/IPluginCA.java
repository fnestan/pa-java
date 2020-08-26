package fr.plugin;

public interface IPluginCA {
    public <T> T pluginProcessing(T element);

    public String getPluginType();
}

