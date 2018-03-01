/**
 * @author Carlos Sanchez
 * @version 1.01
 * @since 2/11/2001
 * <p>
 * Polynomial.java : holds a LinkedList of Terms which are ordered from highest to lower terms.  Polynomials can be
 * be added to local polynomial in which case any matching terms will be combine and the sum will take it's original place.
 * Individual terms can be added or removed as needed.
 */
package edu.miracosta.cs113;

import java.util.LinkedList;

public class Polynomial
{
    private final static char PLUS_SYMBOL = '+';

    // Class variables
    private LinkedList<Term> termList;

    /**
     * Default constructor, initializes termList to new empty linked list.
     */
    public Polynomial()
    {
        this.termList = new LinkedList();
    }


    /**
     * Copy constructor, initializes termlist to new empty linked list then copies over
     * every term from referenced polynomial.
     *
     * @param polynomial the polynomial you want to copy from.
     */
    public Polynomial(Polynomial polynomial)
    {
        this.termList = new LinkedList();

        if (polynomial != null)
        {
            for (int i = 0; i < polynomial.getNumTerms(); i++)
            {
                termList.add(new Term(polynomial.getTerm(i)));
            }
        }
    }

    /**
     * Ittereates through the given polynomial and adds each term to local polynomial using the addTerm(Term) method.
     *
     * @param polynomial The polynomial you would like to combine with this polynomial.
     */
    public void add(Polynomial polynomial)
    {
        for (int i = 0; i < polynomial.getNumTerms(); i++)
        {
            this.addTerm(polynomial.getTerm(i));
        }
    }


    /**
     * adds the given term to this polynomial in the correct position, going from highest exponent to lowest.
     * if given term's exponent has the same value as local term's exponent, then both terms will be added
     * together then the result would be inserted into the position occupied by local term, unless the coefficient
     * sum = 0 in which case the local term will be removed from the list.
     *
     * @param termToAdd the term you would like to add to this polynomial.
     */
    public void addTerm(Term termToAdd)
    {
        // local variable
        Term localTerm, currentTerm, nextTerm, sum;
        int lastIndex;

        // If list is empty, add term to index 0
        if (this.termList.size() == 0)
        {
            this.termList.add(termToAdd);
        }
        // else if only one element in list
        else if (this.termList.size() == 1)
        {
            localTerm = termList.get(0);
            if (termToAdd.compareTo(localTerm) == 1)
            {
                termList.add(0, termToAdd);
            }
            else if (termToAdd.compareTo(localTerm) == 0)
            {

                sum = termToAdd.plus(localTerm);
                if (sum != null)
                {
                    termList.set(0, sum);
                }
                else
                {
                    termList.remove(0);
                }
            }
            else
            {
                termList.add(termToAdd);
            }

        }
        else  // there is more then 1 element in list
        {
            // loop up too last index
            for (int i = 0; i < this.getNumTerms() - 1; i++)
            {
                currentTerm = this.getTerm(i);
                nextTerm = this.getTerm(i + 1);

                // if term to add exponent bigger then current term's exponent
                if (termToAdd.compareTo(currentTerm) == 1)
                {
                    termList.add(i, termToAdd);
                    return;
                }

                // else if term to Add Exponent matches current term's exponent
                else if (termToAdd.compareTo(currentTerm) == 0)
                {
                    sum = termToAdd.plus(currentTerm);
                    if (sum != null)
                    {
                        termList.set(i, sum);
                    }
                    else
                    {
                        termList.remove(i);
                    }
                    return;
                }

                // if term to add exponent is smaller then current term exponent
                // and larger then the next term exponent
                if (termToAdd.compareTo(currentTerm) == -1
                        && termToAdd.compareTo(nextTerm) == 1)
                {
                    // Insert term in between current and next
                    termList.add(i + 1, termToAdd);
                    return;
                }
            }

            // reached last index
            lastIndex = this.getNumTerms() - 1;
            localTerm = this.getTerm(lastIndex);

            if (termToAdd.compareTo(localTerm) == 0)
            {
                sum = termToAdd.plus(localTerm);
                if (sum != null)
                {
                    termList.set(lastIndex, sum);
                }
                else
                {
                    termList.remove(lastIndex);
                }
            }
            else
            {
                termList.add(termToAdd);
            }
        }
    }

    /**
     * Removes the term at the selected index.
     *
     * @param index the index to remove term from, which is checked prior to term removal.
     * @return a boolean, true if removal was successful, false if failed.
     */
    public Term remove(int index)
    {
        if (index < 0 || index > this.getNumTerms() - 1)
        {
            return null;
        }
        else
        {
            Term temp = new Term(this.getTerm(index));
            this.termList.remove(index);
            return temp;
        }
    }

    /**
     * Returns a deep copy of the term at the index given.
     *
     * @param index the location of the term you want to access.
     * @return a new Term from desired index.
     */
    public Term getTerm(int index)
    {
        return new Term(this.termList.get(index));
    }

    /**
     * Removes every Term from this polynomial's term list.  The term list will then have a size of 0.
     */
    public void clear()
    {
        this.termList.clear();
    }

    /**
     * The term count accessor for this polynomial.
     *
     * @return integer representing  term count in this polynomial.
     */
    public int getNumTerms()
    {
        return this.termList.size();
    }

    /**
     * Compares equality of two polynomials, both turms must have same term count and each term must
     * match other's term and exponent.
     *
     * @param other object to campre this polynomial againts.
     * @return a boolean representing equality.
     */
    @Override
    public boolean equals(Object other)
    {
        if (other == null || other.getClass() != this.getClass())
        {
            return false;
        }
        else
        {
            Polynomial otherPolynomial = (Polynomial) other;
            if (this.getNumTerms() != ((Polynomial) other).getNumTerms())
            {
                return false;
            }
            else
            {
                for (int i = 0; i < this.getNumTerms(); i++)
                {
                    if (this.getTerm(i).getCoefficient() != otherPolynomial.getTerm(i).getCoefficient()
                            || this.getTerm(i).getExponent() != otherPolynomial.getTerm(i).getExponent())
                    {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    /**
     * toString method for polynomial.
     *
     * @return String with all terms in termlist.
     */
    @Override
    public String toString()
    {
        String temp;

        if (this.termList == null || this.termList.size() == 0)
        {
            temp = "0";
        }
        else
        {
            temp = "";

            for (Term term : termList)
            {
                temp += term.toString();
            }

            if (temp.charAt(0) == PLUS_SYMBOL)
            {
                temp = temp.substring(1);
            }
        }

        return temp;
    }
}

/****
 *     --- Scratch Pad -----
 *     this was used in testAddPolynomials() after test1.add(test2);
 *     for debuggin purposes


 // Carlos debugging
 for(int i = 0; i < test1.getNumTerms(); i++)
 {
 System.out.println(i + ": " + test1.getTerm(i).getCoefficient() + "x^" +
 test1.getTerm(i).getExponent());
 }
 System.out.println("The term: " + test1);
 System.out.println("Num of terms: " + test1.getNumTerms());
 System.out.println("Term01: Coeff: " + test1.getTerm(1).getCoefficient()
 + "Exponent: " + test1.getTerm(1).getExponent());
 System.out.println("manual access: "+ test1.getTerm(0)); // My problem is accessing index 1 for some reason
 // Left of here !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 // OK Term of 0,0 is getting added to my list, cousing issues


 *
 */
