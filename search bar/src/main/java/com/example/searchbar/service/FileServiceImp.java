package com.example.searchbar.service;

import com.example.searchbar.model.FindingRespondForAdding;
import com.example.searchbar.model.MyTree;
import com.example.searchbar.model.Node;
import com.example.searchbar.model.Sibling;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Scanner;

@Slf4j
@AllArgsConstructor
@Service
public class FileServiceImp implements FileService{


    private MyTree tree ;
    private final String defaultpath = "D:/university/programs-data struct/github projects/project-4-MelikaShirian12/script files/EnglishData2";
    private static int makeNumberForNewFiles = 1;

    @Override
    public Boolean readDefaultFiles() throws IOException {

        //initializing the tree
        MyTree.MyTreeCreator();
        tree = MyTree.getInstance();

        String address = defaultpath;
        File file = new File(address);
        File [] files_name = file.listFiles();
        address+="/";


        for (File eachFile : files_name) {


            Scanner readInfo = new Scanner(eachFile);
            String [] words;
            while (readInfo.hasNext()) {
                words = readInfo.nextLine().split("\\s+");
                addWordsToTree(words , eachFile.getName());
            }
            readInfo.close();

        }


        return true;
    }

    @Override
    public boolean newFile(String path) {
        FileInputStream read= null;
        try {
            read = new FileInputStream(path);
            Scanner readInfo = new Scanner(read);

            String [] words;
            while (readInfo.hasNext()) {
                words = readInfo.nextLine().split("\\s+");
                addWordsToTree(words , "new files"+makeNumberForNewFiles);
                ++makeNumberForNewFiles;
            }


            readInfo.close();
            read.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean addWordsToTree(String[] words , String file_number) {


        Node <String> previous = null ;

        for (String eachWord : words) {
            eachWord = eachWord.replaceAll("[^a-zA-Z]", "");
            //eachWord = eachWord.replaceAll("\\d", "");
            eachWord= eachWord.toLowerCase();

            if (eachWord.equals(""))
                continue;

            Node node = MyTree.getInstance().getRoot();

            String findingWord = "";
            FindingRespondForAdding isThere = MyTree.searchInTree2(eachWord, node, findingWord);

            if (isThere != null) {
                node = isThere.getLastNodeForAdding();
                findingWord = isThere.getDifferenceWord();
            }

            if (isThere == null) {
                //we should turn back into the parent and add a new child for this new word

                String[] addingAlphabets = eachWord.split("");
                Node <String> node1 = MyTree.addNodesToTree(MyTree.getInstance().getRoot(), addingAlphabets , file_number);

                if (previous != null) {
                    previous.getRightSiblings().add(new Sibling(file_number, node1));
                    node1.getLeftSiblings().add(new Sibling(file_number , previous));
                }
                previous = node1;
            }


            else if (isThere.getState()) {
                node.setHasMeaning(true);
                node.getFileNumbers().add(file_number);

                if (previous != null) {
                    previous.getRightSiblings().add(new Sibling(file_number, node));
                    node.getLeftSiblings().add(new Sibling(file_number , previous));
                }
                previous = node;
            } else { //this means we have to add from root because there was no coincidence for that word
                int startingMissingCharacters = eachWord.length() - findingWord.length() ;
                String[] addingWords = new String[startingMissingCharacters];

                for (int i = 0; i < startingMissingCharacters; ++i)
                    addingWords[i] = Character.toString(eachWord.charAt(i+findingWord.length()));

                Node <String> node1 = MyTree.addNodesToTree(node, addingWords , file_number);

                if (previous != null) {
                    previous.getRightSiblings().add(new Sibling(file_number, node1));
                    node1.getLeftSiblings().add(new Sibling(file_number , previous));
                }
                previous = node1;
            }
        }

        return true;
    }
}
