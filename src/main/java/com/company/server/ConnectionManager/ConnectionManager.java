package com.company.server.ConnectionManager;

import com.company.collection_manage.CollectionManagement;
import com.company.server.Replier.Replier;
import com.company.server.RequestBuilder.RequestBuilder;
import com.company.server.RequestReader.RequestReader;

public class ConnectionManager {
    private final int port;
    private RequestBuilder requestBuilder;
    private Replier replier;
    private RequestReader requestReader;

    public ConnectionManager(int port) {
        this.port = port;
        requestBuilder = new RequestBuilder();
        //replier = new Replier();
        requestReader = new RequestReader(new CollectionManagement(), "f"); //TODO: change args
    }

    public RequestBuilder getRequestBuilder() { return requestBuilder; }

    public Replier getReplier() { return replier; }
}
