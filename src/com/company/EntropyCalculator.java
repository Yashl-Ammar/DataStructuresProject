package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class EntropyCalculator {

    private int windowSize = 0;
    static int firstWindowSize = 0;


    public double Entropy(String Subject,String filepath) throws IOException {
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> arrayList=new ArrayList<>();
        ArrayList<Integer> array=new ArrayList<>();
        String line = bufferedReader.readLine();
        line= bufferedReader.readLine();
        int sum=0;
        double EntropyCalculator=0.0;
        while(line!=null)
        {


            String[] arr=line.split(",");
            if(Subject.equals("CS201"))
            {
                Boolean B=false;
                for (String i: arrayList) {
                    if(arr[16].equals(i))
                    {
                        array.set(arrayList.indexOf(i),array.get(arrayList.indexOf(i))+1);
                        B=true;
                        break;
                    }
                }
                if(!B)
                {
                    if(!arr[16].equals(" "))
                    {
                        arrayList.add(arr[16]);
                        array.add(1);
                    }
                }
            }

            line= bufferedReader.readLine();
        }

        for (Integer i: array) {

            sum=sum+i;
        }
        ArrayList<Double> entropy=new ArrayList<>();
        for (Integer i: array)
        {
               double fraction=(double)i/sum;
               entropy.add(fraction);
        }
        double  Entropysum=0;
        for (Double i: entropy) {

                Entropysum+= - i * (Math.log(i)/Math.log(2) );

        }
        //System.out.println("Entropy of this Subject is :" +Entropysum);

        bufferedReader.close();
        fileReader.close();


        return Entropysum;
    }


    public double conditionedEntropy(int index,String grade , String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        ArrayList<String> dsGrade = new ArrayList<>();
        ArrayList<Integer> dsGradeCount = new ArrayList<>();

        int sum = 0;
        String line = bufferedReader.readLine();
        line = bufferedReader.readLine();
        while(line != null)
        {
            String [] arr =  line.split(",");
            if(arr[index].equals(grade))
            {
                sum++;
                boolean b = false;
                for (String i : dsGrade)
                {
                    if (i.equals(arr[16]))
                    {
                        int tempIndex = dsGrade.indexOf(i);
                        dsGradeCount.set(tempIndex, dsGradeCount.get(tempIndex)+1);
                        b = true;
                        break;
                    }
                }
                if (!b)
                {
                    dsGrade.add(arr[16]);
                    dsGradeCount.add(1);
                }
            }
            line = bufferedReader.readLine();
        }

        ArrayList<Double> fractions = new ArrayList<>();
        for (Integer i : dsGradeCount)
        {
            fractions.add((double)i/sum);
        }
        double conditionedEntropy = 0;
        for (Double i : fractions)
        {
            conditionedEntropy += - i * (Math.log(i)/Math.log(2) );
        }

        bufferedReader.close();
        fileReader.close();

        //System.out.println(conditionedEntropy);

        return conditionedEntropy;

    }

    public double weightedEntropy(int index,String filepath) throws IOException {
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        ArrayList<String> grades = new ArrayList<>();
        ArrayList<Integer> gradeCounts = new ArrayList<>();

        String line = bufferedReader.readLine();
        line = bufferedReader.readLine();

        while (line!= null)
        {
            String [] arr = line.split(",");
            boolean b = false;
            for(String i : grades)
            {
                if(arr[index].equals(i))
                {
                    int tempIndex = grades.indexOf(i);
                    gradeCounts.set(tempIndex, gradeCounts.get(tempIndex)+1);
                    b = true;
                    break;
                }
            }
            if(!b)
            {
                grades.add(arr[index]);
                gradeCounts.add(1);
            }

            line = bufferedReader.readLine();
        }

        int sum = 0;
        for (Integer i : gradeCounts)
        {
            sum += i;
        }

        double weightedEntropy = 0;
        int counter = 0;
        for (String i: grades)
        {
            weightedEntropy += (double)gradeCounts.get(counter)/sum  *  conditionedEntropy(index,i,filepath);
            counter++;
        }


        //System.out.println("Weighted Entropy of col " + index + " : " + weightedEntropy);

        bufferedReader.close();
        fileReader.close();

        return weightedEntropy;

    }

    public double informationGain(int index,String filePath) throws IOException {
        double infoGain = Entropy("CS201",filePath) - weightedEntropy(index, filePath);

        return infoGain;
    }

    public ArrayList<String []> create2darr(int index, String grade , String filepath) throws IOException {

        ArrayList<String []> arrayList= new ArrayList<String []>();

        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = bufferedReader.readLine();
        arrayList.add(line.split(","));
        line=bufferedReader.readLine();

        while (line!= null)
        {
            String [] tempArr = line.split(",");

            if (tempArr[index].equals(grade))
            {
                arrayList.add(tempArr);
            }

            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        fileReader.close();




        FileWriter fileWriter = new FileWriter("ConditionalEntropyFile.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (String [] i : arrayList)
        {
            for (String j : i)
            {
                bufferedWriter.write(j + ",");
            }
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();
        fileReader.close();

        return arrayList;

    }

    public void createTreeRoot(int windowSize) throws IOException {
        FileReader fileReader = new FileReader("trainigDataset.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        if (this.windowSize == 0) {
            this.windowSize = windowSize;
        }

        double [] arr = new double[windowSize];
        double max = 0;
        int maxIndex = 0;

        System.out.println("window : "+this.windowSize);
        if (this.windowSize%15 == windowSize || this.windowSize%15 == windowSize+1)
        {
            this.windowSize = windowSize;
        }
        if (this.windowSize < 15) {
            for (int i = this.windowSize - windowSize, j = 0; i < this.windowSize;j++, i++) {
                arr[j] = informationGain(i + 1, "trainigDataset.csv");
                if (arr[j] > max) {
                    max = arr[j];
                    maxIndex = i;
                }
            }
        }
        else
        {

            for (int i = 0; i < this.windowSize%15; i++) {
                arr[i] = informationGain(i + 1, "trainigDataset.csv");
                if (arr[i] > max) {
                    max = arr[i];
                    maxIndex = i;
                }
            }

            for (int i = 14-windowSize+this.windowSize%15 , j = this.windowSize%15; i < 14; j++, i++) {
                arr[j] = informationGain(i + 1, "trainigDataset.csv");
                if (arr[j] > max) {
                    max = arr[j];
                    maxIndex = i;
                }
            }
        }


        ArrayList<String> allGrades = new ArrayList<>();

        String line = bufferedReader.readLine();
        String [] firstLine = line.split(",");
        String rootNodeValue = firstLine[maxIndex+1];
        line = bufferedReader.readLine();
        while (line != null)
        {
            String [] tempArr = line.split(",");

            boolean b = false;
            for (String i : allGrades)
            {
                if (tempArr[maxIndex+1].equals(i))
                {
                    b = true;
                    break;
                }
            }
            if(!b)
            {
                if (!tempArr[maxIndex+1].equals(" ")) {
                    allGrades.add(tempArr[maxIndex + 1]);
                }
            }

            line = bufferedReader.readLine();
        }
        TreeNode<String> tempNode = new TreeNode<>(rootNodeValue);

        for(String i : allGrades)
        {
            tempNode.branches.add(i);
            tempNode.allNodes.add(null);
        }

        tempNode.selectedCols.add(maxIndex+1);

        tempNode.index = maxIndex+1;

        Tree tempTree = new Tree(tempNode);

        Tree.allTrees.add(tempTree);

        recurCreate(tempTree.getRoot(),0);

        bufferedReader.close();
        fileReader.close();
    }



    public void recurCreate(TreeNode<String> root,int depth) throws IOException {
        if (root == null)
        {
            return;
        }

        for (String i : root.branches)
        {
            if (depth == 0) {
                if (conditionedEntropy(root.index, i, "trainigDataset.csv") == 0) {
                    FileReader fileReader = new FileReader("trainigDataset.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String line = bufferedReader.readLine();
                    line = bufferedReader.readLine();

                    while (line != null)
                    {
                        String [] tempArr = line.split(",");

                        if (tempArr[root.index].equals(i))
                        {
                            TreeNode<String> newNode = new TreeNode<>(tempArr[16]);
                            int i1 = root.branches.indexOf(i);
                            root.allNodes.set(i1,newNode);
                            break;
                        }
                        line = bufferedReader.readLine();
                    }

                    bufferedReader.close();
                    fileReader.close();
                }
                else
                {
                    ArrayList<String []> temp = create2darr(root.index,i,"trainigDataset.csv");

                    FileReader fileReader = new FileReader("ConditionalEntropyFile.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    double [] arr = new double[windowSize];
                    double max = 0;
                    int maxIndex = 0;

                    if (this.windowSize < 15) {
                        for (int l = this.windowSize - firstWindowSize, j = 0; l < this.windowSize;j++, l++) {
                            //System.out.println(l);
                            for (Integer k : root.selectedCols) {
                                if (j!=k) {
                                    arr[j] = informationGain(l + 1, "ConditionalEntropyFile.csv");
                                    if (arr[j] > max) {
                                        max = arr[j];
                                        maxIndex = l;
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        for (int j = 0; j < this.windowSize%15; j++) {
                            //System.out.println(j);
                            for (Integer k : root.selectedCols) {
                                if (j!=k) {
                                    arr[j] = informationGain(j + 1, "ConditionalEntropyFile.csv");
                                    if (arr[j] > max) {
                                        max = arr[j];
                                        maxIndex = j;
                                    }
                                }
                            }
                        }
                        for (int j = 14-firstWindowSize+this.windowSize%15 , l = this.windowSize%15; j < 14; l++, j++) {
                            //System.out.println(j);
                            for (Integer k : root.selectedCols) {
                                if (j!=k) {
                                    arr[l] = informationGain(j + 1, "ConditionalEntropyFile.csv");
                                    if (arr[l] > max) {
                                        max = arr[l];
                                        maxIndex = l;
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println();


                    String line = bufferedReader.readLine();
                    String [] firstLine = line.split(",");
                    String newNodeVal = firstLine[maxIndex+1];

                    ArrayList<String> allGrades = new ArrayList<>();

                    line = bufferedReader.readLine();
                    while (line != null)
                    {
                        String [] tempArr = line.split(",");

                        boolean b= false;
                        for (String j : allGrades)
                        {
                            if (j.equals(tempArr[maxIndex+1]))
                            {
                                b = true;
                                break;
                            }
                        }
                        if (!b)
                        {
                            allGrades.add(tempArr[maxIndex+1]);
                        }

                        line = bufferedReader.readLine();
                    }

                    TreeNode<String> newNode = new TreeNode<>(newNodeVal);
                    for (String j : allGrades)
                    {
                        newNode.branches.add(j);
                        newNode.allNodes.add(null);
                    }

                    for (Integer j : root.selectedCols)
                    {
                        newNode.selectedCols.add(j);
                    }

                    newNode.selectedCols.add(maxIndex+1);

                    for (String [] j : temp)
                    {
                        newNode.arrayList.add(j);
                    }

                    newNode.index = maxIndex+1;


                    int i1 = root.branches.indexOf(i);
                    root.allNodes.set(i1,newNode);

                    bufferedReader.close();
                    fileReader.close();
                }
            }
            else
            {
                ArrayList<String []> arrayList = root.arrayList;
                writeToCsv(arrayList);
                if (conditionedEntropy(root.index, i, "ConditionalEntropyFile.csv") == 0) {
                    FileReader fileReader = new FileReader("ConditionalEntropyFile.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String line = bufferedReader.readLine();
                    line = bufferedReader.readLine();

                    while (line != null)
                    {
                        String [] tempArr = line.split(",");

                        if (tempArr[root.index].equals(i))
                        {
                            TreeNode<String> newNode = new TreeNode<>(tempArr[16]);
                            int i1 = root.branches.indexOf(i);
                            root.allNodes.set(i1,newNode);
                            break;
                        }
                        line = bufferedReader.readLine();
                    }

                    bufferedReader.close();
                    fileReader.close();
                }
                else
                {
                    ArrayList<String []> temp = create2darr(root.index,i,"ConditionalEntropyFile.csv");

                    FileReader fileReader = new FileReader("ConditionalEntropyFile.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    double [] arr = new double[windowSize];
                    double max = 0;
                    int maxIndex = 0;
                    if (this.windowSize < 15) {
                        for (int l = this.windowSize - firstWindowSize, j = 0; l < this.windowSize;j++, l++) {
                            //System.out.println(l);
                            for (Integer k : root.selectedCols) {
                                if (j!=k) {
                                    arr[j] = informationGain(l + 1, "ConditionalEntropyFile.csv");
                                    if (arr[j] > max) {
                                        max = arr[j];
                                        maxIndex = l;
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        for (int j = 0; j < this.windowSize%15; j++) {
                            //System.out.println(j);
                            for (Integer k : root.selectedCols) {
                                if (j!=k) {
                                    arr[j] = informationGain(j + 1, "ConditionalEntropyFile.csv");
                                    if (arr[j] > max) {
                                        max = arr[j];
                                        maxIndex = j;
                                    }
                                }
                            }
                        }
                        for (int j = 14-firstWindowSize+this.windowSize%15 , l = this.windowSize%15; j < 14; l++, j++) {
                            //System.out.println(j);
                            for (Integer k : root.selectedCols) {
                                if (j!=k) {
                                    arr[l] = informationGain(j + 1, "ConditionalEntropyFile.csv");
                                    if (arr[l] > max) {
                                        max = arr[l];
                                        maxIndex = l;
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println();

                    String line = bufferedReader.readLine();
                    String [] firstLine = line.split(",");
                    String newNodeVal = firstLine[maxIndex+1];

                    ArrayList<String> allGrades = new ArrayList<>();

                    line = bufferedReader.readLine();
                    while (line != null)
                    {
                        String [] tempArr = line.split(",");

                        boolean b= false;
                        for (String j : allGrades)
                        {
                            if (j.equals(tempArr[maxIndex+1]))
                            {
                                b = true;
                                break;
                            }
                        }
                        if (!b)
                        {
                            allGrades.add(tempArr[maxIndex+1]);
                        }

                        line = bufferedReader.readLine();
                    }

                    TreeNode<String> newNode = new TreeNode<>(newNodeVal);
                    for (String j : allGrades)
                    {
                        newNode.branches.add(j);
                        newNode.allNodes.add(null);
                    }

                    for (Integer j : root.selectedCols)
                    {
                        newNode.selectedCols.add(j);
                    }

                    newNode.selectedCols.add(maxIndex+1);

                    for (String [] j : temp)
                    {
                        newNode.arrayList.add(j);
                    }

                    newNode.index = maxIndex+1;


                    int i1 = root.branches.indexOf(i);
                    root.allNodes.set(i1,newNode);

                    bufferedReader.close();
                    fileReader.close();
                }
            }
        }

        if (root.selectedCols.size() >= windowSize)
        {
            return;
        }


        for (TreeNode<String> i : root.allNodes)
        {
            recurCreate(i,depth+1);
        }

    }


    public void writeToCsv(ArrayList<String []> arrayList) throws IOException {
        FileWriter fileWriter = new FileWriter("ConditionalEntropyFile.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (String [] i : arrayList)
        {
            for (String j : i)
            {
                bufferedWriter.write(j+",");
            }
            bufferedWriter.write("\n");
        }

        bufferedWriter.close();
        fileWriter.close();


    }

    public void shiftWindowSize()
    {
        windowSize+=2;
    }

    public void prediction() throws IOException {
        FileReader fileReader = new FileReader("testingdataset.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        ArrayList<String> allParameters = new ArrayList<>();

        String line = bufferedReader.readLine();
        String [] fileLine = line.split(",");
        for (String i : fileLine)
        {
            allParameters.add(i);
        }

        line = bufferedReader.readLine();


        int trueCounter = 0;

        int counter = 0;
        while (line!= null)
        {
            String [] tempArr = line.split(",");

            ArrayList<String> array = new ArrayList<>();
            ArrayList<Integer> countArray = new ArrayList<>();

            for (Tree i : Tree.allTrees)
            {
                boolean b= false;
                String temp = i.predict(tempArr,allParameters);
                if (!temp.equals("-1")) {
                    for (String k : array) {
                        if (k.equals(temp)) {
                            int index = array.indexOf(k);
                            countArray.set(index, countArray.get(index) + 1);
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        array.add(temp);
                        countArray.add(1);
                    }
                }
            }

            int max = 0;
            int maxIndex =0;
            for (int i = 0 ; i <  countArray.size() ; i++)
            {
                if (max < countArray.get(i))
                {
                    max = countArray.get(i);
                    maxIndex = i;
                }
            }

            if (array.size() > 0) {
                if (array.get(maxIndex).equals(tempArr[16])) {
                    trueCounter++;
                }
                System.out.println("Prediction : " + array.get(maxIndex));
            }

            counter++;
            line = bufferedReader.readLine();
        }

        System.out.println("Accuracy : " + (double)trueCounter/counter * 100);

        bufferedReader.close();
        fileReader.close();

    }





}
