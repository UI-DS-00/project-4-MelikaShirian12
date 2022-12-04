package com.example.searchbar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class Sibling {

    private String file_number;
    private Node <String> node ;

    public Sibling(String file_number, Node<String> node) {
        this.file_number = file_number;
        this.node = node;
    }
}
