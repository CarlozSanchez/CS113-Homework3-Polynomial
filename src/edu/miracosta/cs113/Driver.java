/**
 * @author Carlos Sanchez
 * @version 1.01
 * <p>
 * Driver.java : This driver stores 2 polynomials which the user can edit then display the sum of their totals.
 * each polynomial can have terms added or removed as well as cleared. Once the use is satisfied
 * they can output the total without deleting the original polynomials that where created.
 **/

/*  -------------- PSEUDOCODE -------------------------------------
    Instantiate Polynomial as polyA
    Instantiate Polynomial as polyB
    Instantiate Main Menu

    --- Display Main Menu() ---
        " Polynomial A: - Empty - "
        " Polynomial B: - Empty - "

        " 1) Edit Polynomial A
        " 2) Edit Polynomial B
        " 3) Display Sum
        " 4) Exit Program "


    Read input as userChoice
    validate input between 1 - 4
    Switch ( userChoice)
        Case 1: editPolynomial(polyA : Polynomial)
        Case 2: editPolynomial(polyB : Polynomial)
        Case 3: displaySum(polyA : Polynomial, polyB: Polynomial)
        Case 4: exitProgram()


    --- editPolynomial(polynomial : Polynomial) ---
        Display subMenu
            " 1) add Term "
            " 2) remove Term "
            " 3) Clear Polynomial "
            " 4) main Menu "

        Read input as userChoice
        validate input between 1-4
            case 1: addTerm(polynomial
            case 2: removeTerm( polynomial)
            case 3: clearPolynomial( polynomial)
            case 4: mainMenu()

    --- addTerm(polynomial : Polynomial) ---
    Display current status of polynomial
    Print " Enter nothing to go back or
    Prompt user "Enter term coefficient: "
    Read input String
    Store as inputStr
    if inputStr is empty
        go back editPolynomial()
    validate(inputStr)
    assign as Coefficient

    Print " Enter nothing to go back or
    Prompt user "Enter term exponent: "
    Read input String
    Store as inputStr
    if inputStr is empty
        go back editPolynomial()
    validate(inputStr)
    assign as Exponent

    Create new Term(coefficient, exponent)
    add term to polynomial
    go back to editPolynomial()

    --- removeTerm(polynomial : Polynomial) ---
    Print  "polynomial.getTerm(0)" + polynomial.getTerm(1) ......)
    Print "term: 1                  Term: 2
    Prompt "Enter a polynomial to remove, or leave blank to go back"
    store input as input
    if(input is empty)
        go back to editTerm();
    validate termPosition lies between 1 and Polynomial size
    polynomial.removeTerm(termposition -1)
    start from top, removeTerm(polynomial)

    --- clearPolynomial(polynomial: Polynomial) ---
    Prompt "this will remove all tem fromp Polynomial are you sure? (y/n): "
        if (yes)
            call polynomial.clear()
        go back to editPolynomial

    --- displaySum(polyA: Polynomial, polyB: Polynomial) ---
    create new Polynomial called polySum which is a copy of polyA
    Add polyB to polyA  sum.add(polyB)
    Print "PolynomialA : " + polyA
    Print "PolynomialB : " + polyB
    Print " Sum : " + polySum

    --- validateInput(input: String, message: String) ---
    set boolean invalid as false
    while(invalid)
        try
            Integer parse input
            store integer as validInput
            return integer
        catch
            Print "Invalid input"
            Prompt message
            store input as input
    return validInput
 */

package edu.miracosta.cs113;

import java.util.Scanner;

public class Driver
{
    // Finals
    private static final String POLY_NAME_A = "Polynomial A: ";
    private static final String POLY_NAME_B = "Polynomial B: ";
    private static final int BUFFER_A = -8;

    // Static
    private static Polynomial polyA, polyB;
    private static Scanner keyboard;

    /**
     * Driver Starting point, prints author information and gets things started.
     *
     * @param args arguments are not used.
     */
    public static void main(String[] args)
    {
        // Instantiate variables
        polyA = new Polynomial();
        polyB = new Polynomial();

        // Header
        ConsoleForm.printHeader(3, "Polynomials", "2/15/2018");
        ConsoleForm.printTitle(60, "----- Polynomial Driver -----");

        // execute main menu
        mainMenu();
    }

    /**
     * Main menu for Driver, allows user to manipulate polynomials and display their sum.
     */
    private static void mainMenu()
    {
        // Declare
        int userChoice;
        String[] menuOptions;
        Menu mainMenu;

        // Initialize
        menuOptions = new String[]{"Edit Polynomial A", "Edit Polynomial B", "Display Sum", "Exit"};
        mainMenu = new Menu(menuOptions);
        keyboard = new Scanner(System.in);

        // Title
        ConsoleForm.printTitle(60, "--- Main Menu ---");

        // Display polynomials
        printPolynomial(POLY_NAME_A, polyA);
        printPolynomial(POLY_NAME_B, polyB);
        System.out.println();

        // Display menu
        mainMenu.printOptions();
        userChoice = mainMenu.prompt(keyboard);
        executeMainMenuChoice(userChoice);

    }

    /**
     * Executes the selected option from mainMenu.
     *
     * @param choice the options the user selected.
     */
    private static void executeMainMenuChoice(int choice)
    {
        switch (choice)
        {
            case 1:
                editPolynomial(POLY_NAME_A, polyA);
                break;

            case 2:
                editPolynomial(POLY_NAME_B, polyB);
                break;

            case 3:
                displaySum();
                break;

            case 4:
                keyboard.close();
                System.exit(0);

            default:
                System.out.println("Unknown State");
                break;
        }
    }

    /**
     * Allows the user to edits polynomials by adding term, removing term, or clearing polynomial.
     * final options is to go back to main menu.
     *
     * @param polyName for display purposes, the name of the polynomial to edit.
     * @param poly     the refeerence to the polynomial to edit.
     */
    private static void editPolynomial(String polyName, Polynomial poly)
    {
        // Declare local variables
        String[] menuOptions;
        Menu subMenu;
        int userChoice;

        // Initialize local variables
        menuOptions = new String[]{"Add Term", "Remove Term", "Clear Polynomial", "Main menu"};
        subMenu = new Menu(menuOptions);

        // Print header
        ConsoleForm.printTitle(60, "-- Edit Polynomial --");

        // print polynomial
        printPolynomial(polyName, poly);
        System.out.println();

        // print menu
        subMenu.printOptions();

        // prompt for choice
        userChoice = subMenu.prompt(keyboard);

        executeEditPolynomialChoice(userChoice, poly, polyName);
    }

    /**
     * Executes 1 of four choices.
     * 1) allows user to add a term to given polynomial.
     * 2) allows user to removed a term from given polynomial.
     * 3) allows user to clear all terms in given polynomial.
     *
     * @param choice   The user choice to execute.
     * @param poly     the referenced polynomial to edit.
     * @param polyName the name of the polynomial we are editing.
     */
    private static void executeEditPolynomialChoice(int choice, Polynomial poly, String polyName)
    {
        switch (choice)
        {
            case 1:
                addTerm(polyName, poly);
                break;

            case 2:
                removeTerm(polyName, poly);
                break;

            case 3:
                clearPolynomial(polyName, poly);
                break;

            case 4:
                mainMenu();
                break;

            default:
                System.out.println("Unknown State");
                break;
        }
    }

    /**
     * Prompts for term values which are validated then added to given polynomial.
     *
     * @param polyName The name of the polynomial we are adding to.
     * @param poly     The polynomial reference to add term too.
     */
    private static void addTerm(String polyName, Polynomial poly)
    {
        // Local variables
        String input;
        int coefficient, exponent;

        // Initialize variables


        // Print information
        ConsoleForm.printTitle(60, "- Add Term -");
        printPolynomial(polyName, poly);

        System.out.println("Leave input empty to go back.\n");

        // prompt user for Coefficient
        System.out.print("Enter term Coefficient: ");
        input = keyboard.nextLine();

        // check for empty string
        if (input.isEmpty())
        {
            editPolynomial(polyName, poly);
        }
        coefficient = validateInput("Enter a valid Coefficient value  ", input);

        // prompt user for Exponent
        System.out.print("Enter term Exponent: ");
        input = keyboard.nextLine();

        // check for empty string
        if (input.isEmpty())
        {
            editPolynomial(polyName, poly);
        }
        exponent = validateInput("Enter a valid Exponent value ", input);

        // Add term to polyNomial
        poly.addTerm(new Term(coefficient, exponent));

        // Start from top
        addTerm(polyName, poly);
    }

    /**
     * Removes a term from given polynomial, the user is able to select the index of the term to remove from
     * polynomial. The term is then removed and polynomial is updated and user is prompted again until they
     * chose to go back or polynomial is empty.
     *
     * @param polyName the name of the polynomial we are removing terms from.
     * @param poly     the referenced polynomial to remove terms from.
     */
    private static void removeTerm(String polyName, Polynomial poly)
    {
        // local variables
        String input;
        int index;
        Term removedTerm;

        // Print infomration
        ConsoleForm.printTitle(60, "- Remove Term -");
        printFormattedPolynomial(polyName, poly);
        printIndexes(poly);

        System.out.println("Leave Blank to go back or");
        System.out.print("Enter a Term choice to remove: ");
        input = keyboard.nextLine();
        if (input.isEmpty())
        {
            editPolynomial(polyName, poly);
        }
        else if (poly.getNumTerms() == 0)
        {
            System.out.println("No move Terms to remove, going back!!!!");
            editPolynomial(polyName, poly);

        }
        index = validateIndex(input, poly.getNumTerms());
        removedTerm = poly.remove(index - 1);

        if (removedTerm == null)
        {
            System.out.println("Unable to remove Term");
        }
        else
        {
            System.out.println(removedTerm + " was removed.");
        }

        removeTerm(polyName, poly);

    }

    /**
     * Prompts user if they want to remove all the terms in selected polynomial.
     * If user selects "yes" the polynomial is cleared, if "no" polynomial is not cleared.
     * In both cases the user is then returned to edit polynomial menu.
     *
     * @param polyName The polynomial name to display.
     * @param poly     The polynomial to clear.
     */
    private static void clearPolynomial(String polyName, Polynomial poly)
    {
        String input;
        System.out.print("This will remove all terms from " + polyName + ", are you sure(Y/N): ");
        input = keyboard.nextLine();
        if (input.equalsIgnoreCase("y"))
        {
            poly.clear();
            System.out.println(polyName + " was cleared");
        }
        else
        {
            System.out.println(polyName + " was not cleared");
        }

        editPolynomial(polyName, poly);
    }

    /**
     * Displays the sum of both Polynomial A and Polynomial B without destroying the contents of original.
     * if both polynomials are empty the sum will reflect that. afterwards the user is prompted to enter
     * any key to return to main menu.
     */
    private static void displaySum()
    {
        // local variable
        String temp;
        Polynomial polySum;

        // Title
        ConsoleForm.printTitle(60, "- Display Sum -");

        // calculate
        polySum = new Polynomial(polyA);
        polySum.add(polyB);

        // display results
        printPolynomial(POLY_NAME_A, polyA);
        printPolynomial(POLY_NAME_B, polyB);
        printPolynomial("Poly Sum:     ", polySum);
        System.out.println();

        // Prompt use to continue
        System.out.print("Press any key to continue...");
        temp = keyboard.nextLine();
        mainMenu();
    }

    /**
     * Precondition - maxRange must be 1 or higher.
     *
     * @param str      the string to parse/validate.
     * @param maxRange The max integer that str can be parsed as.
     * @return integer value parsed from str.
     */
    private static int validateIndex(String str, int maxRange)
    {
        String message = "Must enter a value of (1-" + maxRange + "): ";
        boolean notValid;
        int value;

        notValid = true;
        value = 0;

        while (notValid)
        {
            try
            {
                value = Integer.parseInt(str);
                if (value < 1 || value > maxRange)
                {
                    throw new IndexOutOfBoundsException();
                }
                notValid = false;
            } catch (IndexOutOfBoundsException e)
            {
                System.out.print("Invalid Range, " + message);
                str = keyboard.nextLine();
                System.out.println();
            } catch (NumberFormatException e)
            {
                System.out.print("Invalid Input, " + message);
                str = keyboard.nextLine();
                System.out.println();
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        return value;
    }

    /**
     * prints the polynomial contents.
     *
     * @param title      Title to be printed infront of polynomial.
     * @param polynomial the polynomial to format and print.
     */
    private static void printPolynomial(String title, Polynomial polynomial)
    {
        String temp;

        if (polynomial.getNumTerms() == 0)
        {
            temp = " - Empty - ";
        }
        else
        {
            temp = polynomial.toString();
        }

        System.out.println(String.format("%s%s", title, temp));
    }


    /**
     * formats and prints the polynomial given.
     *
     * @param title      Title to be printed infront of polynomial.
     * @param polynomial the polynomial to format and print.
     */
    private static void printFormattedPolynomial(String title, Polynomial polynomial)
    {
        String temp, str;
        int buffer;

        if (polynomial.getNumTerms() == 0)
        {
            temp = " - Empty - ";
        }
        else
        {
            temp = "";
            for (int i = 0; i < polynomial.getNumTerms(); i++)
            {
                temp += String.format("%" + BUFFER_A + "s", polynomial.getTerm(i));
            }

        }

        System.out.println(String.format("%s%s", title, temp));
    }

    /**
     * Prints the indexes of given polynomial for selection purposes.
     *
     * @param poly The polynomial to read indexes from.
     */
    private static void printIndexes(Polynomial poly)
    {
        System.out.println("Term  choice: " + indexPoly(poly));
    }

    /**
     * generates a string of indexes spaced out at a regular interval which will correspond with
     * another method that prints out the terms of a given polynomial.
     *
     * @param poly the polynomial to read the indexes from.
     * @return a formmated string of indexes.
     */
    private static String indexPoly(Polynomial poly)
    {
        String temp, str;
        int buffer;

        temp = "";

        for (int i = 0; i < poly.getNumTerms(); i++)
        {
            str = String.format("[%d]", i + 1);
            temp += String.format("%" + BUFFER_A + "s", str);
        }

        temp += "\n";
        return temp;
    }

    /**
     * Validates the strings has an integer value before returned prased integer.
     *
     * @param prompt The message to prompt if string not parsed to integer.
     * @param input  The string containing the value to parse.
     * @return A validated integer.
     */
    private static int validateInput(String prompt, String input)
    {
        // local variables
        boolean invalid;
        int value;

        // initialize
        invalid = true;
        value = 0;

        while (invalid)
        {
            try
            {
                value = Integer.parseInt(input);
                invalid = false;
            } catch (Exception e)
            {
                System.out.println("\nInvalid Input, only Integers allowed EX:(-45, 0, 56):");
                System.out.print(prompt);
                input = keyboard.nextLine();
            }
        }
        return value;
    }
}
