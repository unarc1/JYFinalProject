public class Rook extends Piece{

  public Rook(int team){super(team);}
  
  boolean tryMove(Grid grid, Location start, Location dest) {
    int row = Math.abs(start.getRow() - dest.getRow());
    int col = Math.abs(start.getCol() - dest.getCol());
    
	  if((row == 0 && col > 0) || (row > 0 && col == 0)){
      if(getPath(grid,start,dest)){
        move(grid, start, dest, grid.getCell(start), grid.getCell(dest), grid.getImage(start));
        return true;
      }
    }
    return false; 
  }
}

  
