package ir.iconish.khandevaneh.exception;

/**
 * Created by hamidreza on 5/13/17.
 */

public class InValidDestinationException extends Exception {

    public InValidDestinationException(String destination, String param) {
        super("Invalid Destination - Dest: " + destination + ", Param: " + param);
    }

}
