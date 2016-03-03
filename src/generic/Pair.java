package generic;

/**
 * A simple example used in many tutorials.
 * 
 * Replicates the functionality of Map.Entry, but is perhaps simpler to use.
 */
public class Pair<A, B> {
    // these can be any two types, same or different
    private final A first;
    private final B last;


    public Pair (A key, B value) {  
        this.first = key;
        this.last = value;
    }
    
    public Pair (A both) {
    	this.first = both;
    	this.last = (B) both;
    }

    // getters
    public A getFirst () {
        return first;
    }
    
    public B getLast () {
        return last;
    }

    public String toString() { 
        return "(" + first + ", " + last + ")";  
    }
}