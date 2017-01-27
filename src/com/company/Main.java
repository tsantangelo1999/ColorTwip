package com.company;

import java.util.Scanner;
import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        String response = "";
        while(!(response.equalsIgnoreCase("triplet") || response.equalsIgnoreCase("hex") || response.equalsIgnoreCase("name")))
        {
            System.out.println("Are you going to enter an RGB TRIPLET, a color HEX code, or a color with a defined NAME?");
            response = input.nextLine();
            if(response.equalsIgnoreCase("rgb triplet"))
                response = "triplet";
            if(!(response.equalsIgnoreCase("triplet") || response.equalsIgnoreCase("hex") || response.equalsIgnoreCase("name")))
                System.out.println("Invalid input. (TRIPLET, HEX, NAME)");
        }
        if(response.equalsIgnoreCase("triplet"))
        {
            System.out.println("Insert value for red.");
            int red = getTripletValue();
            System.out.println("Insert value for green.");
            int green = getTripletValue();
            System.out.println("Insert value for blue.");
            int blue = getTripletValue();
            String redVal = Integer.toHexString(red).toUpperCase();
            String greenVal = Integer.toHexString(green).toUpperCase();
            String blueVal = Integer.toHexString(blue).toUpperCase();
            if(redVal.length() < 2)
                redVal = "0" + redVal;
            if(greenVal.length() < 2)
                greenVal = "0" + greenVal;
            if(blueVal.length() < 2)
                blueVal = "0" + blueVal;
            String hex = redVal + greenVal + blueVal;
            String name = null;
            try
            {
                name = findColor(hex);
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Color table not found, name will not be shown if it exists");
            }
            System.out.print("The hex value of your color is " + hex + ".");
            if(name != null)
                System.out.println(" The name of your color is " + name + ".");
        }
        if(response.equalsIgnoreCase("hex"))
        {
            System.out.println("Insert your color's hex code.");
            String hex;
            top: while(true)
            {
                hex = input.nextLine().toUpperCase();
                if(hex.length() != 6)
                {
                    System.out.println("Hex values must be 6 characters.");
                    continue;
                }
                for(int i = 0; i < hex.length(); i++)
                {
                    char c = hex.charAt(i);
                    if(!((c >= 48 && c <= 57) || (c >= 65 && c <= 70)))
                    {
                        System.out.println("Hex values must consist of numbers 0-9 and letters A-F.");
                        continue top;
                    }
                }
                break;
            }
            String trip = generateTriplet(hex);
            String name = null;
            try
            {
                name = findColor(hex);
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Color table not found, name will not be shown if it exists");
            }
            System.out.print("The RGB triplet of your color is " + trip + ".");
            if(name != null)
                System.out.println(" The name of your color is " + name + ".");
        }
        if(response.equalsIgnoreCase("name"))
        {
            System.out.println("What is the name of your color?");
            String name = input.nextLine();
            FileReader fr = null;
            try
            {
                fr = new FileReader("colors2.txt");
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Color table not found. Aborting...");
                System.exit(0);
            }
            Scanner table = new Scanner(fr);
            String hex = null;
            while(table.hasNextLine())
            {
                if(table.nextLine().equalsIgnoreCase(name))
                {
                    hex = table.nextLine();
                    break;
                }
            }
            table.close();
            if(hex == null)
            {
                System.out.println("That color is not one of the defined colors.");
                System.exit(0);
            }
            String trip = generateTriplet(hex);
            System.out.println("The RGB triplet of your color is " + trip + ". The hex value of your color is " + hex + ".");
        }
    }

    public static int getTripletValue()
    {
        Scanner input = new Scanner(System.in);
        int val = -1;
        while(val < 0 || val > 255)
        {
            try
            {
                val = Integer.parseInt(input.nextLine());
            }
            catch(Exception e)
            {
                System.out.println("Values must be between 0 and 255 inclusive.");
                continue;
            }
            if(val < 0 || val > 255)
                System.out.println("Values must be between 0 and 255 inclusive.");
        }
        return val;
    }
    
    public static String generateTriplet(String hex)
    {
        String r = hex.substring(0, 2);
        String g = hex.substring(2, 4);
        String b = hex.substring(4);
        int red = (r.charAt(0) < 64 ? r.charAt(0) - 48 : r.charAt(0) - 55) * 16 + (r.charAt(1) < 64 ? r.charAt(0) - 48 : r.charAt(1) - 55);
        int green = (g.charAt(0) < 64 ? g.charAt(0) - 48 : g.charAt(0) - 55) * 16 + (g.charAt(1) < 64 ? g.charAt(0) - 48 : g.charAt(1) - 55);
        int blue = (b.charAt(0) < 64 ? b.charAt(0) - 48 : b.charAt(0) - 55) * 16 + (b.charAt(1) < 64 ? b.charAt(0) - 48 : b.charAt(1) - 55);
        return "(" + red + ", " + green + ", " + blue + ")";
    }

    public static String findColor(String hex) throws FileNotFoundException
    {
        FileReader fr = new FileReader("colors3.txt");
        Scanner text = new Scanner(fr);
        String name = null;
        while(text.hasNextLine())
        {
            if(text.nextLine().equals(hex))
            {
                name = text.nextLine();
                break;
            }
        }
        text.close();
        return name;
    }
}
