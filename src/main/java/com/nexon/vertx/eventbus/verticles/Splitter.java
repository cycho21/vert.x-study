package com.nexon.vertx.eventbus.verticles;

import com.nexon.vertx.eventbus.model.EventModel;
import com.nexon.vertx.eventbus.model.EventModelCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.Map;

import static com.nexon.vertx.eventbus.model.Configuration.CREATE;

/**
 * Created by chan8 on 2017-03-28.
 */
public class Splitter extends AbstractVerticle {
    private static final int PORT = 8081;

    @Override
    public void start() throws Exception {
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);

        EventBus eventBus = vertx.eventBus();
        EventModelCodec codec = new EventModelCodec();

        eventBus.registerDefaultCodec(EventModel.class, codec);

        router.get("/:id/create").handler(routingContext -> {
            HttpServerResponse httpServerResponse = routingContext.response();
            String id = routingContext.request().getParam("id");

            EventModel eventModel = new EventModel();
            eventModel.setId(id);

            eventBus.send(CREATE, eventModel, message -> {
                if (message.succeeded()) {
                    EventModel retModel = (EventModel) message.result().body();
                    JsonObject retObj = new JsonObject();
                    retObj.put("id", retModel.getId());
                    retObj.put("instanceName", retModel.getInstanceName());

                    if (!httpServerResponse.closed()) {
                        httpServerResponse.setChunked(true);
                        httpServerResponse.write(retObj.toString() + "\n");
                        httpServerResponse.end("Response end. Verticle : " + retModel.getInstanceName());
                        httpServerResponse.close();
                    }
                }
            });
        });


        httpServer.requestHandler(router::accept).listen(PORT);
    }
}