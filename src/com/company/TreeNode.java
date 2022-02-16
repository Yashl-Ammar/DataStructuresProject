package com.company;

import java.util.ArrayList;

public class TreeNode <T> {

    ArrayList<String []> arrayList= new ArrayList<String []>();

    ArrayList<Integer> selectedCols = new ArrayList<>();
    T data;
    ArrayList<String> branches = new ArrayList<>();
    ArrayList<TreeNode<T>> allNodes = new ArrayList<>();
    int index = 0;


    public ArrayList<String[]> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String[]> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<Integer> getSelectedCols() {
        return selectedCols;
    }

    public void setSelectedCols(ArrayList<Integer> selectedCols) {
        this.selectedCols = selectedCols;
    }

    public ArrayList<String> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<String> branches) {
        this.branches = branches;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TreeNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ArrayList<TreeNode<T>> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(ArrayList<TreeNode<T>> allNodes) {
        this.allNodes = allNodes;
    }
}
