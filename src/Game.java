/* Game Class Starter File
 * Last Edit: 5/6/2021
 * Author: _____________________
 */

public class Game {

  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private String userPic = "images/user.gif";
  private String blackP = "images/blackpiece.png";
  private String redP = "images/redpiece.png";
  private String rules = "Cheskers: Checkers on the outside, Chess on the inside.\n\nChesckers is a game where it appears like checkers.\nDifference is the pieces act like chess pieces.\nEach piece can either be a knight, bishop, or rook\n\nin every game, the amount of each piece you get will always be random.\nYou win by capturing (landing on) all your opponent’s pieces.\nThe game will end in a draw if no captures occur 17 moves from the last\ncapture.\n\nIf you haven’t played chess:\n\nThe rook can only move vertically and horizontally,\nwith no ability to jump over any pieces.\n\nThe bishop can only move diagonally, with no ability to jump over any pieces.\n\nThe knight may only move two squares vertically and one square horizontally\nor two squares horizontally and one square vertically (like an L shape),\nwith the ability to jump over pieces.";

;
  
  public Game() {

    grid = new Grid(8, 8);
    userRow = 3;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    grid.turn = Piece.random(0,1);
    if(grid.turn == 0) grid.setTitle("turn: Black");
    else grid.setTitle("turn: Red");               
    setBG();
    setPiece();
    grid.showMessageDialog(rules);
;    
  }   
  
  
  public void play() {
    while(!isGameOver()) handleMouseClick();
  }
  
  public void handleKeyPress(){

    //check last key pressed
    int key = grid.checkLastKeyPressed();
    System.out.println(key);

    //set "w" key to move the plane up
    if(key == 87){
        //check case where out of bounds

        //change the field for userrow

        userRow--;

        //shift the user picture up in the array
        Location loc = new Location(userRow, 0);
        grid.setImage(loc, "user.gif");
        
        Location oldLoc = new Location(userRow+1, 0);
        grid.setImage(oldLoc, null);

    }
    //if I push down arrow, then plane goes down


  }
  
  public boolean isGameOver() {
    int cRed = 0;
    int cBlack = 0;
    for(int r = 0; r < 8; r++){
      for(int c = 0; c < 8; c++){
        if(grid.getImage(new Location(r,c)) == redP) cRed++;
        if(grid.getImage(new Location(r,c)) == blackP) cBlack++;
      }
    }
    if(cRed == 0 || cBlack == 0){ 
      if(grid.turn == 0) grid.showMessageDialog("Red wins");
      if(grid.turn == 1) grid.showMessageDialog("Black wins");
      return true;
    }             
    else if(grid.turnC == 17){
      grid.showMessageDialog("Stalemate: no one wins");
      return true; 
    }
    else return false;
  }
  
  public void setBG(){
    for(int r = 0; r < 8; r++){
      for(int c = 0; c < 8; c++){
        Location cell = new Location(r,c);
        if((r+c)%2!=0) grid.setFillColor(cell, new Color(0,0,0));
        else grid.setFillColor(cell, new Color(255,0,0));;
      }
    }
  }

  public void setPiece(){
    for(int r = 0; r < 8; r++){
      for(int c = 0; c < 8; c++){
        int team = 0;
        Location cell = new Location(r,c);
        if(r < 3 && (r+c)%2!=0){
          team = Piece.TEAM_BLACK;
           grid.setImage(cell, blackP);
        }
        else if(r > 4 && (r+c)%2!=0){
          team = Piece.TEAM_RED;
          grid.setImage(cell, redP);
        }
        else continue; 
        switch(Piece.random(1,3)){
          case 1: 
            grid.getCell(cell).setPiece(new Bishop(team));
            break;
          case 2:
            grid.getCell(cell).setPiece(new Knight(team));
            break;
          case 3: 
            grid.getCell(cell).setPiece(new Rook(team));
            break;
        };
      } 
    }
  }
  
  public void handleMouseClick(){
    
    Location first = grid.waitForClick();
    Cell cell = grid.getCell(first);
    Color one = grid.getFillColor(first);
    if(cell.getPiece() == null) return;
    grid.setFillColor(first, new Color(0, 0, 255));
    
    Location second = grid.waitForClick();
    if(second.equals(first)){
      grid.setFillColor(first, one);
      return;
    }
    Color two = grid.getFillColor(second);
    grid.getCell(first).getPiece().tryMove(grid, first, second);
    
    grid.setFillColor(first, one);
    grid.setFillColor(second, two);
  }
}
