package fr.core.model.customModel;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class PluginData {
    public static PluginModelData pluginModelData;
    ObjectMapper mapper = new ObjectMapper();

    public String sendData() throws IOException {
       return mapper.writeValueAsString(PluginData.pluginModelData);
    }
}
