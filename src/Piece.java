
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
    if(secondP != null && !sameTeam(firstP,secondP)) grid.turnC = 0;
    if(grid.turn != firstP.team) return;
    if(firstP.team == TEAM_RED){ 
      grid.turn = TEAM_BLACK;
      grid.setTitle("Turn: Black");
    }
    else{
      grid.turn = TEAM_RED;
      grid.setTitle("Turn: Red");
    }
    second.setPiece(first.getPiece());
    first.setPiece(null);
    grid.setImage(start, null);
    grid.setImage(dest, image);
    grid.turnC++;
    
  }
  
  abstract boolean tryMove(Grid grid, Location current, Location dest);

  public static int random(int low, int high){
    return (int)(Math.random() * (high-low+1) + low);
  }

  public static boolean sameTeam(Piece one, Piece two){
    if(one.team == two.team) return true;
    else return false;
  }

  public static boolean getPath(Grid grid, Location start, Location dest){

    int r = (dest.getRow() - start.getRow());
    int c = (dest.getCol() - start.getCol());

    if(Math.abs(r) != 0) r = r/Math.abs(r);
    if(Math.abs(c) != 0) c = c/Math.abs(c);
  
    Location locF = new Location(start.getRow()+r, start.getCol()+c);
      
    while(!locF.equals(dest)){
      if(grid.getCell(locF).getPiece() != null) return false; 
      locF = new Location(locF.getRow()+r, locF.getCol()+c);
    }
    return true;
  }
  
}
