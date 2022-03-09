package com.company;

import java.util.Objects;

public class RationalNumber implements Comparable<RationalNumber> {
    private int numerator;
    private int denominator;
    private boolean isPositive;

    public RationalNumber(int numerator, int denominator) {
        if(denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot equal to zero!");
        }
        if(numerator == 0) {
            this.numerator = 0;
            this.denominator = 1;
            this.isPositive = true;
            return;
        }
        this.numerator = numerator;
        this.denominator = denominator;
        countSign();
    }

    public RationalNumber add(RationalNumber addend) {
        if(numerator == 0) {
            return addend.copy();
        }
        if(addend.numerator == 0) {
            return copy();
        }
        RationalNumber result = copy();
        result.prepare(addend);
        result.numerator = addend.isPositive
                ? result.numerator + addend.numerator
                : result.numerator - addend.numerator;
        addend.simplify();
        result.countSign();
        result.simplify();
        return result;
    }

    public RationalNumber subtract(RationalNumber subtrahend) {
        subtrahend.isPositive = !subtrahend.isPositive;
        RationalNumber result = add(subtrahend);
        subtrahend.isPositive = !subtrahend.isPositive;
        return result;
    }

    public RationalNumber multiply(RationalNumber factor) {
        if(numerator == 0 || factor.numerator == 0) {
            return new RationalNumber(0, 1);
        }
        RationalNumber result = copy();
        result.numerator *= factor.numerator;
        result.denominator *= factor.denominator;
        result.isPositive = result.isPositive == factor.isPositive;
        result.simplify();
        return result;
    }

    public RationalNumber divide(RationalNumber dividend) {
        if(dividend.numerator == 0) {
            throw new IllegalArgumentException("Division by zero!");
        }
        int temp;
        temp = dividend.numerator;
        dividend.numerator = dividend.denominator;
        dividend.denominator = temp;
        RationalNumber result = multiply(dividend);
        dividend.numerator = dividend.denominator;
        dividend.denominator = temp;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!isPositive) {
            sb.append("- ");
        }
        sb.append(numerator)
                .append(" / ")
                .append(denominator);
        return sb.toString();
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public int compareTo(RationalNumber o) {
        RationalNumber result = subtract(o);
        if(!result.isPositive) {
            return -1;
        }
        if(result.numerator == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RationalNumber that = (RationalNumber) o;
        return numerator == that.numerator && denominator == that.denominator && isPositive == that.isPositive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator, isPositive);
    }

    private void prepare(RationalNumber secondNumber) {
        int lcm = lcm(denominator, secondNumber.denominator);
        numerator = numerator * (lcm / denominator);
        secondNumber.numerator = secondNumber.numerator * (lcm / secondNumber.denominator);
        denominator = lcm;
        secondNumber.denominator = lcm;
    }

    private void simplify() {
        if(numerator == 0) {
            denominator = 1;
            isPositive = true;
            return;
        }
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    private int gcd(int number1, int number2) {
        if(number1 < number2) {
            int temp = number1;
            number1 = number2;
            number2 = temp;
        }
        if(number1 % number2 == 0) {
            return number2;
        }
        number1 = number1 % number2;
        return gcd(number2, number1);
    }

    private int lcm(int number1, int number2) {
        return number1 * number2 / gcd(number1, number2);
    }

    private void countSign() {
        if(numerator == 0) {
            isPositive = true;
            return;
        }
        isPositive = (numerator > 0) == (denominator > 0);
        numerator = Math.abs(numerator);
        denominator = Math.abs(denominator);
    }

    private RationalNumber copy() {
        RationalNumber rationalNumber = new RationalNumber(numerator, denominator);
        rationalNumber.isPositive = isPositive;
        return rationalNumber;
    }

}
