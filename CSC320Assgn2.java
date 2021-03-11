/*
course: CSC320
project: Assignment 2
date: Mar. 8, 2020
author: Josiah Luikham
purpose: Finding the optimal alignment of two Gene Sequences
 */

import java.util.Scanner;
class seqAlign{
    char[] s1, s2;
    int[][] table;
    int width;
    int height;
    seqAlign(char[] s1, char[] s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    
    void makeTable() {
        int penalty;
        this.width = s1.length+1; //the table is always one greater in width and height than the lengths of the strings
        this.height = s2.length+1;
        table = new int[height][width];
        for( int k = width + height - 2 ; k >= 0; k-- ) { //this loop iterates along the diagonals starting at the bottom right and 
            for( int j = k ; j >= 0 ; j-- ) {             //working up through the array
                int i = k - j;
                if( i < height && j < width ) { //makes sure the i and j values arent out of bounds
                    if (j == width-1) {    //if at the right edge of the table, then the value automatically equals the formula
                        table[i][j] = 2*(height-1-i);
                    }
                    else if (i == height-1) { //if at the bottom edge of the table, then the value automatically equals the formula
                        table[i][j] = 2*(width-1-j);
                    }
                    else {   //penalty = 1 if the j value of the string does not match the i value of the other string
                        if (s1[j] == s2[i])
                            penalty = 0;
                        else
                            penalty = 1;
                        table[i][j] = Math.min(table[i+1][j+1] + penalty, table[i+1][j]+2); //the entry is the min of the value to the right
                        table[i][j] = Math.min(table[i][j], table[i][j+1]+2);             //and below + penalty, the value to the right + 2
                    }                                                                     //and the value below + 2
                }
            }
        }
        //print out the table
        for (int i = 0; i < height; i++) { //this equals to the row in our matrix.
            for (int j = 0; j < width; j++) { //this equals to the column in each row.
                System.out.printf("%5d", table[i][j]);
            }
            System.out.println(); //change line on console as row comes to end in the matrix.
        }
    }
    void optSeq() {
        char[] opt1 = new char[Math.max(s1.length, s2.length)*2]; //create two new arrays to store the optimal alignments make the size
        char[] opt2 = new char[Math.max(s1.length, s2.length)*2]; //the larger of the two strings length * 2 to be safe
        int i = 0, j = 0, counter = 0;
        while (i < height-1 && j < width-1) { //match the value in the table to the item to the right and below, the item to the right, or 
            if (table[i][j] == table[i][j+1]+2){ //the item below. if to the right and below, add the two values of the strings at the i and
                opt1[counter] = s1[j];          //j values to the alignment arrays, if to the right or below, add on of the values t one of
                opt2[counter] = '_';            //the alignment arrays, and a _ to the other
                counter++;
                j++;
            }
            else if (table[i][j] == table[i+1][j]+2){
                opt1[counter] = '_';
                opt2[counter] = s2[i];
                counter++;
                i++;
            }
            else {
                opt1[counter] = s1[j];
                opt2[counter] = s2[i];
                counter++;
                i++;
                j++;
            }
        }
        if (i == height-1 && j < width-1) {  //if the path leads to the bottom edge right edge of the table, these statements handle it
            while (j < width-1) {
                opt1[counter] = s1[j];
                opt2[counter] = '_';
                counter++;
                j++;
            }
        }
        if (i < height-1 && j == width-1) {
            while (i < height-1) {
                opt1[counter] = '_';
                opt2[counter] = s2[i];
                counter++;
                i++;
            }
        }
        System.out.println();
        System.out.println("the optimal alignment for these sequences is:");
                
        for (int k = 0; k < opt1.length; k++) //print out the sequences
            System.out.print(opt1[k] + " ");
        System.out.println();
        for (int k = 0; k < opt2.length; k++)
            System.out.print(opt2[k]+ " ");
    }
    
    
}

public class CSC320Assgn2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        boolean cont = true;
        String check = "AGCT";
        
        String s1 = "";
        String s2 = "";
        
        String quit = "";
        
        do {
            while (cont) {//loop will repeat until user enters a valid entry and cont is not set to true
                cont = false;
                System.out.print("Enter a string of letters A,G,C, or T: ");
                s1 = in.nextLine();
                s1 = s1.toUpperCase();
                
                for (int i = 0; i < s1.length(); i++) { //checks if the entered string only consists of A,G,C, or T
                    if (!check.contains(s1.charAt(i)+"")) {
                        System.out.println("invalid entry");
                        cont = true; 
                        break;
                    }
                }
            }
            
            cont = true;
            while (cont) {
                cont = false;
                System.out.print("Enter a string of letters A,G,C, or T: ");
                s2 = in.nextLine();
                s2 = s2.toUpperCase();
                for (int i = 0; i < s2.length(); i++) {
                    if (!check.contains(s2.charAt(i)+"")) {
                        System.out.println("invalid entry");
                        cont = true;
                        break;
                    }
                }
            }
            cont = true;
            
            System.out.println();
            
            char[] a = new char[s1.length()]; //put strings into arrays of chars
            for (int i = 0; i < s1.length(); i++) 
                a[i] = s1.charAt(i);
            
            char[] b = new char[s2.length()];
            for (int i = 0; i < s2.length(); i++) 
                b[i] = s2.charAt(i);
                   
            seqAlign sa = new seqAlign(a, b); //create object that aligns sequences
            sa.makeTable(); //make the number table
            sa.optSeq(); //use the number table to get the optimal alignment sequence
            
            System.out.println();
            System.out.print("Quit or Continue?: ");
            quit = in.next().toLowerCase();
            
            in.nextLine();
            System.out.println();
        } while (quit.charAt(0) != 'q');
    }    
}
