package fr.core.service;

import fr.core.IMapper;
import fr.core.model.customModel.Information;
import fr.core.model.customModel.MessageInterceptor;
import fr.core.model.customModel.Session;
import fr.core.service.inter.IRestConnector;
import fr.core.ui.DialogModal;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RestConnector implements IRestConnector {
    IMapper mapper;
    Session session = new Session();
    public static String baseUrl = "http://localhost:3000/";

    public void setMapper(IMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T get(String url, Class<T> type) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(baseUrl + url);
        request.setHeader("authorization", "Bearer " + this.session.user.getToken());
        request.setHeader("content-type", "application/json");
        return executeRequest(client, request, type);
    }

    public <T, V> T post(String url, V data, Class<T> type) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(baseUrl + url);
        System.out.println(baseUrl + url);
        String d = mapper.toJsonString(data);
        StringEntity httpEntity = new StringEntity(d, ContentType.APPLICATION_FORM_URLENCODED);
        post.setEntity(httpEntity);
        post.setHeader("content-type", "application/json");
        if (!url.contains("auth/login")) {
            post.setHeader("authorization", "Bearer " + this.session.user.getToken());
        }
        return executeRequest(client, post, type);
    }

    public <T, V> T put(String url, V data, Class<T> type) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(baseUrl + url);
        String d = mapper.toJsonString(data);
        StringEntity httpEntity = new StringEntity(d, ContentType.APPLICATION_FORM_URLENCODED);
        put.setEntity(httpEntity);
        put.setHeader("content-type", "application/json");
        if (!url.contains("auth/login")) {
            put.setHeader("authorization", "Bearer " + this.session.user.getToken());
        }
        return executeRequest(client, put, type);
    }

    public <T> T delete(String url, Class<T> type) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete request = new HttpDelete(baseUrl + url);
        request.setHeader("authorization", "Bearer " + this.session.user.getToken());
        request.setHeader("content-type", "application/json");
        return executeRequest(client, request, type);
    }

    public <T> T executeRequest(HttpClient client, HttpUriRequest methode, Class<T> type) throws Exception {
        HttpResponse response = client.execute(methode);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        if (response.getStatusLine().getStatusCode() > 210) {
            Information information = (Information) mapper.getObject(result.toString(), Information.class);
            return SendError(information);
        }
        return mapper.getObject(result.toString(), type);
    }

    private <T> T SendError(Information object) {
        MessageInterceptor.mesage = object.message;
        DialogModal.intercet("test", MessageInterceptor.mesage);
        return (T) object;
    }
}
