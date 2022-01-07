package com.demo.controllers;

import ch.qos.logback.core.net.server.Client;
import com.demo.controllers.DTO.AddBoyRequest;
import com.demo.entities.Boy;
import com.demo.entities.BoyPage;
import com.demo.entities.BoySearchCriteria;
import com.demo.exception.CustomException;
import com.demo.repositories.BoyRepository;
import com.demo.services.boy.BoyService;
import com.demo.services.excel.ExcelService;
import com.demo.services.json.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
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

    
    @GetMapping("/boys")
    public ResponseEntity getAllBoys() throws Exception {
        return new ResponseEntity<>(boyService.listAll(), HttpStatus.OK);
    }

    
    @PostMapping("/boys")
    public ResponseEntity addBoy(@RequestBody AddBoyRequest addBoyRequest) throws Exception {
        Boy newBoy = new Boy(addBoyRequest);
        boyService.save(newBoy);
        return new ResponseEntity<>(newBoy, HttpStatus.OK);
    }

    
    @PutMapping("/boys/{id}")
    public ResponseEntity editBoy(@PathVariable long id, @RequestBody AddBoyRequest addBoyRequest) throws Exception {
        Boy boy = boyService.edit(id, addBoyRequest);
        return new ResponseEntity<>(boy, HttpStatus.OK);
    }

    
    @GetMapping("/boys/delete/{id}")
    @ResponseBody
    public ResponseEntity deleteBoy(@PathVariable long id) throws Exception {
        boyService.delete(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

//    
//    @GetMapping("/boys/export")
//    public ResponseEntity export(HttpServletResponse response) throws Exception {
//        List<Boy> boyList = boyService.listAll();
//        excelService.export(response, boyList);
//        return new ResponseEntity<>("OK", HttpStatus.OK);
//    }

    
    @PostMapping ("/boys/export")
    public ResponseEntity export(@RequestBody List<Boy> boyList, HttpServletResponse response) throws Exception {
        excelService.export(response, boyList);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    
    @GetMapping("/boys/byOptions")
    public ResponseEntity<Page<Boy>> getEmployees(HttpServletResponse response,
                                                  @RequestParam(value = "isExport", defaultValue = "false") boolean isExport,
                                                  BoyPage boyPage,
                                                  BoySearchCriteria boySearchCriteria) throws CustomException {
        System.out.print(isExport);
        List<Boy> result = new ArrayList<>();
        Page<Boy> page= boyService.getBoysByOptions(boyPage, boySearchCriteria);
        if(isExport) {
            for (int i = 0; i < page.getTotalPages(); i++) {
                result.addAll(page.getContent());
            }
            try {
                excelService.export(response, result);
            } catch (Exception e) {
                 new CustomException("Cannot export excel file", 404);
            }
        }
        return new ResponseEntity(page, HttpStatus.OK);
    }

}
