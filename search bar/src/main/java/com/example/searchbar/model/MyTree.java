package com.example.searchbar.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
@Component
public class  MyTree {

    private static MyTree object = null;
    private Node <String> root;
    private int size;


    private static Set<SimilarWords> similarWords = new HashSet<>();

    public MyTree() {
        this.root = new Node<>("/root/");
    }

    public static MyTree MyTreeCreator(){
        if (object == null)
            object = new MyTree();
        return object;
    }

    public static MyTree getInstance(){
        return object;
    }

    public static void makeNewSimilarWords(){
        similarWords = new HashSet<>();
    }

    public static FindingRespondForAdding searchInTree(String word , Node <String> lastNode , String findingNode) {

        FindingRespondForAdding state = null;
        Node <String> theNode = null;
        Queue<Node> nodes = new ArrayDeque<>();


        if (getInstance().getRoot().getChildren().isEmpty()) {
            FindingRespondForAdding findingRespondForAdding=new FindingRespondForAdding(false, getInstance().getRoot());
            findingRespondForAdding.setDifferenceWord("");

            return findingRespondForAdding;
        }


        nodes.add(lastNode);

        while (!nodes.isEmpty()) {

            theNode = nodes.remove();
            if (theNode != getInstance().getRoot())
                findingNode += theNode.getData();

            if (findingNode.equals(word))
                return new FindingRespondForAdding(true , theNode);


            if (word.contains(findingNode)){
                StringBuilder s = new StringBuilder(findingNode);

                String new_word="";
                for (int i=0 ; i<s.length() ; ++i)
                    new_word += Character.toString (word.charAt(i));

                if (!new_word.equals(findingNode)){
                    s.deleteCharAt(findingNode.length()-1);
                    if (s.length() == 0)return null;
                    return new FindingRespondForAdding(false, theNode.getParent(), s.toString());
                }else if (theNode.getChildren().isEmpty()) return new FindingRespondForAdding(false, theNode, theNode.getData());

            }

            if (! word.contains(findingNode)) {

                StringBuilder s = new StringBuilder(findingNode);
                s.deleteCharAt(s.length()-1);

                String new_word="";
                for (int i=0 ; i<s.length() ; ++i)
                    new_word += Character.toString (word.charAt(i));


                if (s.length()!= 0 && new_word.equals(s.toString()))
                     return new FindingRespondForAdding(false, theNode.getParent(), s.toString() , true);

                else return null;
            }

            for (Object child : theNode.getChildren()) {
                state = searchInTree(word, (Node<String>) child, findingNode);

                if (state != null && state.isContains()) {
                    state.setContains(false);
                    continue;
                }
                //this means we have found its place and we don't need further traversal
                if (state != null && !state.getState() && state.getDifferenceWord().length() != 0)
                    return state;
                //if it is null that means we can still find that word on that path
            }

        }

        return state;
        // by value (we don't have duplicate values in this tree)

    }


    public static FindingRespondForAdding searchInTree2(String word , Node <String> lastNode , String findingNode) {

        FindingRespondForAdding state = null;
        Node <String> theNode = null;
        Queue<Node> nodes = new ArrayDeque<>();


        if (getInstance().getRoot().getChildren().isEmpty()) {
            FindingRespondForAdding findingRespondForAdding=new FindingRespondForAdding(false, getInstance().getRoot());
            findingRespondForAdding.setDifferenceWord("");

            return findingRespondForAdding;
        }


        nodes.add(lastNode);

        while (!nodes.isEmpty()) {

            theNode = nodes.remove();
            if (theNode != getInstance().getRoot())
                findingNode += theNode.getData();

            StringBuilder s = new StringBuilder(findingNode);

            String new_word="";
            for (int i=0 ; i<s.length() ; ++i)
                new_word += Character.toString (word.charAt(i));

            if (findingNode.equals(new_word)) {

                if (word.equals(findingNode))
                    return new FindingRespondForAdding(true, theNode);

            }

            if (! findingNode.equals(new_word))
                return new FindingRespondForAdding(false , theNode.getParent() , s.deleteCharAt(s.length()-1).toString());


            for (Object child : theNode.getChildren()) {


                if (state != null && state.getState())
                    return state;//we have already found it , and we don't need further searching

                //we should check other children as well if it was false so we continue

                if (state != null && state.isContains())
                    return state;

                state = searchInTree2(word, (Node<String>) child, findingNode);
            }

            //this mean we have checked all the closest possibilities and this is the best place for adding
            if (state == null)
                return new FindingRespondForAdding(false , theNode , findingNode , true);
            state.setContains(true);

        }

        return state;
        // by value (we don't have duplicate values in this tree)

    }

    public static Node<String> addNodesToTree(Node<String> startingNode , String [] alphabets , String file_number){

        for (int i =0 ; i<alphabets.length ; ++i){

            Node <String> new_node= new Node<>(alphabets[i]);
            startingNode.getChildren().add(new_node);
            new_node.setParent(startingNode);

            startingNode = new_node;
        }

        startingNode.setHasMeaning(true);
        startingNode.getFileNumbers().add(file_number);
        return startingNode;

    }

    public static Set <SimilarWords> [] findWord(String word , Node <String> theNode , String findingNode){

        FindingRespondForAdding state = null;


        if (getInstance().getRoot().getChildren().isEmpty()) {
            FindingRespondForAdding findingRespondForAdding=new FindingRespondForAdding(false, getInstance().getRoot());
            findingRespondForAdding.setDifferenceWord("");

            return new Set[]{similarWords};
        }

        if (theNode != MyTree.getInstance().getRoot())
            findingNode +=  theNode.getData();

        //checking the characters and also if this word has meaning and it is not just a random word that is made by other words' alphabets
        if (findingNode.equals(word) && theNode.isHasMeaning())
            similarWords.add(new SimilarWords(findingNode , theNode.getFileNumbers()));

        else if (findingNode.contains(word)) {
                if (theNode.isHasMeaning())
                    similarWords.add(new SimilarWords(findingNode, theNode.getFileNumbers()));
        }

        else {

            char[] first  = word.toCharArray();
            char[] second = findingNode.toCharArray();

            int minLength = Math.min(first.length, second.length);

            int counter = 0;
            for(int i = 0; i < minLength; i++)
                if (first[i] == second[i])
                    counter++;

            if (counter<= word.length() &&  counter >= 3)
                if (theNode.isHasMeaning())
                    similarWords.add(new SimilarWords(findingNode, theNode.getFileNumbers()));
        }

        for (Object child : theNode.getChildren())
            findWord (word, (Node<String>) child, findingNode);


        return new Set[]{similarWords};
        // by value (we don't have duplicate values in this tree)
    }

    public static Node<String> findSpecificWord(String word, Node <String > lastNode, String findingNode){

        word= word.replaceAll("\\s+" , "");
        word = word.replaceAll("[^a-zA-Z]", "");
        word= word.toLowerCase();

        if (word.length() == 0)
            throw new NoSuchElementException("the word is out of meaning");

        FindingRespondForAdding findingRespondForAdding = searchInTree2(word , lastNode , "");

        if (findingRespondForAdding.getState() != null && findingRespondForAdding.getState())
            if (findingRespondForAdding.getLastNodeForAdding().isHasMeaning())
                return findingRespondForAdding.getLastNodeForAdding();

        throw new NoSuchElementException("the word is out of meaning");
    }

    public static Set<String> findGroupOfWords(String group){

        Set <String> jointFiles = new HashSet<>();
        String [] words = group.split("\\s+");

        boolean allHavingJointFiles = false;
        for (int i=0 ; i< words.length-1 ; ++i){

            Node <String> current_node = findSpecificWord(words[i] ,  MyTree.getInstance().getRoot(),"");
            Node <String> right_node = findSpecificWord(words[i+1], MyTree.getInstance().getRoot(),"" );

            if(current_node == null || right_node == null)
                throw new NoSuchElementException("no such word found");

            HashSet <String> jointFilesForTwo = new HashSet<>();


            for (Sibling entry : current_node.getRightSiblings()) {
                if (entry.getNode() == right_node)
                    jointFilesForTwo.add(entry.getFile_number());
            }


            for (String file  : jointFilesForTwo) {

                if (i == 0) {
                    jointFiles.add(file);
                    allHavingJointFiles = true;
                }
                    //if it wasn't the first comparison , we should check if we have this joint with others as well or not
                else if (jointFiles.contains(file))
                        allHavingJointFiles = true;
                }

            if (!allHavingJointFiles)
                throw new NoSuchElementException("no sush file found");

            allHavingJointFiles = false;

            }

        return jointFiles;
    }
}
