package com.example.searchbar.controller;


import com.example.searchbar.model.Input;
import com.example.searchbar.model.Input2;
import com.example.searchbar.model.SimilarWords;
import com.example.searchbar.service.MyTreeService;
import com.example.searchbar.service.MyTreeServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@RestController
@AllArgsConstructor
public class MyTreeController {

    MyTreeServiceImp myTreeServiceImp;

    @PostMapping("/word/special")
    public ResponseEntity<Set<String>> specialWord(@RequestBody Input word)  {
        return new ResponseEntity<>(myTreeServiceImp.findSpecificWord(word.getInput()), HttpStatus.OK);
    }


    @PostMapping("/word")
    public ResponseEntity<Set<SimilarWords> []> findWord(@RequestBody String word)  {
        return new ResponseEntity<>(myTreeServiceImp.findWord(word), HttpStatus.OK);
    }

    @PostMapping("/word/group")
    public ResponseEntity<Set<String>> findGroup(@RequestBody String word) {
        return new ResponseEntity<>(myTreeServiceImp.findAGroupOfWords(word), HttpStatus.OK);
    }

    @PostMapping("/word/remove")
    public ResponseEntity<Boolean> removeWord(@RequestBody String word) {
        return new ResponseEntity<>(myTreeServiceImp.removeWord(word), HttpStatus.OK);
    }

    @PostMapping("/word/remove/file")
    public ResponseEntity<Boolean> removeWord(@RequestBody Input2 input) {
        return new ResponseEntity<>(myTreeServiceImp.removeWord(input.getWord() , input.getFileNumber()), HttpStatus.OK);
    }



    @PostMapping("/word/update")
    public ResponseEntity<Boolean> updateWord(@RequestBody Input2 input) {
        return new ResponseEntity<>(myTreeServiceImp.updateWord(input.getWord() , input.getFileNumber()) , HttpStatus.OK);
    }

}
