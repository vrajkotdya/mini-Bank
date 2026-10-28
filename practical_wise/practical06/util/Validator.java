package practical06.util;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");

    public static boolean isValidMobile(String mobile) {
        if (mobile == null) return false;
        return MOBILE_PATTERN.matcher(mobile.trim()).matches();
    }
}
