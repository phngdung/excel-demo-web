package com.demo.services.excel;

import com.demo.entities.Boy;
import org.json.simple.JSONArray;

import javax.json.JsonString;
import java.io.FileOutputStream;
import java.util.List;

public interface ExcelService {
    public void writeData(String pathname,String templateFile, List<Boy> listBoy) throws Exception;
    public FileOutputStream writeFromJson(String pathname, String templateFile, JSONArray json) throws Exception;
}

