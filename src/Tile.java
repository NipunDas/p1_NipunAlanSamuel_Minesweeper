import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

public class Tile extends JLabel {
	// instance variables
    private ImageIcon coverImage = new ImageIcon("src/Images/tile.png", "Uncovered tile image"); //initialize with generic tile image (same for all tiles)
    private ImageIcon uncoveredImage; //initialize in constructor depending on the type
    private ImageIcon display; //what the tile actually looks like
    private boolean isFlagged = false;
    private boolean isRevealed = false;
    private int type;
    private final Board board; //reference to current board object
    private final GUI g; //reference to current GUI object
    public int x, y; //storing the x and y of tiles for the purpose of board class
    public Music boom;
    private final Music flag;
    private boolean sound;

    //0 = empty tile, 1 = number tile, 2 = mine tile
    public Tile(int type, Board b, int x, int y, GUI g) {
    	boom = new Music("boom.wav",false);
    	flag = new Music("flag.wav", false);
        this.type = type;
        if (type == 0) {
            //set uncovered image to an empty space
            uncoveredImage = new ImageIcon("src/Images/empty.png", "Empty tile image");
        } else if (type >= 1 && type <= 8) {
            //set uncovered image to a number
            uncoveredImage = new ImageIcon("src/Images/" + type + ".png", "Number tile image");
        } else if (type == 9) {
            //set uncovered image to a mine
            uncoveredImage = new ImageIcon("src/Images/mine.png", "Tile with mine image");
        }
        display = coverImage;
        super.setIcon(display);

        //reveals tile if left clicked, flags if right clicked
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    revealTile();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if (!isRevealed) {
                        if (!isFlagged) {
                            flagTile();
                        } else {
                            unFlagTile();
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        board = b; //storing a reference to board to detect first click
        this.x = x; //x-coordinate of tile in board
        this.y = y; //y-coordinate of tile in board
        this.g = g; //storing a reference to GUI
    }

    public ImageIcon getTileImage() {
        return display;
    }

    public void revealTile() {
        isRevealed = true;
        if (!board.isFilled()) {
            board.fill(x, y);
        }
        display = uncoveredImage;
        super.setIcon(display);
        if (type == 0) {
            board.floodFill(x, y);
        }
        sound = g.sound;
        if (type == 9 && sound) {
            boom.play();
        }
    }

    //to prevent infinite recursion (copy of the method, just doesn't call flood-fill again)
    public void reveal() {
        isRevealed = true;
        if (!board.isFilled()) {
            board.fill(x, y);
        }
        display = uncoveredImage;
        super.setIcon(display);
    }

    public void flagTile() {
        //putting a flag on the tile
        coverImage = new ImageIcon("src/Images/flag.png", "Tile with flag");
        display = coverImage;
        super.setIcon(display);
        isFlagged = true;
        sound = g.sound;
        if (sound) {
            flag.play();
        }
    }
    
    public void happyTile() {
    	//happy face tile
    	coverImage = new ImageIcon("src/Images/smile.png", "Tile with flag");
        display = coverImage;
        super.setIcon(display);
    }

    public void unFlagTile() {
        //removing the flag from a flagged tile
        coverImage = new ImageIcon("src/Images/tile.png", "Uncovered tile image");
        display = coverImage;
        super.setIcon(display);
        isFlagged = false;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getType() {
        return type;
    }

    public void changeType(int type) {
        this.type = type;
        if (type == 0) {
            //set uncovered image to an empty space
            uncoveredImage = new ImageIcon("src/Images/empty.png", "Empty tile image");
        } else if (type >= 1 && type <= 8) {
            //set uncovered image to a number
            uncoveredImage = new ImageIcon("src/Images/" + type + ".png", "Number tile image");
        } else if (type == 9) {
            //set uncovered image to a mine
            uncoveredImage = new ImageIcon("src/Images/mine.png", "Tile with mine image");
        }
        display = coverImage;
        super.setIcon(display);
    }
}