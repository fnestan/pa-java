package fr.core.service.inter;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

public interface IRestConnector {

    public <T> T get(String url, Class<T> type) throws Exception;

    public <T, V> T post(String url, V data, Class<T> type) throws Exception;

    public <T, V> T put(String url, V data, Class<T> type) throws Exception;

    public <T> T delete(String url, Class<T> type) throws Exception;

    public <T> T executeRequest(HttpClient client, HttpUriRequest methode, Class<T> type) throws Exception;


    }
