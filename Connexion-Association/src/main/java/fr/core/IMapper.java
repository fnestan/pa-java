package fr.core;

import java.io.IOException;

public interface IMapper {
    public <T> T getObject(String data, Class<T> type) throws IOException;

    public String toJsonString(Object type) throws IOException;
}
