package com.example.searchbar.service;

import com.example.searchbar.model.SimilarWords;

import java.util.Set;

public interface MyTreeService {


    public Set<String> findSpecificWord (String word);

    public Set<SimilarWords>[] findWord(String word);
    public Set<String> findAGroupOfWords(String group);

    public Boolean removeWord(String word) throws NoSuchMethodException;

    public Boolean removeWord(String word , String fileName) throws NoSuchMethodException;

    public Boolean updateWord(String word , String fileNumber) throws NoSuchMethodException;
}
