/*
 * Main Driver "LostPuppy.java"
 * TCSS 143.
 */
import java.util.*;
import java.io.*;

 /**
 * Singular Main Driver for txt files
 *
 * @author Drew Brown
 *
 * @version 10 May 2023
 */

public class LostPuppy {

    /**
    * the method main is the main driver method
    * @param String containing Args
    */
    public static void main(String[] Args){

        /** 
         * A 2D character array that represents the maze
        */
         char[][] myMaze;

        /** 
         * Array that will hold 2 values that represent the start location
         * and 2 more values that will hold the end location
         * Legend: This array will hold 2 pairs of values
         * ([0] = row for starting, [1] = column for starting)
         * ([2] =  row for ending, [3] = column for ending)
        */
         int[] myStartEndLocation;

        /** 
         * Scanner that reads input from the txt file
        */
        Scanner myInput = null;
       
        //try catch to open the txt file
        try {
           myInput = new Scanner(new File("MazeData1.txt"));
        }
        catch(Exception e){ 
           System.out.println("Could not open file");
           System.exit(0);              //end program
        }

        myMaze = getMaze(myInput);             //fills maze
        myStartEndLocation = getStartExitLocation(myMaze);

        int startRow = myStartEndLocation[0];  //grabs coordinates from array
        int startCol = myStartEndLocation[1];

        if(findPath(myMaze, startRow, startCol) == false){
            System.out.println("No Path Found!");
        }
        else{
            System.out.print(displayMaze(myMaze));
        }

    }

    /**
    * getMaze reads the input file and fills the characters into an array
    * @param Scanner theInput 
    * @return 2D array representing the maze
    */
    private static char[][] getMaze(Scanner theInput) { 
        String s = "";
        
        while(theInput.hasNextLine()){
            s += theInput.nextLine();
            s += "8";                        //this is to note when a line ends
        }
        
        String[] split = s.split("8"); //grab each line
        int totalRows = split.length;        //how many rows in the txt
        String indexed = split[0];
        int totalColumns = indexed.length(); //how many columns in the txt

        char[][] myMaze = new char[totalRows][totalColumns];

        for(int i = 0; i < totalRows; i++){
            for(int x = 0; x < totalColumns; x++){
                myMaze[i][x] = split[i].charAt(x);
            }
        }

        return myMaze;

    }

    /**
     * getStartExitLocation searches for 'S' and 'E' and places
     * coordinates in an integer array which is returned
     * 
     * @param theMaze Our 2D array that will be searched through
     * @return Returns an integer array holding the coordinates of both
     *         starting position and ending position
     */
    public static int[] getStartExitLocation(char[][] theMaze){
        int[] returnArr = new int[4];           //size 4
        for(int i = 0; i < theMaze.length; i++){
            for(int x = 0; x < theMaze[0].length; x++){
                if(theMaze[i][x] == 'S'){
                    returnArr[0] = i;
                    returnArr[1] = x;
                    theMaze[i][x] = '*';        //Place an astrick at the start location
                }
                else if(theMaze[i][x] == 'E'){
                    returnArr[2] = i;
                    returnArr[3] = x;
                }
            }
        }
        return returnArr;
    }

    /**
     * Recursively paths through 2D array until E is found
     * 
     * @param theMaze 2D char array representing the maze
     * @param startRow Integer representing row number
     * @param startColumn Integer representing column number
     * @return Returns true if E was found false if otherise
     */
    public static boolean findPath(char[][] theMaze, int startRow, int startColumn){
        boolean found = false;
        if(theMaze[startRow][startColumn] == 'E'){
            theMaze[startRow][startColumn] = '*';
            found = true;
        }
        
        if(found == false){
            if(canMove(theMaze, startRow - 1, startColumn)){ //North
                if(theMaze[startRow - 1][startColumn] != 'E'){
                    theMaze[startRow - 1][startColumn] = '*';
                }
                found = findPath(theMaze, startRow - 1, startColumn);
                if(found == false){
                    theMaze[startRow - 1][startColumn] = '-';
                }
            }
        }
        if(found == false){
            if(canMove(theMaze, startRow + 1, startColumn)){ //South
                if(theMaze[startRow + 1][startColumn] != 'E'){
                    theMaze[startRow + 1][startColumn] = '*';
                }
                found = findPath(theMaze, startRow + 1, startColumn);
                if(found == false){
                    theMaze[startRow + 1][startColumn] = '-';
                }
            }
        }
        if(found == false){
            if(canMove(theMaze, startRow, startColumn + 1)){ //East
                if(theMaze[startRow][startColumn + 1] != 'E'){
                    theMaze[startRow][startColumn + 1] = '*';
                }
                found = findPath(theMaze, startRow, startColumn + 1);
                if(found == false){
                    theMaze[startRow][startColumn + 1] = '-';
                }
            }
        }
        if(found == false){
            if(canMove(theMaze, startRow, startColumn - 1)){ //West
                if(theMaze[startRow][startColumn - 1] != 'E'){
                    theMaze[startRow][startColumn - 1] = '*';
                }
                found = findPath(theMaze, startRow, startColumn - 1);
                if(found == false){
                    theMaze[startRow][startColumn - 1] = '-';
                }
            }
        }
        
        return found;
    }

    /**
     * Checks if the given coordinates are available for a move
     * 
     * @param theMaze 2D Char array representing the main maze
     * @param row Row integer to check for move
     * @param column Column integer to check for move
     * @return Returns true if can move, false if cannot
     */
    public static boolean canMove(char[][] theMaze, int row, int column){
        boolean myReturn = false;
        int numRows = theMaze.length;
        int numColumns = theMaze[0].length;

        if((row >= 0 && row <= numRows) && (column >= 0 && column <= numColumns)){
            if(theMaze[row][column] == ' ' || theMaze[row][column] == 'E'){
                myReturn = true;
            }
        }

        return myReturn;
    }

    /**
     * displayMaze takes in a 2D and converts its content into a String
     * that is then printed in main when called
     * 
     * @param theMaze The variable that will represent the maze
     * @return Returns string that represents the Maze structure
     */
    private static String displayMaze(char[][] theMaze){
        int rows = theMaze.length;
        int columns = theMaze[0].length;
        String s = "";
        for(int i = 0; i < rows; i++){
            for(int x = 0; x < columns; x++){
                s += theMaze[i][x];
            }
            s += "\n";
        }
        return s;
    }

}