class ComplexNumber {
    double re, im;

    ComplexNumber(double real, double imaginary) {
        this.re = real;
        this.im = imaginary;
    }

    double getReal() {
        return this.re;
    }

    double getImaginary() {
        return this.im;
    }

    double abs() {
        return Math.sqrt(this.re * this.re + this.im * this.im);
    }

    ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.re + other.re, this.im + other.im);
    }

    ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.re - other.re, this.im - other.im);
    }

    ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber((this.re * other.re) - (this.im * other.im), (this.re * other.im) + (this.im * other.re));
    }

    ComplexNumber divide(ComplexNumber other) {
        ComplexNumber result = this.multiply(other.conjugate());
        double scalar = Math.pow(other.abs(), 2);
        return new ComplexNumber(result.re / scalar, result.im / scalar);
    }

    ComplexNumber conjugate() {
        return new ComplexNumber(this.re, -this.im);
    }

    ComplexNumber exponentialOf() {
        double real = Math.exp(this.re) * Math.cos(this.im);
        double imag = Math.exp(this.re) * Math.sin(this.im);
        return new ComplexNumber(real, imag);
    }
}