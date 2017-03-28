package com.nexon.vertx.eventbus;

import com.nexon.vertx.eventbus.verticles.SimpleVerticle;
import com.nexon.vertx.eventbus.verticles.Splitter;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

/**
 * Created by chan8 on 2017-03-28.
 */
public class App {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleVerticle("A"), new DeploymentOptions().setInstances(1));
        vertx.deployVerticle(new SimpleVerticle("B"), new DeploymentOptions().setInstances(1));
        vertx.deployVerticle(Splitter.class.getName(), new DeploymentOptions().setInstances(1));
    }
}
