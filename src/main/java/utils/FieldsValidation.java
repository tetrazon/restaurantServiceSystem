package utils;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldsValidation {

    private static Logger logger = Logger.getLogger(FieldsValidation.class.getName());
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String NAME_PATTERN = "[\\w+]";

    private static Pattern pattern;
    private static Matcher matcher;

    public static boolean validateEmail(String email){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        boolean result = matcher.matches() && email.length()<=30;
        if (!result){
            logger.info("email mismatch");
        }
        return result;
    }

    public static boolean validateFLName(String fLname){
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(fLname);
        boolean result = matcher.matches() && fLname.length() > 2 && fLname.length() <= 20;
        if (!result){
            logger.info("fLname mismatch");
        }
        return result;
    }






}
