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
    grid.setTitle("Game:  " + getScore());
  }
  
  public boolean isGameOver() {
    return false;
  }
  
  public boolean isRed(String tile){
        return tile.equals(redT);
    }
  
  public void setBG(){
    grid.setImage(new Location(0,0) , redT);
    for(int r = 0; r < 8; r++){
      for(int c = 0; c < 8; c++){
        if(r != 0 && c==0 && isRed(grid.getImage(new Location(r-1,c)))) grid.setImage(new Location(r,c), blackT);
        else if(r != 0 && c==0 && !isRed(grid.getImage(new Location(r-1,c)))) grid.setImage(new Location(r,c), redT);
        else if(c != 0 && isRed(grid.getImage(new Location(r,c-1)))) grid.setImage(new Location(r,c), blackT);
        else if(c != 0 && !isRed(grid.getImage(new Location(r,c-1)))) grid.setImage(new Location(r,c), redT);
      }
    }
    
  }
  
  public void setPiece(){
    for(int r = 0; r < 8; r++){
      for(int c = 0; c < 8; c++){
        if(r < 3 && !isRed(grid.getImage(new Location(r,c)))) grid.setImage(new Location(r,c), blackP);
        else if(r > 4 && !isRed(grid.getImage(new Location(r,c)))) grid.setImage(new Location(r,c), redP);
      } 
    }
  }




  
}