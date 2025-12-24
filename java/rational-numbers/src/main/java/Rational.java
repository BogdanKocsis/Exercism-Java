import java.util.Objects;

class Rational {
    private final int num;
    private final int den;

    Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }

        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }

        final int gcd = gcd(numerator, denominator);
        this.num = numerator / gcd;
        this.den = denominator / gcd;
    }

    int getNumerator() {
        return num;
    }

    int getDenominator() {
        return den;
    }

    Rational add(Rational other) {
        int lcm = lcm(den, other.den);
        return new Rational(
                (num * lcm / den) + (other.num * lcm / other.den),
                lcm);
    }

    Rational subtract(Rational other) {
        return add(new Rational(-other.num, other.den));
    }

    Rational multiply(Rational other) {
        return new Rational(num * other.num, den * other.den);
    }

    Rational divide(Rational other) {
        return multiply(new Rational(other.den, other.num));
    }

    Rational abs() {
        return new Rational(Math.abs(num), Math.abs(den));
    }

    Rational pow(int power) {
        if (power < 0) {
            return new Rational(den, num).pow(-power);
        }
        return new Rational((int) Math.pow(num, power), (int) Math.pow(den, power));
    }

    double exp(double base) {
        return Math.pow(base, approx());
    }

    @Override
    public String toString() {
        return String.format("%d/%d", this.getNumerator(), this.getDenominator());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rational other) {
            return this.getNumerator() == other.getNumerator()
                    && this.getDenominator() == other.getDenominator();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getNumerator(), this.getDenominator());
    }

    static private int gcd(int a, int b) {
        if (b == 0) return Math.abs(a);
        return gcd(b, a % b);
    }

    static private int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    private double approx() {
        return ((double) num) / den;
    }
}