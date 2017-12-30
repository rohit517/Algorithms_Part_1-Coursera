//package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{

    private final int size;
    private final int top = 0;
    private final int bottom;
    private final WeightedQuickUnionUF qf;
    private boolean[][] grid;
    private int openSitesCount;
    
    public Percolation(int n){ // create n-by-n grid, with all sites blocked
        
        if ( n <= 0) throw new IllegalArgumentException (" Grid Size cannot be less than 0 ");
        size = n;
        bottom = size*size + 1;
        qf = new WeightedQuickUnionUF(size*size + 2);
        grid = new boolean[size][size];
        openSitesCount = 0;
        
        //Set grids to closed
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                grid[i][j] = false;
            }
        }
    }

    public void open(int row, int col){
        int i = row - 1;
        int j = col - 1;
        
        if( row < 1 || row > size || col < 1 || col > size){
            throw new IllegalArgumentException ("Row or colom cannot be greater than grid size");
        }
        
        if(isOpen(row,col) == false){
            grid[i][j] = true;
            openSitesCount++;
        }
        
        if(row == 1) {
            qf.union(getIndex(i,j),top); //Top row cases
        }
        
        if(row == size) {
            qf.union(getIndex(i,j), bottom); //Bottom row cases
        }
        
        if(row > 1 && isOpen(row-1,col)){
            qf.union(getIndex(i,j),getIndex(i-1,j)); //Same colom, top row element
        }
         
        if(row < size && isOpen(row+1,col)){
            qf.union(getIndex(i,j),getIndex(i+1,j)); //Same colom, bottom row element
        }
        
        if(col > 1 && isOpen(row,col-1)){
            qf.union(getIndex(i,j),getIndex(i,j-1)); //Same row, prev col element
        }
        
        if(col < size && isOpen(row,col+1)){
            qf.union(getIndex(i,j),getIndex(i,j+1)); //Same row, next col element
        }
    }
    
    public boolean isOpen(int row, int col){ //Check if cell is open
        if( row < 1 || row > size || col < 1 || col > size){
            throw new IllegalArgumentException ("Row or colom cannot be greater than grid size");
        }
        return grid[row-1][col-1];
    }
    
    public boolean isFull(int row, int col){    //Check if cell is connected to top
         if( row < 1 || row > size || col < 1 || col > size){
            throw new IllegalArgumentException ("Row or colom cannot be greater than grid size");
        }
         
         return qf.connected(top, getIndex(row-1,col-1));
    }
    
    public int numberOfOpenSites(){  // Get number of open cells
        return openSitesCount;
    }
    
    public boolean percolates(){ //Check if system percolates
        return qf.connected(top,bottom);
    }
        
    private int getIndex(int i, int j){ // Get index of grid from row and col mapping
        return (i*size + j + 1);
        
    }
    
    
    
}