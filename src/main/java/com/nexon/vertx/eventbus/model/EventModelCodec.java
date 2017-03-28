package com.nexon.vertx.eventbus.model;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

/**
 * Created by chan8 on 2017-03-28.
 */
public class EventModelCodec implements MessageCodec<EventModel, EventModel> {
    @Override
    public void encodeToWire(Buffer buffer, EventModel eventModel) {
        JsonObject jsonObject = new JsonObject();

        if (eventModel.getId() != null)
            jsonObject.put("id", eventModel.getId());
        if (eventModel.getInstanceName() != null)
            jsonObject.put("instanceName", eventModel.getInstanceName());

        String stringFromJson = jsonObject.encode();

        int length = stringFromJson.getBytes().length;

        buffer.appendInt(length);
        buffer.appendString(stringFromJson);
    }

    @Override
    public EventModel decodeFromWire(int pos, Buffer buffer) {
        int position = pos;
        int length = buffer.getInt(position);
        String jsonString = buffer.getString(position += 4, position = length);
        JsonObject content = new JsonObject(jsonString);

        String id = content.getString("id");
        String instanceName = content.getString("instanceName");

        return new EventModel(id, instanceName);
    }

    @Override
    public EventModel transform(EventModel eventModel) {
        return eventModel;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
