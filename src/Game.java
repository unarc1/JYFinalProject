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
  private String redT = "images/red.png";
  private String blackT = "images/black.png";
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
    while(true) HandleMouseClick();
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
    for(int r = 0; r < 8; r++)
      for(int c = 0; c < 8; c++){
        if(grid.getImage(new Location(r,c)) == redP) cRed++;
        if(grid.getImage(new Location(r,c)) == blackP) cBlack++;
      }
    if(cRed == 0 || cBlack == 0) return true;
    else return false;
  }
  
  public void isValidSimpleMove(){
    
  }
  
  public void setBG(){
    grid.setImage(new Location(0,0) , redT);
    for(int r = 0; r < 8; r++)
      for(int c = 0; c < 8; c++){
        if((r+c)%2!=0) grid.setImage(new Location(r,c), blackT);
        else grid.setImage(new Location(r,c), redT);
      }
  }
    
  public void setPiece(){
    for(int r = 0; r < 8; r++)
      for(int c = 0; c < 8; c++){
        if(r < 3 && (r+c)%2!=0) grid.setImage(new Location(r,c), blackP);
        else if(r > 4 && (r+c)%2!=0) grid.setImage(new Location(r,c), redP);
      } 
  }

  public void HandleMouseClick(){
    
    Location first = grid.waitForClick();
    
    while(grid.getImage(first).equals(blackP) || grid.getImage(first).equals(redP)){
    
    if(grid.getImage(first).equals(blackP)){
      Location second = grid.waitForClick();
      if(grid.getImage(second).equals(blackT)){
        grid.setImage(second, blackP);
        grid.setImage(first, blackT);
      }
    }
    
    if(grid.getImage(first).equals(redP)){
      Location second = grid.waitForClick();
      if(grid.getImage(second).equals(blackT)){
        grid.setImage(second, redP);
        grid.setImage(first, blackT);
      }
    }
    first = grid.waitForClick();
    }
  }
  
  
  





















  
}



