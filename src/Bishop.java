public class Bishop extends Piece{

  public Bishop(int team){super(team);}
  
  boolean tryMove(Grid grid, Location start, Location dest) {
    int row = Math.abs(start.getRow() - dest.getRow());
    int col = Math.abs(start.getCol() - dest.getCol());
    
	  if(row == col){
      move(grid, start, dest, grid.getCell(start), grid.getCell(dest), grid.getImage(start));
      return true; 
    }
    else return false; 
    
	  
  }

  
}