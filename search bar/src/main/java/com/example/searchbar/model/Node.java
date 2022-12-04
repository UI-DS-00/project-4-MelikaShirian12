package com.example.searchbar.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class Node <MODEL> {

    private ArrayList <Node <MODEL>> children;
    private Node <MODEL> parent;
    private MODEL data;

    private boolean hasMeaning = false;

    private Set<String> fileNumbers;

    private ArrayList <Sibling> rightSiblings;
    private ArrayList <Sibling> leftSiblings;

    public Node(MODEL data) {
        this.parent = null;
        this.data = data;
        this.children= new ArrayList<>();
        this.fileNumbers = new HashSet<>();
        this.leftSiblings = new ArrayList<>();
        this.rightSiblings = new ArrayList<>();
    }
}