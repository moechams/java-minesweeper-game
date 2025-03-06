
public class LinkedGrid<T> {

    //Data Members
    private int width;
    private int height;
    private LinearNode<T> [] grid;

    //Constructor
    public LinkedGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new LinearNode[width];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                LinearNode<T> node = new LinearNode<T>();
                if(grid[i] == null) {
                    grid[i] = node;
                }
                else {
                    LinearNode<T> temp = grid[i];
                    while(temp.getNext() != null) {
                        temp = temp.getNext();
                    }
                    LinearNode<T> node1 = new LinearNode<T>();
                    temp.setNext(node1);
                }
            }
        }
    }

    //Setter for element at particular row and column of grid
    public void setElement(int col, int row, T element) {
        if(col >= width || row >= height || row < 0 || col < 0) {
            throw new LinkedListException("Specified Row or Column is outside the grid!");
        }
        else {
            int count = 0;
            LinearNode<T> temp = grid[col];
            while(count < row) {
                temp = temp.getNext();
                count++;
            }
            temp.setElement(element);
        }
    }

    //Get element at particular row and column
    public T getElement(int col, int row) {
        if(col >= width || row >= height || row < 0 || col < 0) {
            throw new LinkedListException("Specified Row or Column is outside the grid!");
        }
        else {
            int count = 0;
            LinearNode<T> temp = grid[col];
            while(count < row) {
                temp = temp.getNext();
                count++;
            }
            return temp.getElement();
        }
    }


    //Getter for width attribute
    public int getWidth() {
        return width;
    }


    //Getter for height attribute
    public int getHeight() {
        return height;
    }


    //Return string representation of the grid
    public String toString() {
        String str = "";
        for(int i = 0; i < height; i++) {
            LinearNode<T> temp;
            for(int j = 0; j < width; j++) {
                temp = grid[j];
                for(int k = 0; k < i; k++) {
                    temp = temp.getNext();
                }
                str += temp.getElement() + "  ";
            }
            str +=  "\n";
        }
        return str;
    }
}
