package com.demo.services.json;

import com.demo.entities.Boy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public interface JsonService {
    public JSONArray toJson (List<Boy> boyList) throws IOException;
//    public void objectToJson(String pathname, Boy boy) throws IOException;
    public Boy parseObject(JSONObject jsonObject);
    public List<Boy> parseFile(String pathname);
//    public Json readFile(String pathname);
}
