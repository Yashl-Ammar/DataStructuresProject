package com.company;

import java.util.ArrayList;

public class Tree {
    public static ArrayList<Tree> allTrees = new ArrayList<>();

    TreeNode<String> Root;

    public Tree(TreeNode<String> root) {
        Root = root;
    }

    public TreeNode<String> getRoot() {
        return Root;
    }

    public void setRoot(TreeNode<String> root) {
        Root = root;
    }

    public void preOrderdisplay(TreeNode<String> root,int index)
    {
        if (root == null)
        {
            return;
        }

        System.out.println(root.data);
        System.out.println("Index : "+index);
        System.out.println("Branches : ");
        for (String i : root.branches)
        {
            System.out.print(i + " ");
        }
        System.out.println();
        for (TreeNode<String> i : root.allNodes)
        {
            preOrderdisplay(i,index+1);
        }
    }


    public void postOrderdisplay(TreeNode<String> root,int index)
    {
        if (root == null)
        {
            return;
        }

        for (TreeNode<String> i : root.allNodes)
        {
            postOrderdisplay(i,index+1);
        }

        System.out.println(root.data);
        System.out.println("Index : "+index);
        System.out.println("Branches : ");
        for (String i : root.branches)
        {
            System.out.print(i + " ");
        }
        System.out.println();

    }

    public void inOrderdisplay(TreeNode<String> root,int index)
    {
        if (root == null)
        {
            return;
        }


        int j = 0;
        for (TreeNode<String> i : root.allNodes)
        {
            inOrderdisplay(i,index+1);
            if ( j== root.allNodes.size()/2)
            {
                System.out.println(root.data);
                System.out.println("Index : "+index);
                System.out.println("Branches : ");
                for (String k : root.branches)
                {
                    System.out.print(k + " ");
                }
                System.out.println();
            }
        }
    }


    public static void displayAllTrees()
    {
        int counter = 1;
        for (Tree i : allTrees)
        {
            System.out.println("Tree Number : " + counter);
            i.preOrderdisplay(i.getRoot(),0);
            for (int j = 0 ; j < 30 ; j++)
            {
                System.out.println();
            }
            counter++;
        }
    }

    public String predict(String [] arr , ArrayList<String> arrayList)
    {
        TreeNode<String> curr = Root;

        while (curr != null)
        {
            int index = arrayList.indexOf(curr.data);
            if (index == -1)
            {
                return curr.data;
            }
            else
            {
                boolean b = false;
                for (String i : curr.branches)
                {
                    if (i.equals(arr[index]))
                    {
                        int i1 = curr.branches.indexOf(i);
                        curr = curr.allNodes.get(i1);
                        b= true;
                        break;
                    }
                }
                if (!b)
                {
                    break;
                }
            }

        }
        return "-1";
    }
}
