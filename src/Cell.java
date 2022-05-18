/* Updated 6/18/2020
 * Outline and OutlineColor added
 */ 

public class Cell {

  private Color fillColor;
  private String imageFileName;
  private Color outlineColor;
  private boolean coveredPic;
  
  public Cell() {
    fillColor = new Color(0, 0, 0);
    imageFileName = null;
    outlineColor = null;
    coveredPic = false;
  }
  
  public void setFillColor(Color c) {
    fillColor = c;
  }
  
  public Color getFillColor() {
    return fillColor;
  }

  public void setImageFileName(String fileName) {
    imageFileName = fileName;
  }
  
  public String getImageFileName() {
    return imageFileName;
  }

  public void setOutlineColor(Color oc){
    this.outlineColor = oc;
  }

  public Color getOutlineColor(){
    return this.outlineColor;
  }

  public void setCoveredWithPic(boolean isCoveredWithPic) {
    this.coveredPic = isCoveredWithPic;
  }
  
  public boolean isCoveredWithPic() {
    return this.coveredPic;
  }

}  