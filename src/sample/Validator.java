package sample;

import java.util.regex.Pattern;

public class Validator {
    private static String patternString = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{16}";

    public boolean isValidPassword(String password) {
        return Pattern.matches(patternString, password);
    }

}
