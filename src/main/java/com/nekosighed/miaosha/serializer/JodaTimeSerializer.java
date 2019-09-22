package com.nekosighed.miaosha.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * 自定义序列化器
 * 此处指定要序列化 joda time 的 DateTime
 */
public class JodaTimeSerializer  extends JsonSerializer<DateTime> {
    /**
     * 序列化的方式
     * @param dateTime
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // 将 DateTime 序列化为 String
        jsonGenerator.writeString(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
    }
}
