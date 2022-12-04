package com.example.searchbar.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.searchbar.service.FileServiceImp;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@AllArgsConstructor
@Controller
public class WorkWithFilesController {

    private FileServiceImp fileServiceImp;

    @GetMapping("/upload")
    public ResponseEntity<Boolean> upload() throws SQLException, IOException, ClassNotFoundException {
        return new ResponseEntity<>(fileServiceImp.readDefaultFiles(), HttpStatus.CREATED);
    }

    @PostMapping("/special/upload")
    public ResponseEntity<Boolean> specialUpload(@RequestBody String path) throws SQLException, IOException, ClassNotFoundException {
        return new ResponseEntity<>(fileServiceImp.newFile(path), HttpStatus.CREATED);
    }

}
