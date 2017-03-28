package com.nexon.vertx.eventbus.verticles;

import com.nexon.vertx.eventbus.model.EventModel;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

import static com.nexon.vertx.eventbus.model.Configuration.CREATE;

/**
 * Created by chan8 on 2017-03-28.
 */
public class SimpleVerticle extends AbstractVerticle {
    private String name;

    public SimpleVerticle(String name) {
        this.name = name;
    }

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer(CREATE, message -> {
            EventModel eventModel = (EventModel) message.body();
            eventModel.setInstanceName(name);
            message.reply(eventModel);
        });
    }
}
