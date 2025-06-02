import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String [] args) throws IOException {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,-2,5};
        int [] e1 = {0,1,3};
        Polynomial p1 = new Polynomial(c1, e1);;
        
        double [] c2 = {-2,5,1,7};
        int [] e2 = {1,0,3,5};
        Polynomial p2 = new Polynomial(c2, e2);
        
        Polynomial s = p1.add(p2);
        Polynomial prod = p1.multiply(p2);
        
        s.saveToFile("text.txt");
        prod.saveToFile("product.txt");
        
        System.out.println("s(2) = " + s.evaluate(2));
        System.out.println("prod(2) = " + prod.evaluate(2));
        System.out.println("p1(-1) = " + p1.evaluate(-1));
        System.out.println("p2(0.5) = " + p2.evaluate(0.5));
        
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        
        File file = new File("input.txt");
        if (file.exists()) {
            Polynomial output = new Polynomial(file);
            System.out.println("The file input was (evaluated): " + output.evaluate(1));
        }
        else {
            System.out.println("The file was not found.");
        }
    }
}
