package nodal.view.util;

public class Position {
	private int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object other) {
		if(! (other instanceof Position))
			return false;
		Position p = (Position) other;
		return getX() == p.getX() && getY() == p.getY();
	}
	
	@Override
	public int hashCode() {
		return x + ((y+17)<<16);
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
}
