import java.awt.Color;

public class Game {

    //Data Members
    private LinkedGrid<Character> board;
    private LinkedGrid<GUICell> cells;
    public static int width;
    public static int height;
    private boolean isPlaying;
    private GUI gui;


    //Constructor
    public Game(int width, int height, boolean fixedRandom, int seed) {
        this.width = width;
        this.height = height;
        board = new LinkedGrid<Character>(width, height);
        cells = new LinkedGrid<GUICell>(width, height);
        for(int i = 0 ; i < width; i++) {
            for(int j = 0; j < height; j++) {
                board.setElement(i, j, '_');
            }
        }

        for(int i = 0 ; i < width; i++) {
            for(int j = 0; j < height; j++) {
                cells.setElement(i, j, new GUICell(j,i));
            }
        }
        BombRandomizer.placeBombs(board, fixedRandom, seed);
        determineNumbers();
        gui = new GUI(this, cells);
        isPlaying = true;
    }

    //Second constructor
    public Game(LinkedGrid<Character> board) {
        this.board = board;
        this.width = board.getWidth();
        this.height = board.getHeight();
        cells = new LinkedGrid<GUICell>(width, height);
        for(int i = 0 ; i < width; i++) {
            for(int j = 0; j < height; j++) {
                cells.setElement(i, j, new GUICell(j,i));
            }
        }
        determineNumbers();
        gui = new GUI(this,cells);
        isPlaying = true;
    }


    //Getter for width
    public int getWidth() {
        return width;
    }

    //Getter for height
    public int getHeight() {
        return height;
    }

    //Getter for cells
    public LinkedGrid<GUICell> getCells() {
        return cells;
    }

    //Function to determine the number of bombs in the neighbor of the cell
    public void determineNumbers() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(board.getElement(i, j) == 'x') {
                    cells.getElement(i, j).setNumber(-1);
                }
                else {
                    int count = 0;
                    if(i > 0 && board.getElement(i - 1, j) == 'x') {
                        count++;
                    }
                    if(j > 0 && board.getElement(i, j - 1) == 'x') {
                        count++;
                    }
                    if(i > 0 && j > 0 && board.getElement(i - 1, j - 1) == 'x') {
                        count++;
                    }
                    if(i < width - 1 && board.getElement(i + 1, j) == 'x') {
                        count++;
                    }
                    if(j < height - 1 && board.getElement(i, j + 1) == 'x') {
                        count++;
                    }
                    if(i < width - 1 && j < height - 1 && board.getElement(i + 1, j + 1) == 'x') {
                        count++;
                    }
                    if(i < width - 1 && j > 0 && board.getElement(i + 1, j - 1) == 'x') {
                        count++;
                    }
                    if(i > 0 && j < height - 1 && board.getElement(i - 1, j + 1) == 'x') {
                        count++;
                    }
                    cells.getElement(i, j).setNumber(count);
                }
            }
        }
    }

    //Function to call on every click on the block
    public int processClick(int column, int row) {
        if(!isPlaying) {
            return -10;
        }
        if(cells.getElement(column, row).getNumber() == -1) {
            cells.getElement(column, row).setBackground(Color.red);
            cells.getElement(column, row).reveal();
            isPlaying = false;
            return -1;
        }
        else if(cells.getElement(column, row).getNumber() == 0) {
            return recClear(column, row);
        }
        else {
            if(cells.getElement(column, row).isRevealed()) {
                return 0;
            }
            else {
                cells.getElement(column, row).reveal();
                cells.getElement(column, row).setBackground(Color.white);
                return 1;
            }
        }
    }

    //Recursive function to reveal the cells
    private int recClear(int column, int row) {
        if(column < 0 || row < 0 || column >= cells.getWidth() || row >= cells.getHeight()) {
            return 0;
        }
        else if(cells.getElement(column, row).isRevealed()) {
            return 0;
        }
        else if(cells.getElement(column, row).getNumber() == -1) {
            return 0;
        }
        else if(cells.getElement(column, row).getNumber() > 0){
            cells.getElement(column, row).reveal();
            if(gui != null)
                cells.getElement(column, row).setBackground(Color.white);
            return 1;
        }
        else {
            cells.getElement(column, row).reveal();
            if(gui != null)
                cells.getElement(column, row).setBackground(Color.white);
            int result = 1;
            result += recClear(column - 1, row);
            result += recClear(column - 1, row - 1);
            result += recClear(column - 1, row + 1);
            result += recClear(column, row - 1);
            result += recClear(column, row + 1);
            result += recClear(column + 1, row - 1);
            result += recClear(column + 1, row);
            result += recClear(column + 1, row + 1);
            return result;
        }
    }
}

