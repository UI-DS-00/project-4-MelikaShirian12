package com.example.searchbar.service;


import com.example.searchbar.model.SimilarWords;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.searchbar.model.MyTree;
import com.example.searchbar.model.Node;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class MyTreeServiceImp implements MyTreeService{

    @Override
    public Set<String> findSpecificWord(String word) {
        Node <String> theWord=MyTree.findSpecificWord(word , MyTree.getInstance().getRoot(),"");

        if (theWord == null)
            throw new NoSuchElementException("the word counld not be found");

        if (theWord.getData().equals(""))
            throw new NoSuchElementException("the word doesn't have a meaning");

        return theWord.getFileNumbers();
    }

    @Override
    public Set<SimilarWords>[] findWord(String word) {

        MyTree.makeNewSimilarWords();
        Set <SimilarWords> []similarWords = MyTree.findWord(word , MyTree.getInstance().getRoot(),"");

        if (similarWords ==null || similarWords.length == 0)
            throw new NoSuchElementException("could not find any similar word ");

        return similarWords;
    }


    @Override
    public Set<String> findAGroupOfWords(String group) {
        //splitting throw math equations
        String [] commands = group.split("(?=[-+*/()])|(?<=[^-+*/][-+*/])|(?<=[()])");

        String command ;
        HashSet<String> fileNumbers = new HashSet<>();
        HashSet <String> plusFileNumbers= new HashSet<>();

        //for the files + ones are in so after that we can find the best shot
        HashSet <String> plusFounded= new HashSet<>();
        for (int i= 0 ; i<commands.length ;++i){

            command = commands[i];

            if (!command.equals("+") && !command.equals("-")) {
                String[] words = command.split("\\s+");


                Node<String> node;
                Set<String> groupNodeFinding;
                if (words.length == 1) {
                    node = MyTree.findSpecificWord(words[0],  MyTree.getInstance().getRoot(),"");
                    groupNodeFinding = node.getFileNumbers();
                } else
                    groupNodeFinding = MyTree.findGroupOfWords(command);

                if (((i==1 )|| i == 0) && fileNumbers.isEmpty())
                    fileNumbers = (HashSet<String>) ((HashSet<String>) groupNodeFinding).clone();


                if (i!=0 && (commands[i-1].equals("+")  || commands[i-1].equals("-"))){
                    command = commands[i - 1];

                    switch (command) {
                        case "+":
                            //havingPlus = true;
                            HashSet <String> foundedOnes = new HashSet<>();
                            for (String file : groupNodeFinding) {
                                plusFileNumbers.add(file);
                                if (fileNumbers.contains(file))
                                    foundedOnes.add(file);
                            }


                            fileNumbers=foundedOnes;

                            break;

                        case "-":

                            //finding the ones that have the word we don't want to have
                            HashSet<String> deletingHavingNegative = new HashSet<>();
                            for (String file : groupNodeFinding) {
                                if (fileNumbers.contains(file))
                                    deletingHavingNegative.add(file);
                            }

                            //removing the ones we don't want to have from list of our file numbers
                            for (String deleting : deletingHavingNegative)
                                fileNumbers.remove(deleting);

                    }
                }
            }
        }

      /*  HashSet <String> finalResult = new HashSet<>();
       for (String file : fileNumbers)
            if (plusFounded.contains(file))
                finalResult.add(file);

       */

        if (fileNumbers.isEmpty())
            throw new NoSuchElementException("no such directory found");

        return fileNumbers;
    }

    @Override
    public Boolean removeWord(String word) {


        Node <String> node=MyTree.findSpecificWord(word , MyTree.getInstance().getRoot(),"");

        if (node == null)
            throw new NoSuchElementException("the word could not be found");

        if (node.getData().equals(""))
            throw new NoSuchElementException("the word doesn't have a meaning");


        node.setHasMeaning(false);
        node.setFileNumbers(new HashSet<>());

        return true;
    }

    @Override
    public Boolean removeWord(String word, String fileName){


        Node <String> node=MyTree.findSpecificWord(word , MyTree.getInstance().getRoot(),"");

        if (node == null)
            throw new NoSuchElementException("the word could not be found");

        if (node.getData().equals(""))
            throw new NoSuchElementException("the word doesn't have a meaning");

        for (String file : node.getFileNumbers()){
            if (file.equals(fileName))
            {
                node.getFileNumbers().remove(file);
                return true;
            }
        }

        log.info("there was no such file number");
        return false;
    }

    @Override
    public Boolean updateWord(String word , String fileNumber) throws NoSuchElementException {

        Node <String> node=MyTree.findSpecificWord(word , MyTree.getInstance().getRoot(),"");

        if (node == null)
            throw new NoSuchElementException("the word could not be found");

        if (node.getData().equals(""))
            throw new NoSuchElementException("the word doesn't have a meaning");


        for (String file : node.getFileNumbers())
            if (file.equals(fileNumber))
                log.info("this file is already there");
        node.getFileNumbers().add(fileNumber);

        return true;

    }
}
