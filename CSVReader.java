import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//class for a basic CSV file reader.
//this CSV reader assumes that the first line of every .csv file are the headings, so they will not be treated as data
//since this is a very basic CSV reader, in order to create objects from CSV data, we will need to extend this class to more specialized classes.

/**
 * This class provides basic functionality for reading and manipulating CSV files.
 * It includes methods to read, print, add, delete, and modify lines in a CSV file.
 * Additionally, it handles the conversion of data types such as lists, sentences, booleans, and integers.
 */

public class CSVreader{
    public static void main(String[] a){
        System.out.println("test");
    }
     /**
     * Returns an array of strings, with each string representing a line from the CSV file (excluding the header).
     *
     * @param filepath The path of the CSV file to read.
     * @return An array of strings representing lines from the CSV file.
     */
    public static String[] getLines(String filepath){
        List<String> stringList = new ArrayList<>();
        Scanner sc = null;
        try{
            sc = new Scanner(new File(filepath));
        }catch(IOException e){
            e.printStackTrace();
        }
        while (sc.hasNextLine()){
            stringList.add(sc.nextLine());
        }
        sc.close();
        stringList.remove(0); //remove the header
        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * Returns an array of strings, including the header and each line from the CSV file.
     *
     * @param filepath The path of the CSV file to read.
     * @return An array of strings representing lines from the CSV file with the header included.
     */
    public static String[] getLinesWithHeader(String filepath){
        List<String> stringList = new ArrayList<>();
        Scanner sc = null;
        try{
            sc = new Scanner(new File(filepath));
        }catch(IOException e){
            e.printStackTrace();
        }
        while (sc.hasNextLine()){
            stringList.add(sc.nextLine());
        }
        sc.close();
        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * Prints the contents of the CSV file to the console.
     *
     * @param filepath The path of the CSV file to print.
     */
    public static void printData(String filepath){
        String[] lines = getLines(filepath);
        int index = 1;
        for(String line : lines){
            System.out.printf("Item %d: %s \n", index, line);
            index++;
        }
    }

     /**
     * Appends a line at the bottom of the CSV file, used to add a data value into the CSV.
     *
     * @param filepath The path of the CSV file to which the line will be added.
     * @param line     The line to be added to the CSV file.
     */
    public static void addLine(String filepath, String line){
        FileWriter fw = null;
        try{
            fw = new FileWriter(filepath, true);
            fw.write(line + "\n");
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Deletes a line from the CSV file based on the first entry (usually the name of the item).
     *
     * @param filepath    The path of the CSV file from which the line will be deleted.
     * @param firstEntry  The value of the first entry to identify the line to be deleted.
     */
    public static void deleteLine(String filepath, String firstEntry){
        String[] lines = getLinesWithHeader(filepath);
        int index = 0;
        int indexToDelete = -1;
        for(String line : lines){
            if(line.startsWith(firstEntry, 0)){
                indexToDelete = index;
            }
            index++;
        }
        if(indexToDelete == -1){
            System.out.println("Cannot find item.");
            return;
        }
        FileWriter fw = null;
        try{
            fw = new FileWriter(filepath, false);
            for(int x = 0; x<lines.length; x++){
                if(x != indexToDelete){
                    fw.write(lines[x] + "\n");   
                }
            }
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }   

    /**
     * Modifies a line in the CSV file based on the first entry.
     *
     * @param filepath    The path of the CSV file in which the line will be modified.
     * @param firstEntry  The value of the first entry to identify the line to be modified.
     * @param newLine     The new line that will replace the existing line.
     */
    public static void modifyLine(String filepath, String firstEntry, String newLine){
        String[] lines = getLinesWithHeader(filepath);
        int index = 0;
        int indexToModify = -1;
        for(String line : lines){
            if(line.startsWith(firstEntry, 0)){
                indexToModify = index;
            }
            index++;
        }
        if(indexToModify == -1){
            System.out.println("Cannot find item.");
            return;
        }
        FileWriter fw = null;
        try{
            fw = new FileWriter(filepath, false);
            for(int x = 0; x<lines.length; x++){
                if(x == indexToModify){
                    fw.write(newLine + "\n");   
                }else{
                    fw.write(lines[x] + "\n");   
                }
            }
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Parses a string representing a list, e.g., "[BRANDON|CALVIN]", and returns an array of strings {"BRANDON", "CALVIN"}.
     *
     * @param stringList The string representation of a list.
     * @return An array of strings representing the list.
     */
    //some csv data can contain lists. for instance, the camp.csv file 
    //contains data fields that might be list of values: Attendees, Commitees, etc
    //this takes care of that by parsing a string such as "[BRANDON|CALVIN]"
    //and returning a list of Strings: {"BRANDON", "CALVIN"}

    public static String[] stringToList(String stringList){
        if(stringList.equals("[]")){
            String[] empty = new String[0];
            return empty;
        }
        if(!(stringList.startsWith("[") && stringList.endsWith("]"))){
            System.out.println("String must be contained within [] and must be seperated by |. " + stringList);
            return null;
        }
        stringList = stringList.substring(1, stringList.length()-1);
        return stringList.split("\\|", -1);
    }   

    /**
     * Converts an array of strings into a string representing a list, e.g., {"BRANDON", "CALVIN"} to "[BRANDON|CALVIN]".
     *
     * @param listOfStrings The array of strings to be converted into a list.
     * @return A string representing the list.
     */
    public static String listToString(String[] listOfStrings){
        if(listOfStrings.length == 0){
            return "[]";
        }
        StringBuilder str = new StringBuilder("[");
        for(String value : listOfStrings){
            str.append(value + "|");
        }
        str.deleteCharAt(str.length()-1);
        str.append("]");
        return str.toString();
    }
    
    /**
     * Replaces commas in a string with a special pattern "%^&".
     *
     * @param str The input string.
     * @return The string with commas replaced by "%^&".
     */
    //sentences are hard to include in a .csv, because they may contain commas
    //which causes delimiter error. in order to store sentences in CSV's 
    //we convert all commas into another lesser-known char pattern while writing
    //and convert them back into commas while reading. sloppy but gets the job done
    public static String removeCommas(String str){
        return str.replace(",", "%^&");
    }

    /**
     * Replaces the special pattern "%^&" with commas in a string.
     *
     * @param str The input string with the special pattern.
     * @return The string with "%^&" replaced by commas.
     */
    public static String getCommas(String str){
        return str.replace("%^&", ",");
    }

    /**
     * Converts a string representing "Yes" or "No" to a boolean value.
     *
     * @param str The input string ("Yes" or "No").
     * @return The boolean value corresponding to the input string.
     */
    public static boolean toBool(String str){
        if(str.equals("Yes")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Converts a boolean value to a string ("Yes" or "No").
     *
     * @param bool The boolean value to be converted.
     * @return The string representation of the boolean value.
     */
    public static String boolToStr(boolean bool){
        return bool ? "Yes" : "No";
    }

    /**
     * Converts a string to an integer.
     *
     * @param str The input string representing an integer.
     * @return The integer value corresponding to the input string.
     */
    public static int toInt(String str){
        return Integer.parseInt(str);
    }

    /**
     * Converts an integer to a string.
     *
     * @param integer The integer value to be converted.
     * @return The string representation of the integer value.
     */
    public static String intToStr(int integer){
        return Integer.toString(integer);
    }
}
