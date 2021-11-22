package com.demo.services.excel;

import com.demo.entities.Boy;
import org.json.simple.JSONArray;

import javax.json.JsonString;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public interface ExcelService {
    public void writeFromObject(String pathname, String templateFile, List<Boy> listBoy) throws Exception;

    public ServletOutputStream writeFromJson(ServletOutputStream outputStream, String templateFile, JSONArray json) throws Exception;
    public void export ( HttpServletResponse response, List<Boy> boyList) throws Exception;
}

