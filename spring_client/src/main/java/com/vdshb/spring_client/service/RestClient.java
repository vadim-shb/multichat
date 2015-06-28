package com.vdshb.spring_client.service;

import com.vdshb.spring_client.configuration.ConnectionSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {

    private String server;
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    @Autowired
    public RestClient(ConnectionSettings connectionSettings) {
        server = "http://" + connectionSettings.getRestConnectionUrl();
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public String get(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public String post(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public void put(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.PUT, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
    }

    public void delete(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.DELETE, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
