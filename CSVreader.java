import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//class for a basic CSV file reader.
//this CSV reader assumes that the first line of every .csv file are the headings.
//since this is a very basic CSV reader, in order to create objects from CSV data, i assume we will be extedning this class to a class that can do that.

public class CSVreader{
    //run this function to try the CSV reader.
    public static void main(String[] a) throws IOException{
        CSVreader.printData("data/camps.csv");
    }

    //returns an array of strings, with each string a line from the csv file. excludes the headings.
    public static String[] getLines(String filepath) throws IOException{
        List<String> stringList = new ArrayList<>();
        Scanner sc = new Scanner(new File(filepath));
        while (sc.hasNextLine()){
            stringList.add(sc.nextLine());
        }
        sc.close();
        stringList.remove(0); //remove the header
        return stringList.toArray(new String[stringList.size()]);
    }

    //prints the contents of the CSV file.
    public static void printData(String filepath) throws IOException{
        String[] lines = getLines(filepath);
        int index = 1;
        for(String line : lines){
            System.out.printf("Item %d: %s \n", index, line);
            index++;
        }
    }

    //appends a line at the bottom of the CSV file.
    public static void addLine(String filepath, String line) throws IOException{
        FileWriter fw = new FileWriter(filepath, true);
        fw.write(line + "\n");
        fw.close();
    }

    //deletes a line according to the first entry of the line.
    public static void deleteLine(String filepath, String firstEntry) throws IOException{
        String[] lines = getLines(filepath);
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
        FileWriter fw = new FileWriter(filepath, false);
        for(int x = 0; x<lines.length; x++){
            if(x != indexToDelete){
                fw.write(lines[x] + "\n");   
            }
        }
        fw.close();
    }   

    //change a line in a csv file according to the first entry
    public static void modifyLine(String filepath, String firstEntry, String newLine) throws IOException{
        String[] lines = getLines(filepath);
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
        FileWriter fw = new FileWriter(filepath, false);
        for(int x = 0; x<lines.length; x++){
            if(x == indexToModify){
                fw.write(newLine + "\n");   
            }else{
                fw.write(lines[x] + "\n");   
            }
        }
        fw.close();
    }
}