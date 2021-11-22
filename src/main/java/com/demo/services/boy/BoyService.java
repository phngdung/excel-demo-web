package com.demo.services.boy;

import com.demo.controllers.DTO.AddBoyRequest;
import com.demo.entities.Boy;

import java.util.List;

public interface BoyService {
    public List<Boy> makeData(int numOfData);
    public void save(Boy boy);
    public List<Boy> listAll();
    public Boy edit(long id, AddBoyRequest addBoyRequest);
}
