package misc;


public class Coordinate {
	
	private int _x;//row
	private int _y;//column
	
	public Coordinate(int x,int y){
		_x = x;
		_y = y;
	}
	
	public int get_x(){
		return _x;
	}
	public int get_y() {
		return _y;
	}	
	
	public boolean equals(Coordinate c) {
		if(c.get_x() == _x && c.get_y() == _y) {
			return true;
		}
		return false;
	}
	
	public String toString(){
		return "("+_x + "," + _y +")";
	}
}
