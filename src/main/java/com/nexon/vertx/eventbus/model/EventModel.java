package com.nexon.vertx.eventbus.model;

/**
 * Created by chan8 on 2017-03-28.
 */
public class EventModel {
    private String id;
    private String instanceName;

    public EventModel() {
    }

    public EventModel(String id, String instanceName) {
        this.id = id;
        this.instanceName = instanceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
