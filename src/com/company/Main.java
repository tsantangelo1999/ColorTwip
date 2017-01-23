package com.company;

import java.util.Scanner;
import java.io.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Scanner input = new Scanner(System.in);
        FileReader fr = new FileReader("colors.txt");
        FileWriter fw = new FileWriter("colors2.txt");
        PrintWriter pw = new PrintWriter(fw);
        Scanner text = new Scanner(fr);
        text.useDelimiter("\\s+");
        while(text.hasNext())
        {
            pw.println(text.next() + "\t");
        }
        pw.close();
        fw.close();
    }
}
