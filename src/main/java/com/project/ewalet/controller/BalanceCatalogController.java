package com.project.ewalet.controller;

import com.project.ewalet.mapper.BalanceCatalogMapper;
import com.project.ewalet.model.BalanceCatalog;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class BalanceCatalogController {

    @Autowired
    BalanceCatalogMapper balanceCatalogMapper;

    @GetMapping("/get-balance-catalog")
    public ResponseEntity getBalanceCatalog() {
        ArrayList<BalanceCatalog> balanceCatalogArrayList = balanceCatalogMapper.getAll();
        JSONObject jsonResponse = new JSONObject();
        if (!balanceCatalogArrayList.isEmpty()) {
            jsonResponse.put("status", 200);
            jsonResponse.put("data", balanceCatalogArrayList);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        else {
            jsonResponse.put("status", 404);
            jsonResponse.put("message", "Balance catalog is empty");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

}
