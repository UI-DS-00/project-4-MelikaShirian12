package com.example.searchbar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Getter
@Setter
@RequiredArgsConstructor
@Component
public class FindingRespondForAdding {
    private Boolean state;
    private Node<String> lastNodeForAdding;

    private String differenceWord;//for the time the return statement is false

    private boolean isContains= false;
    public FindingRespondForAdding(Boolean state, Node<String> lastNodeForAdding) {
        this.state = state;
        this.lastNodeForAdding = lastNodeForAdding;
    }

    public FindingRespondForAdding(Boolean state, Node<String> lastNodeForAdding, String differenceWord) {
        this.state = state;
        this.lastNodeForAdding = lastNodeForAdding;
        this.differenceWord = differenceWord;
    }

    public FindingRespondForAdding(Boolean state, Node<String> lastNodeForAdding, String differenceWord, boolean isContains) {
        this.state = state;
        this.lastNodeForAdding = lastNodeForAdding;
        this.differenceWord = differenceWord;
        this.isContains = isContains;
    }
}
