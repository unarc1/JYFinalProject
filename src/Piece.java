
public abstract class Piece
{ 
  static final int TEAM_BLACK = 0;
  static final int TEAM_RED = 1;
  final int team;
  
  Piece(int team) {this.team = team;}
  
  protected void move(Grid grid, Location start, Location dest, Cell first, Cell second, String image){
    Piece temp = first.getPiece();
    first.setPiece(second.getPiece());
    second.setPiece(temp);
    grid.setImage(start, grid.getImage(dest));
    grid.setImage(dest, image);
  }
  
  abstract boolean tryMove(Grid grid, Location current, Location dest);

  public static int random(int low, int high){
    return (int)(Math.random() * (high-low+1) + low);
  }
  
}
