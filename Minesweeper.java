package misc;

import java.util.ArrayList;
import java.util.Random;



public class Minesweeper {
	
	public static class cell{
		
		private Coordinate _c;
		private String _type;
		private int _num;
		private int mark = 0;
		
		public cell(Coordinate c,String type,int num){
			_c = c;
			_type = type;
			_num = num;
		}
		
		public Coordinate get_coord(){
			return _c;
		}
		public String get_type(){
			return _type;
		}
		public int get_num(){
			return _num;
		}
		public String toString(){
			return _type + "/"+ _num;
		}
		private void set_mark(int x){
			mark = x;
		}
		
	}
	
	//NxN grid
	private int N;
	
	//Number of mines
	private int M;
	
	//Inner board,this will hold the cells,this will not be shown to the player
	private ArrayList<ArrayList<cell>> inner_board = new ArrayList<>();
	
	//Board that will be shown to player
	private ArrayList<ArrayList<String>> board = new ArrayList<>();
	
	//number non bomb cells
	private int found = 0;
	
	public Minesweeper(int n,int m){
		
		N = n;
		M = m;
		//fill the inner_board with blanks
		for(int i = 0;i < n;i++){
			inner_board.add(new ArrayList<cell>());
			for(int j = 0;j < n;j++){
				Coordinate c = new Coordinate(i,j);
				cell blank = new cell(c,"blank",0);
				inner_board.get(i).add(blank);
			}
		}
		
		//start the board
		for(int i = 0;i < n;i++) {
			board.add(new ArrayList<String>());
			for(int j = 0;j < n;j++){
				board.get(i).add("?");
			}
		}
		
		
		this.place_mines();
		this.complete_board();
		
	}
	
	public boolean check_win() {
		if(found == N*N-M) {
			return true;
		}
		return false;
	}
	
	//TO DO:
	//1)mark cell/unmark cell
	//2)print board
	//3)expose cell:can be:numbered -->number exposed;blank --> this cell and all adj blanks up to the closest numbered
	//cells are exposed,bomb:foi de base f no chat
	
	//3)
	public boolean expose_cell(int x,int y){
		
		if(!board.get(x).get(y).equals("?")){
			System.out.println("This cell is alredy exposed");
			return false;
		}
		
		cell exp = inner_board.get(x).get(y);
		if(exp.get_type().equals("number")){
			board.get(x).remove(y);
			board.get(x).add(y,""+exp.get_num());
			return true;
		}
		if(exp.get_type().equals("bomb")){
			System.out.println("FOI DE BASE,F NO CHAT");
			return false;
		}
		else {//blank type
			board.get(x).remove(y);
			board.get(x).add(y," ");
			
			//exposing upper cells:
			
			//right
			cell curr = null;
			for(int i = x;i >= 0;i--){
				for(int j = y;j < N;j++){
					cell c  = inner_board.get(i).get(j);
					curr = c;
					if(c.get_type().equals("bomb")) {
						break;
					}
					if(c.get_type().equals("number")){
						board.get(i).remove(j);
						board.get(i).add(j,""+c.get_num());
						break;
					}
					else if(c.get_type().equals("blank")){
						board.get(i).remove(j);
						board.get(i).add(j," ");
					}
				}
				if(!curr.get_type().equals("blank") && i != x) {
					break;
				}
				
			}
			//left
			curr = null;
			for(int i = x;i >= 0;i--){
				for(int j = y;j >= 0;j--){
					cell c  = inner_board.get(i).get(j);
					curr = c;
					if(c.get_type().equals("bomb")) {
						break;
					}
					if(c.get_type().equals("number")){
						board.get(i).remove(j);
						board.get(i).add(j,""+c.get_num());
						break;
					}
					else if(c.get_type().equals("blank")){
						board.get(i).remove(j);
						board.get(i).add(j," ");
					}
				}
				if(!curr.get_type().equals("blank") && i != x) {
					break;
				}
				
			}
			//end of upper cells
			
			//exposing lower cells:
			
			//right
			curr = null;
			for(int i = x;i < N;i++){
				for(int j = y;j < N;j++){
					cell c  = inner_board.get(i).get(j);
					curr = c;
					if(c.get_type().equals("bomb")) {
						break;
					}
					if(c.get_type().equals("number")){
						board.get(i).remove(j);
						board.get(i).add(j,""+c.get_num());
						break;
					}
					else if(c.get_type().equals("blank")){
						board.get(i).remove(j);
						board.get(i).add(j," ");
					}
				}
				if(curr.get_type().equals("blank") && i != x) {
					break;
				}
			}
			
			//left
			curr = null;
			for(int i = x;i < N;i++){
				for(int j = y;j >= 0;j--){
					cell c  = inner_board.get(i).get(j);
					curr = c;
					if(c.get_type().equals("bomb")) {
						break;
					}
					if(c.get_type().equals("number")){
						board.get(i).remove(j);
						board.get(i).add(j,""+c.get_num());
						break;
					}
					else if(c.get_type().equals("blank")){
						board.get(i).remove(j);
						board.get(i).add(j," ");
					}
				}
				if(curr.get_type().equals("blank") && i != x) {
					break;
				}
			}
		}
		found++;
		if(found == N*N-M) {
			System.out.println("WIN!");
		}
		return true;
		
	}
	
	//2)
	public void print_board(){
		
		for(int i = 0;i < board.size();i++){
			System.out.println(board.get(i));
		}
		
	}
	
	
	//1)
	public boolean mark_cell(int i,int j){
		
		if(i < 0 || j < 0 || i >= N || j >= N) {
			System.out.println("Invalid position");
			return false;
		}
		if(!board.get(i).get(j).equals("?")){
			System.out.println("You cant mark exposed cells");
			return false;
		}
		board.get(i).remove(j);
		board.get(i).add(j,"*");
		//change in the inner
		inner_board.get(i).get(j).set_mark(1);//marking the cell
		
		return true;
	}
	
	public boolean unmark_cell(int i,int j) {
		if(i < 0 || j < 0 || i >= N || j >= N) {
			System.out.println("Invalid position");
			return false;
		}
		if(!board.get(i).get(j).equals("*")){
			System.out.println("This cell is not marked");
			return false;
		}
		board.get(i).remove(j);
		board.get(i).add(j,"?");
		
		inner_board.get(i).get(j).set_mark(0);//unmark cell
		
		return true;
	}
	
	
	//starting the game
	public ArrayList<Coordinate> generate_pos(){
		
		ArrayList<Coordinate> all = new ArrayList<>();

		while(all.size() != M) {
			Random a = new Random();
			Random b = new Random();
			int x = a.nextInt(N);
			int y = b.nextInt(N);
			
			Coordinate c = new Coordinate(x,y);
			if(!in_all(all,c)){
				all.add(c);
			}
		}
		//System.out.println(all);
		return all;
		
	}
	
	public boolean in_all(ArrayList<Coordinate> all,Coordinate p){
		for(int i = 0;i < all.size();i++) {
			if(all.get(i).equals(p)){
				return true;
			}
		}
		return false;
		
	}
	
	public void show_inner(){
		for(int i = 0;i < inner_board.size();i++) {
			System.out.println(inner_board.get(i));
		}
	}
	
	public void place_mines() {
		ArrayList<Coordinate> mines = new ArrayList<>();
		mines.addAll(this.generate_pos());
		
		
		
		
		for(int i = 0;i < mines.size();i++) {
			
			cell bomb = new cell(mines.get(i),"bomb",0);
			int x = mines.get(i).get_x();
			int y = mines.get(i).get_y();
			
			//placing the bomb in the coordinate
			inner_board.get(x).remove(y);
			inner_board.get(x).add(y,bomb);		
		}	
	}
	
	public void complete_board(){
		
		for(int i = 0;i < inner_board.size();i++){
			for(int j = 0;j < inner_board.get(i).size();j++){
				cell c = inner_board.get(i).get(j);
				if(c.get_type().equals("bomb")) {
					continue;
				}
				int num = num_bombs_around(c);
				//System.out.println(num);
				if(num != 0){//"transform" the blank in a numbered
					cell number = new cell(c.get_coord(),"number",num);
					inner_board.get(i).remove(j);
					inner_board.get(i).add(j,number);
				}
				/*else {
					cell number = new cell(c.get_coord(),"number",num);
					inner_board.get(i).remove(j);
					inner_board.get(i).add(j,number);
				}*/
			}
		}
		
	}
	
	public int num_bombs_around(cell c){
		
		int num = 0;
		
		int x = c.get_coord().get_x();
		int y = c.get_coord().get_y();
		
		if(x-1 >= 0 && x+1 <= N-1){
			if(y-1 >= 0 && y+1 <= N-1){
				for(int i = x-1;i <= x+1;i++){
					for(int j = y-1;j <= y+1;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
			
			if(y-1 >= 0 && y+1 >= N){
				for(int i = x-1;i <= x+1;i++){
					for(int j = y-1;j < N;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
			else {
				for(int i = x-1;i <= x+1;i++){
					for(int j = 0;j <= y+1;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
		}
		if(x-1 >= 0 && x+1 >= N){
			if(y-1 >= 0 && y+1 <= N-1){
				for(int i = x-1;i < N;i++){
					for(int j = y-1;j <= y+1;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
			
			if(y-1 >= 0 && y+1 >= N){
				for(int i = x-1;i < N;i++){
					for(int j = y-1;j < N;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
			else {
				for(int i = x-1;i < N;i++){
					for(int j = 0;j <= y+1;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
		}
		else {
			if(y-1 >= 0 && y+1 <= N-1){
				for(int i = 0;i <= x+1;i++){
					for(int j = y-1;j <= y+1;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
			
			if(y-1 >= 0 && y+1 >= N){
				for(int i = 0;i <= x+1;i++){
					for(int j = y-1;j < N;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
			else {
				for(int i = 0;i <= x+1;i++){
					for(int j = 0;j <= y+1;j++){
						if(inner_board.get(i).get(j).get_type().equals("bomb")){
							num++;
						}
					}
				}
				return num;
			}
		}
		
	}
	//
	
}
