package fr.core;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Mapper implements IMapper {
    ObjectMapper mapper = new ObjectMapper();


    public <T> T getObject(String data, Class<T> type) throws IOException {
        mapper.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(data, type);
    }

    public String toJsonString(Object type) throws IOException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(type);
    }
}
