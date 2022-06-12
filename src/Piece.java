
public abstract class Piece
{ 
  static final int TEAM_BLACK = 0;
  static final int TEAM_RED = 1;
  final int team;
  
  Piece(int team) {this.team = team;}
  
  protected void move(Grid grid, Location start, Location dest, Cell first, Cell second, String image){
    Piece firstP = first.getPiece();
    Piece secondP = second.getPiece();
    if(secondP != null && sameTeam(firstP,secondP)) return;
    second.setPiece(first.getPiece());
    first.setPiece(null);
    grid.setImage(start, null);
    grid.setImage(dest, image);
  }
  
  abstract boolean tryMove(Grid grid, Location current, Location dest);

  public static int random(int low, int high){
    return (int)(Math.random() * (high-low+1) + low);
  }

  public static boolean sameTeam(Piece one, Piece two){
    if(one.team == two.team) return true;
    else return false;
  }
}
