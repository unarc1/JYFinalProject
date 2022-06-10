/*
 * Grid class
 * 6/6/22	Cells resize to fit the window frame
 * 6/8/22	Fixed issue if null or empty String image loaded
 * 6/9/22	Adjusted cell heights to account for 30 pixel title bar height
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;
import javax.swing.*;

public class Grid extends JComponent implements KeyListener, MouseListener
{
	private Cell[][] cells;
	private JFrame frame;
	private int frameHeight = 600;
	private int frameWidth = 800;
	private int titleHeight = 30;
	private boolean isFullScreen = false;
	private int cellHeight;
	private int cellWidth;
	private int lastKeyPressed;
	private Location lastLocationClicked;
	private BufferedImage backgroundImage;
	private boolean bgSet = false;
	private McImage mcImage;
	private boolean mciSet = false;

	private int xOffset;
	private int yOffset;
	private double xScale;
	private double yScale;
	

	public Grid(final int numRows, final int numCols) {
		init(numRows, numCols);
	}

	/**
	 * USE THIS CONSTRUCTOR IF YOU WANT A BACKGROUND IMAGE Note: If you use this
	 * constructor then you cannot use the method setColor()
	 * 
	 * @param numRows
	 * @param numCols
	 * @param imageName the background
	 */
	public Grid(final int numRows, final int numCols, final String imageName) {
		init(numRows, numCols);
		setBackground(imageName);
	}

	public Grid(final String imageFileName) {
		BufferedImage image = loadImage(imageFileName);
		init(image.getHeight(), image.getWidth());
		showFullImage(image);
		setTitle(imageFileName);
	}

	public int getNumRows() {
		return cells.length;
	}

	public int getNumCols() {
		return cells[0].length;
	}

	public boolean isValid(final Location loc){
		if(loc!=null){
			final int row = loc.getRow();
			final int col = loc.getCol();
			return 0 <= row && row < getNumRows() && 0 <= col && col < getNumCols();
		} else {
			return false;
		}
	}

		// returns -1 if no key pressed since last call.
	// otherwise returns the code for the last key pressed.
	public int checkLastKeyPressed() {
		final int key = lastKeyPressed;
		lastKeyPressed=-1;
		return key;
	}

	// returns null if no location clicked since last call.
	public Location checkLastLocationClicked() {
		final Location loc = lastLocationClicked;
		lastLocationClicked = null;
		return loc;
	}

	public Location waitForClick() {
		while (true) {
			final Location clicked = this.checkLastLocationClicked();
			if (clicked != null) {
				System.out.print("Row:" + (clicked.getRow()) + " Col:" + (clicked.getCol())+ "\n");
				Location button = new Location(clicked.getRow(), clicked.getCol());
				return button;
			} else {
				System.out.print(".");
				Grid.pause(100);
			}
		}
	}

	public String showInputDialog(final String message) {
		return JOptionPane.showInputDialog(this, message);
	}

	public void showMessageDialog(final String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void setTitle(final String title) {
		frame.setTitle(title);
	}

	public void fullscreen() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.frameWidth = (int) screenSize.getWidth();
		this.frameHeight = (int) (screenSize.getHeight() - titleHeight);

		isFullScreen = true;
	}

	public void close() {
		frame.dispose();
	}

	public static void pause(final int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (final Exception e) {
			// ignore
		}
	}

	/**
	 * sets the background to imgName. The img is resized to fit in the grids
	 * dimensions. setColor() is disabled
	 * 
	 * @param imgName
	 */
	public void setBackground(final String imgName) {
		this.xOffset = 0;
		this.yOffset = 0;
		this.xScale = 1.0;
		this.yScale = 1.0;

		backgroundImage = loadImage(imgName);
		bgSet = true;

		repaint();
	}

	/**
	 * sets the background to imgName. The img is resized to fit in the grids
	 * dimensions. setColor() is disabled
	 * 
	 * @param imgName
	 */
	public void setMovableBackground(final String imgName, final int xOffset, final int yOffset, final double xScale,
			final double yScale) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xScale = xScale;
		this.yScale = yScale;

		backgroundImage = loadImage(imgName);
		bgSet = true;

		repaint();
	}

	/**
	 * moves the background image within the frame.
	 * 
	 * @param dx how much to move the image to the right (+) or left (-)
	 * @param dy how much to move the image down (+) or up (-)
	 */
	public void moveBackground(final int dx, final int dy) {
		this.xOffset += dx;
		this.yOffset += dy;

		repaint();
	}

	/**
	 * Removes a regular background or moveable background, allowing setColor to
	 * work again.
	 */
	public void removeBackground() {
		bgSet = false;
	}

	/*
	 * Sets an image into only some of the Cells of the Grid
	 */
	public void setMultiCellImage(String mcImageName, Location mcTopLeftCell, int mcRows, int mcCols) {
		mcImage = new McImage(mcImageName, mcTopLeftCell, mcRows, mcCols);
		mciSet = true;
		repaint();
	}

	public void moveMultiCellImage() {

	}

	public void setFillColor(final Location loc, final Color color) {
		if (!isValid(loc))
			throw new RuntimeException("cannot set color of invalid location " + loc + " to color " + color);
		cells[loc.getRow()][loc.getCol()].setFillColor(color);
		repaint();
	}

	public Color getFillColor(final Location loc) {
		if (!isValid(loc))
			throw new RuntimeException("cannot get color from invalid location " + loc);
		return cells[loc.getRow()][loc.getCol()].getFillColor();
	}

	public void setImage(final Location loc, final String imageFileName) {
		if (!isValid(loc))
			throw new RuntimeException(
					"cannot set image for invalid location " + loc + " to \"" + imageFileName + "\"");
		cells[loc.getRow()][loc.getCol()].setImageFileName(imageFileName);
		repaint();
	}

	public String getImage(final Location loc) {
		if (!isValid(loc))
			throw new RuntimeException("cannot get image for invalid location " + loc);
		return cells[loc.getRow()][loc.getCol()].getImageFileName();
	}

	public void setOutlineColor(final Location loc, final Color oc) {
		if (!isValid(loc))
			throw new RuntimeException("cannot set outline for invalid location " + loc);
		cells[loc.getRow()][loc.getCol()].setOutlineColor(oc);
		repaint();
	}

	public Color getOutlineColor(final Location loc) {
		if (!isValid(loc))
			throw new RuntimeException("cannot get outline color for invalid location " + loc);
		return cells[loc.getRow()][loc.getCol()].getOutlineColor();
	}

	public void setAllOutlinesColor(final Color oc) {
		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				cells[r][c].setOutlineColor(oc);
			}
		}
		repaint();
	}


	// ------------------ HELPER METHODS -----------------//
	public void load(String imageFileName) {
		showFullImage(loadImage(imageFileName));
		setTitle(imageFileName);
	}

	public void save(String imageFileName) {
		try {
			BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			paintComponent(bi.getGraphics());
			int index = imageFileName.lastIndexOf('.');
			if (index == -1)
				throw new RuntimeException("invalid image file name:  " + imageFileName);
			ImageIO.write(bi, imageFileName.substring(index + 1), new File(imageFileName));
		} catch ( IOException e) {
			throw new RuntimeException("unable to save image to file:  " + imageFileName);
		}
	}

	private void init(final int numRows, final int numCols) {
		lastKeyPressed = -1;
		lastLocationClicked = null;

		cells = new Cell[numRows][numCols];

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++)
				cells[row][col] = new Cell();
		}

		frame = new JFrame("Grid");
		frame.addKeyListener(this);
		frame.addMouseListener(this);

		System.out.println("cells setup with : " + numRows + "," + numCols);
		//System.out.println(cells.length + "," + cells[0].length);
		//System.out.println(getNumRows() + "," + getNumCols());
		//System.out.println(getCellHeight() + "," + getCellWidth());

		if(!isFullScreen){
			int cellSize = Math.max(Math.min(frameHeight / getNumRows(), frameWidth / getNumCols()), 1);
			cellHeight = frameHeight / getNumRows();
			cellWidth = frameWidth / getNumCols();
			frame.setPreferredSize(new Dimension(cellSize * numCols, cellSize * numRows));
		} else {
			frameHeight = getUsableFrameHeight();
			frameWidth = frame.getWidth();
			cellHeight = frameHeight / getNumRows();
			cellWidth = frameWidth / getNumCols();
		}

		frame.getContentPane().add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

	}

	private BufferedImage loadImage(String imageFileName) {
		
		if(imageFileName == null || "".equals(imageFileName)){
			System.out.println("Image is null or \"\"");
			return null;
		} else{
			final URL url = getClass().getResource(imageFileName);
			if (url == null) {
				throw new RuntimeException("cannot find file:  " + imageFileName);
			}
			try {
				return ImageIO.read(url);
			} catch (IOException e) {
				throw new RuntimeException("unable to read from file:  " + imageFileName);
			}
		}
	}

	private void showFullImage(BufferedImage image) {
		for (int row = 0; row < getNumRows(); row++) {
			for (int col = 0; col < getNumCols(); col++) {
				int x = col * image.getWidth() / getNumCols();
				int y = row * image.getHeight() / getNumRows();
				int c = image.getRGB(x, y);

				int red = (c & 0x00ff0000) >> 16;
				int green = (c & 0x0000ff00) >> 8;
				int blue = c & 0x000000ff;

				cells[row][col].setFillColor(new Color(red, green, blue));
			}
		}
		repaint();
	}

	// private int getCellSize() {
	// 	final int cellWidth = getWidth() / getNumCols();
	// 	final int cellHeight = getHeight() / getNumRows();
	// 	return Math.min(cellWidth, cellHeight);
	// }

	private int getCellHeight(){
		//System.out.println("fh: " + frame.getHeight());
		return getUsableFrameHeight() / getNumRows();
	}

	private int getUsableFrameHeight(){
		return frame.getHeight() - this.titleHeight;
	}

	private int getCellWidth(){
		//System.out.println("fw: " + frame.getWidth());
		return frame.getWidth() / getNumCols();
	}

	private static java.awt.Color toJavaColor(final Color color) {
		return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue());
	}

	public void paintComponent(final Graphics g) {
		
		cellHeight = getCellHeight();
		cellWidth = getCellWidth();
		
		//set background picture
		if(bgSet) {
			final int bgWidth = (int) (frame.getWidth()*xScale);
			final int bgHeight = (int) (getUsableFrameHeight()*yScale);
			g.drawImage(backgroundImage, xOffset, yOffset, bgWidth,bgHeight,null);
			//g.drawImage(backgroundImage,0,0,frame.getWidth(),frame.getHeight(),null);
		}
		
		//set multi-cell image
		if(mciSet){
			String mcImageName = mcImage.getMcImageFileName();
			if(mcImageName == null){
				System.out.println("McImage file not found:  " + mcImageName);
			} else {

				BufferedImage mcBuff = loadImage(mcImageName);
				int picWidth =  mcBuff.getWidth(null);
				int picHeight = mcBuff.getHeight(null);
				double picRatio = (double) picWidth/picHeight;

				int xCells = mcImage.getMcCols();
				int yCells = mcImage.getMcRows();
				int mciWidth = xCells * cellWidth;
				int mciHeight = yCells * cellHeight;

				System.out.print("xcells:"+xCells);
				System.out.print("\tycells:"+yCells);
				System.out.print("\tmciW:"+mciWidth);
				System.out.println("\tmciH:"+mciHeight);

				double mciRatio = (double) mciWidth/mciHeight;

				Location startLoc = mcImage.getMcTopLeftLoc();
				int yStartRow = startLoc.getRow();
				int xStartCol = startLoc.getCol();

				int xStartPixel = xStartCol * cellWidth;
				int yStartPixel = yStartRow * cellHeight + titleHeight;

				int drawHeight = mciHeight;
				int drawWidth = mciWidth;

				if (mciRatio > picRatio)	{
					drawWidth = (int) (drawHeight * picRatio);
					xStartPixel += (mciWidth - drawWidth) / 2;
					System.out.println("mciRatio bigger");
				} else {
					drawHeight = (int) (drawWidth / picRatio);
					yStartPixel += (mciHeight - drawHeight) / 2;
					System.out.println("picRatio bigger");
				}
				g.drawImage(mcBuff, xStartPixel, yStartPixel, drawWidth, drawHeight, null);

				System.out.print("mciRatio:"+mciRatio);
				System.out.print("\tpicRatio"+picRatio);
				System.out.print("\txStartPixel"+xStartPixel);
				System.out.print("\tyStartPixel"+yStartPixel);
				System.out.print("\tdrawHeight"+drawHeight);
				System.out.print("\tdrawWidth"+drawWidth);
				System.out.println();


				//annotate all of the Cells covered by a McImage
				for(int r = yStartRow; r < yStartRow + yCells; r++){
					for(int c = xStartCol; c < xStartCol + xCells; c++){
						cells[r][c].setCoveredWithPic(true);
					}
				}
			}
		}

		//loop through every Cell in the Grid
		for (int row = 0; row < getNumRows(); row++){
			for (int col = 0; col < getNumCols(); col++){

				Location loc = new Location(row, col);
				Cell cell = cells[loc.getRow()][loc.getCol()];
				int x = col * cellWidth;		//start pixel of each Cell
				int y = row * cellHeight;

				//Fill Cells with Color
				final Color color = cell.getFillColor();
				g.setColor(toJavaColor(color));
				if(!bgSet && !cells[row][col].isCoveredWithPic()){
					g.fillRect(x, y, cellWidth, cellHeight);
				}

				//Fill Cells with Location images
				final String locImageName = cell.getImageFileName();
				if (locImageName != null){
					final URL url = getClass().getResource(locImageName);
					if (url == null){
						System.out.println("File not found:  " + locImageName);
					} else {
						final Image image = new ImageIcon(url).getImage();
						final int width =  image.getWidth(null);
						final int height = image.getHeight(null);
						if (width > height)	{
							final int drawHeight = cellHeight * height / width;
							g.drawImage(image, x, y + (cellHeight - drawHeight) / 2, cellHeight, drawHeight, null);
						} else {
							final int drawWidth = cellWidth * width / height;
							g.drawImage(image, x + (cellWidth - drawWidth) / 2, y, drawWidth, cellWidth, null);
						}
					}
				}
				
				//Draw outlines around Cells
				if (cell.getOutlineColor()!=null){
					final Color oc = cell.getOutlineColor();
					g.setColor(toJavaColor(oc));
					g.drawRect(x, y, cellWidth, cellHeight);
				}
			}
		}
	}

	public void keyPressed(final KeyEvent e) {
		lastKeyPressed = e.getKeyCode();
	}

	public void mousePressed(final MouseEvent e) {
		final int cellWidth = getCellWidth();
		final int cellHeight = getCellHeight();

		final int row = (e.getY()-titleHeight) / cellHeight;
		//System.out.println("ey" + e.getY() + "\tch: " + cellHeight + "\tey/ch: " + e.getY()/cellHeight);
		if (row < 0 || row >= getNumRows())
			return;

		final int col = e.getX() / cellWidth;
		if (col < 0 || col >= getNumCols())
			return;
		lastLocationClicked = new Location(row, col);
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// ignore
	}

	@Override
	public void keyTyped(final KeyEvent e) {
		//ignore
	}
	
	public void keyReleased(final KeyEvent e) {
		// ignored
	}

}  