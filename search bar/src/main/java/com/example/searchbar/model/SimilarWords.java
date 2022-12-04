package com.example.searchbar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class SimilarWords {

    private String word;
    private Set<String> file_numbers;

    public SimilarWords(String word, Set<String> file_numbers) {
        this.word = word;
        this.file_numbers = file_numbers;
    }
}
