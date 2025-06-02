import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileReader;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[]{0};
        exponents = new int[]{0};
    }
    
    public Polynomial(double [] input_coeff, int[] input_exp) {
        int len = input_coeff.length;
        coefficients = new double[len];
        exponents = new int[len];
        for (int i = 0; i < len; i++) {
            coefficients[i] = input_coeff[i];
            exponents[i] = input_exp[i];
        }
    }
    
    public Polynomial(File poly) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(poly));
        String line = input.readLine();
        input.close();
        
        int count = 1; // Amount of terms in polynomial
        for (int i = 0; i < line.length(); i ++) {
            if (i != 0 && (line.charAt(i) == '-' || line.charAt(i) == '+')) {
                count ++;
            }
        }
        
        coefficients = new double[count];
        exponents = new int[count];
        
        int index = 0;
        int incr = 0;
        
        while (incr < line.length()) {
            int begin = incr;
            incr ++;
            
            while (incr < line.length() && line.charAt(incr) != '-' && line.charAt(incr) != '+') {
                incr ++;
            }
            
            String seg = line.substring(begin, incr);
            double coeff = 0;
            int exp = 0;
            
            if (seg.contains("x")) {
                int index_x = seg.indexOf("x");
                
                String strCoeff = seg.substring(0, index_x);
                coeff = Double.parseDouble(strCoeff);
                
                String strExp = seg.substring(index_x + 1);
                exp = Integer.parseInt(strExp);
            }
            else {
                coeff = Double.parseDouble(seg);
                exp = 0;
            }
            
            coefficients[index] = coeff;
            exponents[index] = exp;
            index ++;
        }
    }
    
    
    public void saveToFile(String file_name) throws IOException {
        File file = new File(file_name);
        PrintStream text = new PrintStream(file);
        
        int len = coefficients.length;
        String polynomial = "";
        double coeff;
        int exp;
        
        for (int i = 0; i < len; i ++) {
            coeff = coefficients[i];
            exp = exponents[i];
            
            if (i > 0) {
                if (coeff >= 0) {
                    polynomial += "+";
                }
            }
            if (exp == 0) {
                polynomial += coeff;
            }
            else {
                polynomial += coeff + "x" + exp;
            }
        }
        text.println(polynomial);
        text.close();
    }
    
    
    public Polynomial add(Polynomial poly) {
        int len1 = this.coefficients.length;
        int len2 = poly.coefficients.length;
        
        int maxlength = len1 + len2;
        double [] sum_coefficient = new double[maxlength];
        int [] sum_exp = new int[maxlength];
        
        int c = 0;
        for (int i = 0; i < len1; i ++) {
            sum_coefficient[c] = this.coefficients[i];
            sum_exp[c] = this.exponents[i];
            c ++;
        }
        
        for (int i = 0; i < len2; i ++) {
            sum_coefficient[c] = poly.coefficients[i];
            sum_exp[c] = poly.exponents[i];
            c ++;
        }
        return remove_redundance(sum_coefficient, sum_exp, c);
    }
    
    
    private Polynomial remove_redundance(double [] coeffs, int [] exps, int length) {
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (exps[i] == exps[j]) {
                    coeffs[i] += coeffs[j];
                    coeffs[j] = 0;
                    exps[j] = -1;
                }
            }
        }
        
        int c = 0;
        for (int i = 0; i < length; i ++) {
            if (coeffs[i] != 0) {
                c ++;
            }
        }
        double [] new_coeffs = new double[c];
        int [] new_exps = new int[c];
        
        int incr = 0;
        for (int i = 0; i < length; i ++) {
            if (coeffs[i] != 0) {
                new_coeffs[incr] = coeffs[i];
                new_exps[incr] = exps[i];
                incr ++;
            }
        }
        
        return new Polynomial(new_coeffs, new_exps);
    }
    
    public Polynomial multiply(Polynomial poly) {
        int len1 = this.coefficients.length;
        int len2 = poly.coefficients.length;
        int maxlength = len1 * len2;
        
        double [] product = new double[maxlength];
        int [] exponent_array = new int[maxlength];
        int incr = 0;
        
        // Multiply the coefficients:
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                product[incr] = this.coefficients[i] * poly.coefficients[j];
                exponent_array[incr] = this.exponents[i] + poly.exponents[j];
                incr ++;
            }
        }
        return remove_redundance(product, exponent_array, incr);
    }

    public double evaluate(double value) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(value, exponents[i]);
        }
        return result;
    }
    
    public boolean hasRoot(double value) {
        return evaluate(value) == 0;
    }
}
