public class Bishop extends Piece{

  public Bishop(int team){super(team);}
  
  boolean tryMove(Grid grid, Location start, Location dest) {
    int row = Math.abs(start.getRow() - dest.getRow());
    int col = Math.abs(start.getCol() - dest.getCol());
    /* step1: subtract row coord 2 - row coord 1.
              subtract col coord 2 - col cord 1
      step 2: divide the sum of the row by the absolute value of rows sum
              divide the sum of the cols by the absolute value of cols sum
      step 3: congrats, you have obtained the direction. 
    */
    
	  if(row == col){
      move(grid, start, dest, grid.getCell(start), grid.getCell(dest), grid.getImage(start));
      return true; 
    }
    else return false; 
    
	  
  }

  
}