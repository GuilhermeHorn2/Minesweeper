package misc;

import java.util.ArrayList;
import java.util.Scanner;

import misc.Minesweeper.cell;

public class main_misc2 {
	
	
	
	public static void main(String[] args) {
	//(a+b)%k = ((a % k)+(b % k)) % k    || ||
	
	
		play_Minesweeper(8,10);
		/*Minesweeper a = new Minesweeper(8,10);
		
		a.show_inner();
        a.expose_cell(5, 0);
        a.print_board()*/
	
	}	
	private static void play_Minesweeper(int n,int m){
		
		Minesweeper a = new Minesweeper(n,m);
		
		int rounds = 0;
		while(true){
			a.print_board();
			System.out.println("Select a cell:");
			System.out.println("row(x):");
			Scanner sc1 = new Scanner(System.in);
			System.out.println("column(y):");
			Scanner sc2 = new Scanner(System.in);
			int x = sc1.nextInt();
			int y = sc2.nextInt();
			boolean exp = a.expose_cell(x, y);
			if(!exp) {
				break;
			}
			a.print_board();
			if(a.check_win()) {
				break;
			}
			
			
			//marking a cell:
            System.out.println("Digit 1 if you want to mark a cell:");
            Scanner sc3 = new Scanner(System.in);
            int mrk = sc3.nextInt();
            if(mrk == 1) {
            	System.out.println("Select a cell to mark:");
    			System.out.println("row(x):");
    			Scanner sc4 = new Scanner(System.in);
    			System.out.println("column(y):");
    			Scanner sc5 = new Scanner(System.in);
    			x = sc4.nextInt();
    			y = sc5.nextInt();
    			a.mark_cell(x,y);
            }
			if(rounds > 0) {
				//unmark a cell
				 System.out.println("Digit 1 if you want to unmark a cell:");
		            Scanner sc6 = new Scanner(System.in);
		            int unmrk = sc6.nextInt();
		            if(unmrk == 1) {
		            	System.out.println("Select a cell to unmark:");
		    			System.out.println("row(x):");
		    			Scanner sc7 = new Scanner(System.in);
		    			System.out.println("column(y):");
		    			Scanner sc8 = new Scanner(System.in);
		    			x = sc7.nextInt();
		    			y = sc8.nextInt();
		    			a.unmark_cell(x,y);
		            }

			}
            
            
			rounds++;
		}
		
	} 
	
}
