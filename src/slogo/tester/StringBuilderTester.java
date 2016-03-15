package slogo.tester;

public class StringBuilderTester {
    
    public static void main (String[] args){
        StringBuilder b = new StringBuilder();
        b.append("I LIKE FOOD ");
        StringTest apple = new StringTest(b);
        System.out.print(b);
    }
}
