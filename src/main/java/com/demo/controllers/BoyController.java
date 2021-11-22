package com.demo.controllers;

import com.demo.controllers.DTO.AddBoyRequest;
import com.demo.entities.Boy;
import com.demo.entities.BoyPage;
import com.demo.entities.BoySearchCriteria;
import com.demo.repositories.BoyRepository;
import com.demo.services.boy.BoyService;
import com.demo.services.excel.ExcelService;
import com.demo.services.json.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
        List<Boy> boyList = boyService.makeData(numOfData);
        boyRepository.saveAll(boyList);
        return new ResponseEntity("OK", HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/boys")
    public ResponseEntity getAllBoys() throws Exception {
        return new ResponseEntity<>(boyService.listAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/boys")
    public ResponseEntity addBoy(@RequestBody AddBoyRequest addBoyRequest) throws Exception {
        Boy newBoy = new Boy(addBoyRequest);
        boyService.save(newBoy);
        return new ResponseEntity<>(newBoy, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/boys/{id}")
    public ResponseEntity editBoy(@PathVariable long id, @RequestBody AddBoyRequest addBoyRequest) throws Exception {
        Boy boy = boyService.edit(id, addBoyRequest);
        return new ResponseEntity<>(boy, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/boys/delete/{id}")
    @ResponseBody
    public ResponseEntity deleteBoy(@PathVariable long id) throws Exception {
        boyService.delete(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/boys/export")
    public ResponseEntity export(HttpServletResponse response) throws Exception {
        List<Boy> boyList = boyService.listAll();
        excelService.export(response, boyList);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/boys/byOptions")
    public ResponseEntity<Page<Boy>> getEmployees(BoyPage boyPage,
                                                  BoySearchCriteria boySearchCriteria){
        return new ResponseEntity<>(boyService.getBoysByOptions(boyPage, boySearchCriteria),
                HttpStatus.OK);
    }



}
