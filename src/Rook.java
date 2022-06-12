public class Rook extends Piece{

  public Rook(int team){super(team);}
  
  boolean tryMove(Grid grid, Location start, Location dest) {
    int row = Math.abs(start.getRow() - dest.getRow());
    int col = Math.abs(start.getCol() - dest.getCol());
    
	  if((row == 0 && col > 0) || (row > 0 && col == 0)){
      move(grid, start, dest, grid.getCell(start), grid.getCell(dest), grid.getImage(start));
      System.out.println(row + col);
      return true;
    }
    else return false; 
    
	  
  }

  
}