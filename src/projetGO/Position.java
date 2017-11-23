package projetGO;

public class Position {

	private int x;
	private int y;
	
	boolean Equals(Position a, Position b){
		if(a.x==b.x && a.y==b.y){
			return true;
		}
		return false;
		
	}
	
	Position(int x, int y){
		this.x=x;
		this.y=y;
	}
}
