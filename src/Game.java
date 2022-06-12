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
  
  public Game() {

    grid = new Grid(8, 8);
    userRow = 3;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    setBG();
    setPiece();
    System.out.println(Piece.random(1,3));
    while(true) handleMouseClick();
    
  }   
  
  
  public void play() {

    while (!isGameOver()) {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
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
  
  public void populateRightEdge(){

  }
  
  public void scrollLeft(){

  }
  
  public void handleCollision(Location loc) {

  }
  
  public int getScore() {
    return 0;
  }
  
  public void updateTitle() {
    grid.setTitle("Checkers  " + getScore());
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
    if(cRed == 0 || cBlack == 0) return true;
    else return false;
  }
  
  public void isValidSimpleMove(){
    
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
    if(cell.getPiece()instanceof Rook) System.out.println("rook");
    if(cell.getPiece()instanceof Bishop) System.out.println("Bish");
    if(cell.getPiece()instanceof Knight) System.out.println("Knight");
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
