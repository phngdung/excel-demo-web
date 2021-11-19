package com.demo.controllers;

import com.demo.controllers.DTO.AddBoyRequest;
import com.demo.entities.Boy;
import com.demo.repositories.BoyRepository;
import com.demo.services.boy.BoyService;
import com.demo.services.excel.ExcelService;
import com.demo.services.json.JsonService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
public class BoyController {

    @Autowired
    BoyService boyService;
    @Autowired
    JsonService jsonService;

    @Autowired
    ExcelService excelService;

    @Autowired
    BoyRepository boyRepository;


    @GetMapping("/boys/makeData/{numOfData}")
    public ResponseEntity makeData(@PathVariable Integer numOfData) throws Exception {
        List<Boy> boyList= boyService.makeData(numOfData);
        boyRepository.saveAll(boyList);
        return new ResponseEntity("OK", HttpStatus.OK);

    }

    @CrossOrigin(origins= "http://localhost:8080")
    @GetMapping("/boys")
    public ResponseEntity getAllBoys() throws Exception {
        return new ResponseEntity<>(boyRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/boys")
    public ResponseEntity addBoy(@RequestBody AddBoyRequest addBoyRequest) throws Exception {
        Boy newBoy= new Boy(addBoyRequest);
        boyRepository.save(newBoy);
        return new ResponseEntity<>(newBoy, HttpStatus.OK);
    }


    @PutMapping("/boys/{id}")
    public ResponseEntity editBoy(@PathVariable long id, @RequestBody AddBoyRequest addBoyRequest) throws  Exception{
        Boy boy= boyRepository.findById(id).get();
        System.out.print(boy.getId());

        boy.setAge(addBoyRequest.getAge());
        boyRepository.save(boy);
        return new ResponseEntity<>(boy, HttpStatus.OK);
    }

    @GetMapping("/boys/toExcel")
    public ResponseEntity exportToJson() throws  Exception{
        Iterable<Boy> boyIterable= boyRepository.findAll();
        List<Boy> boyList = IterableUtils.toList(boyIterable);

        excelService.writeData("boy.xlsx", "boy.json", boyList);
        return new ResponseEntity<>(boyList, HttpStatus.OK);
    }
}