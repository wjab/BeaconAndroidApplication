package utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Eduardo on 12/5/2016.
 */
public class InputValidatorHelper {

    private final String EMAIL_PATTERN = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final String WORDS_ONLY_PATTERN = "([A-Za-z ])*";
    private final String USERNAME_PATTERN = "([_a-zA-Z||0-9])*";

    public boolean isValidEmail(String string){



        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches() && !isNullOrEmpty(string);
    }

    public boolean isValidWord(String string){

        Pattern pattern = Pattern.compile(WORDS_ONLY_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches() && !isNullOrEmpty(string);

    }

    public boolean isValidUsername(String string){

        Pattern pattern = Pattern.compile(USERNAME_PATTERN,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches() && !isNullOrEmpty(string) ;
    }


    public boolean isValidPassword(String string){
        if (!isNullOrEmpty(string) && string.length() >= 6) {
            return true;
        }
        return false;
    }

    public boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    public boolean isNumeric(String string){
        return TextUtils.isDigitsOnly(string) && !isNullOrEmpty(string);
    }


    //Add more validators here if necessary
}


