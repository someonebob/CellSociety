package model;

/**
 * This class represents what might go wrong when using Rules.
 * 
 * @author Robert C. Duvall
 * @author Nathaniel Brooke
 */
public class RulesException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;


    /**
     * Create an exception based on an issue in our code.
     */
    public RulesException (String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public RulesException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public RulesException (Throwable cause) {
        super(cause);
    }
}
