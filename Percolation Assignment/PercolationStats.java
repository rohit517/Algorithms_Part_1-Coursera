//package percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats{

    private final int noTrials;
    private final double[] results;
    private static final double CONFIDENCE_95 = 1.96; 
    
    public PercolationStats(int n, int trials){
        if ( n <= 0 || trials <= 0) throw new IllegalArgumentException (" Grid Size or trials cannot be less than 0 ");
        
        noTrials = trials;
        results = new double[noTrials];
        for(int no = 0; no < noTrials; no++){
        
            Percolation pr = new Percolation(n);
            while(!pr.percolates()){
            
                int i = StdRandom.uniform(1,n+1);
                int j = StdRandom.uniform(1,n+1);
                if(!pr.isOpen(i,j)){
                    pr.open(i,j);                    
                }
            }
            
            int openSites = pr.numberOfOpenSites();
            double currentResult = (double) openSites / (n*n);
            results[no] = currentResult;
        }
                                     
    }
    
    public double mean(){
        return StdStats.mean(results);
    }
    
    public double stddev(){
        return StdStats.stddev(results);
    }
    
    public double confidenceLo(){
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(noTrials));
    }
    
    public double confidenceHi(){
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(noTrials));
    }
    
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, T);

        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
    

}