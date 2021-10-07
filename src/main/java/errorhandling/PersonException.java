package errorhandling;

public class PersonException extends Exception {
    int code;

    public PersonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
