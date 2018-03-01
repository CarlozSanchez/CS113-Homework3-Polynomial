/**
 * @author Carlos Sanchez
 * @version 1.0
 * @since 2/10/2018
 * <p>
 * Term.java : Holds coefficient and exponent value for a term, in the form of +/-Cx^E
 * where C is a coefficient which can be plus or minus. E is th Exponent that can be plus or minus.
 * Term can be created using integer values (coefficient,Term) or String can be parsed to derive the
 * coefficient Term values in it.
 */

package edu.miracosta.cs113;

public class Term implements Cloneable, Comparable
{

    // Static Final variables
    private final static char VARIABLE_SYMBOL = 'x';
    private final static char EXPONENT_SYMBOL = '^';
    private final static char PLUS_SYMBOL = '+';
    private final static char MINUS_SYMBOL = '-';

    // Class variables
    private int coefficient;
    private int exponent;

    /**
     * Default constructor for Term class, sets both coefficient and exponent to integer value 1.
     */
    public Term()
    {
        this.setAll(1, 1);
    }

    /**
     * Full constructor for Term class, sets coefficient and exponent to given values.
     *
     * @param coefficient
     * @param exponent
     */
    public Term(int coefficient, int exponent)
    {
        this.setAll(coefficient, exponent);
    }

    /**
     * Copy constructor for Term class, copies coefficient and exponent from other and sets them
     * to local variables.
     *
     * @param other The term you want to copies the values from
     */
    public Term(Term other)
    {
        if (other == null)
        {
            throw new NullPointerException();
        }
        else
        {
            this.setAll(other.getCoefficient(), other.getExponent());
        }
    }

    /**
     * Full constructor using string input for both coefficient and exponent.
     * Precondition - String must be formatted correctly example(+5, +x, +x^3, -54x^-17).
     *
     * @param term The string to be parsed into coefficient and term values.
     */
    public Term(String term)
    {
        int coefficient, exponent;

        if (!term.isEmpty())
        {

            // Term contains variable
            if (term.contains(Character.toString(VARIABLE_SYMBOL)))
            {
                // Split string in between X variable
                String[] splitTerm = term.split(Character.toString(VARIABLE_SYMBOL));

                // Parse left side of variable X
                coefficient = parseCoefficientString(splitTerm[0]);

                // if right side of variable X is available
                if (splitTerm.length == 2)
                {
                    exponent = parseExponentString(splitTerm[1]);
                }
                else
                {
                    exponent = 1;
                }
            }
            else
            { // Term does not contain variable
                coefficient = parseCoefficientString(term);
                exponent = 0;
            }

        }
        else // String is empty assign both coefficient and exponent to zero
        {
            coefficient = exponent = 0;
        }

        this.setAll(coefficient, exponent);
    }

    /**
     * Helper Method for Constructor(String), parse coefficient side.
     *
     * @param str The string to be parsed into coefficient value.
     * @return Integer value representing coefficient.
     */
    private int parseCoefficientString(String str)
    {
        if (str.length() == 1 && str.charAt(0) == MINUS_SYMBOL)
        {
            return -1;
        }
        else if (str.length() == 1 && str.charAt(0) == PLUS_SYMBOL)
        {
            return 1;
        }
        else
        {
            return Integer.parseInt(str);
        }
    }

    /**
     * Helper method for Constructor(String), parse exponent side.
     *
     * @param str The string to be parsed into exponent value.
     * @return Integer value representing exponent.
     */
    private int parseExponentString(String str)
    {
        return Integer.parseInt(str.substring(1));
    }

    /**
     * Mutator for term coefficient, set to value given.
     *
     * @param value the integer value to set coefficient.
     */
    public void setCoefficient(int value)
    {
        this.coefficient = value;
    }

    /**
     * Mutator for term exponent, set to value given.
     *
     * @param value the integer value to set exponent.
     */
    public void setExponent(int value)
    {
        this.exponent = value;
    }

    /**
     * Mutator for both coefficient and exponent for term.
     *
     * @param coefficient the integer value to set coefficient.
     * @param exponent    the integer value to set exponent.
     */
    public void setAll(int coefficient, int exponent)
    {
        this.setCoefficient(coefficient);
        this.setExponent(exponent);
    }

    /**
     * Accessor for coefficient value.
     *
     * @return integer representing the coefficient value.
     */
    public int getCoefficient()
    {
        return this.coefficient;
    }

    /**
     * Accessor for exponent value.
     *
     * @return integer representing the exponent value.
     */
    public int getExponent()
    {
        return this.exponent;
    }

    /**
     * Clone method.
     *
     * @return
     */
    public Object clone()
    {
        try
        {
            return super.clone();
        } catch (CloneNotSupportedException e)
        {
            return null;
        }
    }

    /**
     * Comparable implementation for Term class. Compares the exponent value in this Term with other
     *
     * @param other
     * @return returns -1 if this exponent preceeds other
     * returns 1 if this exponent exceeds other
     * returns 0 if both exponents are equal
     */
    public int compareTo(Object other)
    {
        Term otherTerm = (Term) other;
        if (this.exponent > otherTerm.getExponent())
        {
            return 1;
        }
        else if (this.exponent < otherTerm.getExponent())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Equals method for comparing the values coefficient and exponent in Term class.
     *
     * @param other The object you want to compare this Term with.
     * @return boolean representing equivalency.
     */
    public boolean equals(Object other)
    {
        if (other == null || other.getClass() != this.getClass())
        {
            return false;
        }
        else
        {
            Term otherTerm = (Term) other;
            return this.coefficient == otherTerm.getCoefficient()
                    && this.exponent == otherTerm.getExponent();
        }
    }

    /**
     * Override toString() method for Term.
     *
     * @return String with both coefficient and exponent value.
     */
    public String toString()
    {
        String temp = "";

        // Will not run if coefficient is zero
        if (this.coefficient != 0)
        {

            // Handles Coefficient
            if (coefficient > 0)
            { // Adds plus symbol where needed
                temp += PLUS_SYMBOL;
                if (coefficient > 1)
                {
                    temp += this.coefficient;
                }
            }
            else
            { // Add minus symbol where needed
                if (coefficient == -1)
                {
                    temp += MINUS_SYMBOL;
                }
                else
                {
                    temp += this.coefficient;
                }
            }
            // Handles Exponent
            if (this.exponent != 0)
            {
                temp += VARIABLE_SYMBOL;

                if (this.exponent > 1 || this.exponent < -1)
                {
                    temp += EXPONENT_SYMBOL + "" + this.exponent;
                }
            }
        }
        return temp;
    }

    /**
     * Adds local term with given term if both exponents match, otherwise no sum is calculated and null is returned.
     * @param other The other term to add to local term.
     * @return A new Term with both coefficients added together, if exponents do NOT match then null will be returned.
     */
    public Term plus(Term other)
    {
        return sumOf(this, other);
    }

    /**
     * Adds two given terms together if their exponents match, otherwise no sum is calculated and null is returned.
     * @param termA The first term to add with termB.
     * @param termB The second term to add with termA.
     * @return A new Term with both coefficients added together, if exponents do NOT match then null will be returned.
     */
    public static Term sumOf(Term termA, Term termB)
    {
        Term temp = null;
        int coefficientSum;

        // if exponents match
        if (termA.exponent == termB.exponent)
        {
            // Add both coefficients
            coefficientSum = termA.coefficient + termB.coefficient;

            if (coefficientSum == 0)
            {
                return null;
            }
            else
            {
                temp = new Term(coefficientSum, termA.exponent);
            }

            //temp = new Term(termA.coefficient + termB.coefficient, termA.exponent);

        }

        return temp;
    }
}




/*

    ----- Dead Baby Pile -----

    public Term(String term) {
        boolean readingCoefficient;
        String coefficientToParse = "0";
        String exponentToParse = "0";
        int coefficient;
        int exponent;

        System.out.println("do ir each here");
        if (term.isEmpty()) {
            this.setAll(0, 0);
        } else {
            readingCoefficient = true;
            coefficientToParse = "";
            exponentToParse = "";

            for (int i = 0; i < term.length(); i++) {
                if (term.charAt(i) == VARIABLE_SYMBOL) {
                    readingCoefficient = false;
                    i ++;
                    if(i > term.length())
                    {
                        break;
                    }
                }

                if (readingCoefficient) {
                    coefficientToParse += term.charAt(i);
                } else {
                    exponentToParse += term.charAt(i);
                }
            }
        }
        System.out.println("Coefficient: " + coefficientToParse);
        System.out.println("Exponent: " + exponentToParse);

        if (coefficientToParse.isEmpty()) {
            coefficient = 0;
        }
        else if(coefficientToParse.equals("+"))
        {
            coefficient = 1;
        } else
        {
            coefficient = Integer.parseInt(coefficientToParse);
        }

        if (exponentToParse.isEmpty()) {
            exponent = 0;
        }
        else {
            exponent = Integer.parseInt(coefficientToParse);
        }

        this.setAll(coefficient, exponent);
        System.out.println("Above complete");
    }
 */