package com.demo.service.excel;

import com.demo.entities.Boy;

import java.util.List;

public interface ExcelService {
    public void writeData(String pathname,String templateFile, List<Boy> listBoy) throws Exception;
}

