public class Knight extends Piece{

  public Knight(int team){super(team);}
  
  boolean tryMove(Grid grid, Location start, Location dest) {
    int row = Math.abs(start.getRow() - dest.getRow());
    int col = Math.abs(start.getCol() - dest.getCol());
    
	  if((row == 2 && col == 1) || (row == 1 && col == 2)){
      move(grid, start, dest, grid.getCell(start), grid.getCell(dest), grid.getImage(start));
      return true;
    }  
    else return false;
  }

  
}