package com.company;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        try {
            PreProcessing();
            trainingdataset();
            EntropyCalculator E=new EntropyCalculator();
            boolean b = false;
            int windowSize = 0;
            do {
                b = false;
                System.out.println("Enter window size : ");
                windowSize = sc.nextInt();

                if (windowSize < 1 || windowSize > 14)
                {
                    System.out.println("Invalid input try again");
                    b =true;
                }
            }while (b);

            int noTrees = 0;
            do {
                b = false;
                System.out.println("Enter number of trees : ");
                noTrees = sc.nextInt();

                if (noTrees < 1)
                {
                    System.out.println("Invalid input try again");
                    b =true;
                }
            }while (b);


            EntropyCalculator.firstWindowSize = windowSize;
            for (int i = 0 ; i < noTrees ; i++) {
                E.createTreeRoot(windowSize);
                E.shiftWindowSize();
            }
            Tree.displayAllTrees();
            E.prediction();


        }catch (IOException e)
        {

        }
    }
  public static void PreProcessing() throws IOException {
      FileWriter fileWriter = new FileWriter("newDataset.csv");
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      FileReader fileReader = new FileReader("DataFolder\\c.csv");
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String line = bufferedReader.readLine();
      bufferedWriter.write("SrNo"+"," + "CL118"+"," + "CL217"+"," + "CS118"+"," + "CS217"+","  + "EE227"+"," + "EL227"+"," + "MT104"+"," + "MT119"+","+"MT224"+"," + "SL101"+","  + "SS101"+","   + "SS122"+"," +"EE182"+"," + "cgpa"+"," + "warnings"+"," + "CS201");
      bufferedWriter.write("\n");

      int counter = 0;
      boolean b = false;

      String gradePoint = "";
      ArrayList<String> subGradePoints = new ArrayList<>();
      ArrayList<String> courseCodes = new ArrayList<>();
      String cgpa = "";
      String warning = "";
      String SRNo = "";
      while(line != null)
      {
          double prevSr = 0;
          double prevCgpa = 0;
          double prevWarning = 0;
          if(!cgpa.equals("") && !warning.equals("")) {
              prevSr = Double.parseDouble(SRNo);
              prevCgpa = Double.parseDouble(cgpa);
              prevWarning= Double.parseDouble(warning);
          }

          String [] arr = line.split(",");
          int tempSr = Integer.parseInt(arr[0]);

          SRNo = arr[0];
          cgpa = arr[8];
          warning = arr[9];

          if(tempSr == counter) {

              if(arr[2].equals("CS201") && !arr[5].equals("W"))
              {
                  gradePoint = arr[6];
                  b = true;
              }
              else {
                  int tempCount = 0;
                  boolean tempFlag = false;
                  for (String k : courseCodes) {
                      if (k.equals(arr[2])) {
                          subGradePoints.set(tempCount, arr[6]);
                          tempFlag = true;
                          break;
                      }
                      tempCount++;
                  }
                  if (!tempFlag) {
                      courseCodes.add(arr[2]);
                      subGradePoints.add(arr[6]);
                  }
              }
          }
          else {
              if(b)
              {
                  bufferedWriter.write(prevSr+",");
                  String [] temp = {"CL118","CL217","CS118","CS217","EE227","EL227","MT104","MT119","MT224","SL101","SS101","SS122","EE182"};
                  for(String k : temp) {
                      if (courseCodes.contains(k)) {
                          int t = courseCodes.indexOf(k);
                          bufferedWriter.write(subGradePoints.get(t) + ",");
                      }else
                      {
                          bufferedWriter.write(" " + ",");
                      }
                  }
                  bufferedWriter.write(prevCgpa+","+prevWarning + "," + gradePoint);
                  bufferedWriter.write("\n");
              }
              subGradePoints.clear();
              courseCodes.clear();
              if(arr[2].equals("CS201"))
              {
                  gradePoint = arr[6];
                  b = true;
              }
              else {
                  b= false;

                  int tempCount = 0;
                  boolean tempFlag = false;
                  for (String k : courseCodes) {
                      if (k.equals(arr[2])) {
                          subGradePoints.set(tempCount, arr[6]);
                          tempFlag = true;
                          break;
                      }
                      tempCount++;
                  }
                  if (!tempFlag) {
                      courseCodes.add(arr[2]);
                      subGradePoints.add(arr[6]);
                  }
              }
              counter = tempSr;
          }
          line = bufferedReader.readLine();
      }
      bufferedReader.close();
      fileReader.close();
      bufferedWriter.close();
      fileReader.close();
  }

    public static void trainingdataset() throws IOException {
            FileWriter filewriter=new FileWriter("trainigDataset.csv");
            BufferedWriter B1=new BufferedWriter(filewriter);

             FileWriter filewriter1=new FileWriter("testingDataset.csv");
             BufferedWriter B2=new BufferedWriter(filewriter1);

            FileReader fileReader = new FileReader("newDataset.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        B1.write("SrNo"+"," + "CL118"+"," + "CL217"+"," + "CS118"+"," + "CS217"+","  + "EE227"+"," + "EL227"+"," + "MT104"+"," + "MT119"+","+"MT224"+"," + "SL101"+","  + "SS101"+","   + "SS122"+"," +"EE182"+"," + "cgpa"+"," + "warnings"+"," + "CS201");
        B1.write("\n");
        B2.write("SrNo"+"," + "CL118"+"," + "CL217"+"," + "CS118"+"," + "CS217"+","  + "EE227"+"," + "EL227"+"," + "MT104"+"," + "MT119"+","+"MT224"+"," + "SL101"+","  + "SS101"+","   + "SS122"+"," +"EE182"+"," + "cgpa"+"," + "warnings"+"," + "CS201");
        B2.write("\n");

            String line=bufferedReader.readLine();
            line= bufferedReader.readLine();
            int counter=0;
            while(line!=null)
            {
                if(counter<250)
                {
                    String [] arr=line.split(",");
                    for(int i=0;i< arr.length;i++)
                    {
                        B1.write(arr[i]+",");
                    }
                    B1.write("\n");
                }
                else
                {
                    String [] arr=line.split(",");
                    for(int i=0;i< arr.length;i++)
                    {
                        B2.write(arr[i]+",");
                    }
                    B2.write("\n");
                }
                line = bufferedReader.readLine();
                counter++;
            }

        bufferedReader.close();
        fileReader.close();
        B1.close();
        B2.close();
        filewriter.close();
        filewriter1.close();

    }




}