package com.example.searchbar.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class Input2 {

    String word;
    String fileNumber;


    public Input2(String word, String fileNumber) {
        this.word = word;
        this.fileNumber = fileNumber;
    }
}
