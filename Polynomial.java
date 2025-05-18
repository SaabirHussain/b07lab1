public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    }
    
    public Polynomial(double [] input_coeff) {
        coefficients = new double[input_coeff.length];
        for (int i = 0; i < input_coeff.length; i++) {
            coefficients[i] = input_coeff[i];
        }
    }
    
    public Polynomial add(Polynomial poly) {
        int maxlength = Math.max(this.coefficients.length, poly.coefficients.length);
        double [] sum_coefficient = new double[maxlength];
        
        // Add the coefficients:
        for (int i = 0; i < maxlength; i++) {
            if (i < this.coefficients.length && i < poly.coefficients.length) {
                sum_coefficient[i] = this.coefficients[i] + poly.coefficients[i];
            }
            else if (i >= this.coefficients.length && i < poly.coefficients.length) {
                sum_coefficient[i] = poly.coefficients[i];
            }
            else {
                sum_coefficient[i] = this.coefficients[i];
            }
        }
        return new Polynomial(sum_coefficient);
    }

    public double evaluate(double value) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(value, i);
        }
        return result;
    }
    
    public boolean hasRoot(double value) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(value, i);
        }
        return result == 0;
    }
}
