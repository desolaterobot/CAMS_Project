import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//class for a basic CSV file reader.
//this CSV reader assumes that the first line of every .csv file are the headings, so they will not be treated as data
//since this is a very basic CSV reader, in order to create objects from CSV data, we will need to extend this class to more specialized classes.

public class CSVreader{
    //returns an array of strings, with each string a line from the csv file. excludes the headings.
    public static String[] getLines(String filepath){
        List<String> stringList = new ArrayList<>();
        try{
            Scanner sc = new Scanner(new File(filepath));
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

    //same as above but includes the header as well.
    public static String[] getLinesWithHeader(String filepath){
        List<String> stringList = new ArrayList<>();
        try{
            Scanner sc = new Scanner(new File(filepath));
        }catch(IOException e){
            e.printStackTrace();
        }
        while (sc.hasNextLine()){
            stringList.add(sc.nextLine());
        }
        sc.close();
        return stringList.toArray(new String[stringList.size()]);
    }

    //prints the contents of the CSV file.
    public static void printData(String filepath){
        String[] lines = getLines(filepath);
        int index = 1;
        for(String line : lines){
            System.out.printf("Item %d: %s \n", index, line);
            index++;
        }
    }

    //appends a line at the bottom of the CSV file. used to add a data value into a CSV.
    public static void addLine(String filepath, String line){
        try{
            FileWriter fw = new FileWriter(filepath, true);
        }catch(IOException e){
            e.printStackTrace();
        }
        fw.write(line + "\n");
        fw.close();
    }

    //DELETES a line according to the first entry of the line.
    //linear search through the lines of the CSV file, then removes the line with the given first entry (usually the name of the item)
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
        try{
            FileWriter fw = new FileWriter(filepath, false);
        }catch(IOException e){
            e.printStackTrace();
        }
        for(int x = 0; x<lines.length; x++){
            if(x != indexToDelete){
                fw.write(lines[x] + "\n");   
            }
        }
        fw.close();
    }   

    //MODIFY a line in a csv file according to the first entry
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
        try{
            FileWriter fw = new FileWriter(filepath, false);
        }catch(IOException e){
            e.printStackTrace();
        }
        for(int x = 0; x<lines.length; x++){
            if(x == indexToModify){
                fw.write(newLine + "\n");   
            }else{
                fw.write(lines[x] + "\n");   
            }
        }
        fw.close();
    }

    //some csv data can contain lists. for instance, the camp.csv file 
    //contains data fields that might be list of values: Attendees, Commitees, etc
    //this takes care of that by parsing a string such as "[BRANDON|CALVIN]"
    //and returning a list of Strings: {"BRANDON", "CALVIN"}

    public static String[] stringToList(String stringList){
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX" + stringList);
        if(stringList.equals("]")){
            return new String[0];
        }
        if(!(stringList.startsWith("[") && stringList.endsWith("]"))){
            System.out.println("String must be contained within [] and contain | as a delimiter.");
            return null;
        }
        stringList = stringList.substring(1, stringList.length()-1);
        return stringList.split("\\|");
    }   

    //this does the opposite
    public static String listToString(Iterable listOfStrings){
        System.out.println("start l2s");
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

    //sentences are hard to include in a .csv, because they may contain commas
    //which causes delimiter error. in order to store sentences in CSV's 
    //we convert all commas into another lesser-known char pattern while writing
    //and convert them back into commas while reading. sloppy but gets the job done
    public static String removeCommas(String str){
        return str.replace(",", "%^&");
    }

    public static String getCommas(String str){
        return str.replace("%^&", ",");
    }

    //convert booleans to "Yes"/"No" strings and vice versa
    public static boolean toBool(String str){
        if(str.equals("Yes")){
            return true;
        }else{
            return false;
        }
    }

    public static String boolToStr(boolean bool){
        return bool ? "Yes" : "No";
    }

    //convert string to int and vice versa
    public static int toInt(String str){
        return Integer.parseInt(str);
    }

    public static String intToStr(int integer){
        return Integer.toString(integer);
    }
}
