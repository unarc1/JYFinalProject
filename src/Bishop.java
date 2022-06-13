public class Bishop extends Piece{

  public Bishop(int team){super(team);}
  
  boolean tryMove(Grid grid, Location start, Location dest) {
    int row = Math.abs(dest.getRow() - start.getRow());
    int col = Math.abs(dest.getCol() - start.getCol());
    
	  if(row == col && col != 0){
      if(getPath(grid, start, dest)){
        move(grid, start, dest, grid.getCell(start), grid.getCell(dest), grid.getImage(start));
        return true; 
      }
    }
    return false; 
  }
}