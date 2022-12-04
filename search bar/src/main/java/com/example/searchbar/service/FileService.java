package com.example.searchbar.service;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    public Boolean readDefaultFiles() throws IOException;

    public boolean newFile(String path) throws IOException;

    public boolean addWordsToTree(String [] words , String file_number);

}
