package com.demo.controllers;

import com.demo.controllers.DTO.AddBoyRequest;
import com.demo.entities.Boy;
import com.demo.repositories.BoyRepository;
import com.demo.services.boy.BoyService;
import com.demo.services.excel.ExcelService;
import com.demo.services.json.JsonService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.compress.utils.Lists;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class BoyController {
    Logger logger = Logger.getLogger(this.getClass().getName());

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
        return new ResponseEntity<>(boyService.listAll() , HttpStatus.OK);
    }

    @PostMapping("/boys")
    public ResponseEntity addBoy(@RequestBody AddBoyRequest addBoyRequest) throws Exception {
        Boy newBoy= new Boy(addBoyRequest);
        boyService.save(newBoy);
        return new ResponseEntity<>(newBoy, HttpStatus.OK);
    }

    @CrossOrigin(origins= "http://localhost:8080")
    @PutMapping("/boys/{id}")
    public ResponseEntity editBoy(@PathVariable long id, @RequestBody AddBoyRequest addBoyRequest) throws  Exception{
        Boy boy= boyService.edit(id, addBoyRequest);
        return new ResponseEntity<>(boy, HttpStatus.OK);
    }

    @GetMapping("/boys/export")
    public ResponseEntity export(HttpServletResponse response) throws  Exception{
        final JFileChooser fc = new JFileChooser();
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        JSONArray jsonArray = jsonService.toJson(boyService.listAll());

        FileOutputStream file =  excelService.writeFromJson("boys.xlsx","JavaBooks.xlsx",jsonArray );

        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.close();

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }




}
