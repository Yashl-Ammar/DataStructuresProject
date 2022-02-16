package com.company;

public class GenericArrayList <T>{

    int currentSize;

    T [] ourArray ;

    public GenericArrayList() {
        currentSize = 0;
        ourArray = (T[])new Object[currentSize];
        for(T i : ourArray)
        {
            if (i != null)
            {
                i = null;
            }
        }
    }

    public GenericArrayList(int s) {
        currentSize = s;
        ourArray = (T[])new Object[s];
        for(T i : ourArray)
        {
            if (i != null)
            {
                i = null;
            }
        }
    }

    public void add(T obj)
    {

        boolean b = false;
        for(int i = 0 ; i < currentSize ; i++)
        {
            if(ourArray[i] == null)
            {
                ourArray[i] = obj;
                b = true;
                break;
            }
        }

        if(!b) {
            T[] tempArr = (T[]) new Object[currentSize];
            for (int i = 0 ; i < currentSize ; i++)
            {
                tempArr[i] = ourArray[i];
            }
            currentSize++;
            ourArray = (T[]) new Object[currentSize];

            for (int i = 0 ; i < currentSize ; i++)
            {
                if(i == currentSize-1)
                {
                    ourArray[i] = obj;
                }
                else
                    ourArray[i] = tempArr[i];
            }
        }
    }


    public void remove(int index)
    {
        if(index < currentSize) {
            ourArray[index] = null;
            for(int i = index ; i < currentSize-1; i++)
            {
                ourArray[i] = ourArray[i+1];
            }
            ourArray[currentSize-1] = null;
        }

    }



    public int size() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public T[] getOurArray() {
        return ourArray;
    }

    public void setOurArray(T[] ourArray) {
        this.ourArray = ourArray;
    }

    public T get(int index)
    {
        return ourArray[index];
    }
}