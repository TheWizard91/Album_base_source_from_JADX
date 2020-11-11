package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormatVisitor;

public interface JsonIntegerFormatVisitor extends JsonValueFormatVisitor {
    void numberType(JsonParser.NumberType numberType);

    public static class Base extends JsonValueFormatVisitor.Base implements JsonIntegerFormatVisitor {
        public void numberType(JsonParser.NumberType numberType) {
        }
    }
}
