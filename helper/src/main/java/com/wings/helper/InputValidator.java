package com.wings.helper;

import android.util.Patterns;

/**
 * Purpose: Helper class to validate inputs
 *
 * @author NikunjD
 * Created on March 11, 2019
 * Modified on March 11, 2019
 */
public class InputValidator {

    /**
     * Find out the object is empty.
     *
     * @param value general input
     * @return boolean
     */
    public boolean isEmpty(Object value) {
        return isEmpty(value, false);
    }


    /**
     * Check if object is empty or not, null, blank, 0, false is empty
     * depends on data type or how the object can be casted.
     *
     * @param value         needs to be checked
     * @param isIgnoreSpace is include space or not
     * @return boolean
     */
    public boolean isEmpty(Object value, boolean isIgnoreSpace) {
        if (value == null) {
            return true;
        } else if (value instanceof Boolean) {
            return !((Boolean) value);
        } else if (value instanceof String) {
            if (isIgnoreSpace) {
                return String.valueOf(value).trim().isEmpty();
            }
            return String.valueOf(value).isEmpty();
        } else {
            try {
                int result = Integer.parseInt(value.toString());
                return result == 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }


    /**
     * Check if email string is valid format.
     *
     * @param email input string
     * @return boolean email format validation
     */
    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**
     * Check if string accept as url format.
     *
     * @param url input string
     * @return boolean url format is valid or not
     */
    public boolean isValidUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }


    /**
     * Check if phone string is valid format. It will allow minimum 6 digit and maximum 13 digit
     *
     * @param phone input string
     * @return boolean phone number format is valid or not
     */
    public boolean isValidPhone(String phone) {
        boolean isValid = false;
        if (Patterns.PHONE.matcher(phone).matches()) {
            isValid = phone.length() >= 6 && phone.length() <= 13;
        }
        return isValid;
    }


    /**
     * Check if phone string is valid format
     *
     * @param phone    input string
     * @param minDigit minimum digit to allow
     * @param maxDigit maximum digit to allow
     * @return boolean phone number format is valid or not
     */
    public boolean isValidPhone(String phone, int minDigit, int maxDigit) {
        boolean isValid = false;
        if (Patterns.PHONE.matcher(phone).matches()) {
            isValid = phone.length() >= minDigit && phone.length() <= maxDigit;
        }
        return isValid;
    }


    /**
     * Check if string only contains alpha dash numeric underscore and hyphen.
     *
     * @param value input string
     * @return boolean
     */
    public boolean isAlphaDash(String value) {
        return value.matches("^[a-zA-Z0-9-_]*$");
    }

    /**
     * Check if string only contain alpha dash.
     *
     * @param value input string
     * @return boolean
     */
    public boolean isAlphaNumeric(String value) {
        return value.matches("^[a-zA-Z0-9]*$");
    }

    /**
     * Person name limit character alphabet and some punctuations.
     *
     * @param value input string
     * @return boolean
     */
    public boolean isPersonName(String value) {
        return value.matches("^[a-zA-Z '.,]*$");
    }


    /**
     * Check if input string only contain signed numeric.
     *
     * @param value input string
     * @return boolean
     */
    public boolean isNumeric(Object value) {
        return isNumeric(value, false);
    }


    /**
     * Check if input string only contain numeric signed and unsigned number depends on parameter.
     *
     * @param value        input string
     * @param isSignedOnly is allow signed number only or both
     * @return boolean
     */
    public boolean isNumeric(Object value, boolean isSignedOnly) {
        try {
            int result = Integer.parseInt(value.toString());
            return !isSignedOnly || result >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Check is input has minimum characters including space.
     *
     * @param value    input string
     * @param minValue min length of characters
     * @return boolean
     */
    public boolean minLength(String value, int minValue) {
        return minLength(value, minValue, false);
    }

    /**
     * Check is input has minimum characters.
     *
     * @param value       input string
     * @param minValue    min length of characters
     * @param ignoreSpace count character without space or empty string
     * @return boolean
     */
    public boolean minLength(String value, int minValue, boolean ignoreSpace) {
        if (ignoreSpace) {
            return String.valueOf(value).trim().length() >= minValue;
        }
        return String.valueOf(value).length() >= minValue;
    }

    /**
     * Check is input reach maximum characters.
     *
     * @param value    input string
     * @param maxValue number of maximum characters
     * @return boolean
     */
    public boolean maxLength(String value, int maxValue) {
        return maxLength(value, maxValue, false);
    }

    /**
     * Check is input reach maximum characters.
     *
     * @param value       input string
     * @param maxValue    number of maximum characters
     * @param ignoreSpace count characters exclude space or empty string
     * @return boolean
     */
    public boolean maxLength(String value, int maxValue, boolean ignoreSpace) {
        if (ignoreSpace) {
            return String.valueOf(value).trim().length() <= maxValue;
        }
        return String.valueOf(value).length() <= maxValue;
    }

    /**
     * Check character in range minimum and maximum characters.
     *
     * @param value    input string
     * @param minValue number of minimum characters
     * @param maxValue number of maximum characters
     * @return boolean
     */
    public boolean rangeLength(String value, int minValue, int maxValue) {
        return rangeLength(value, minValue, maxValue, false);
    }

    /**
     * Check character in range minimum and maximum characters with ignoring
     * space and empty string option.
     *
     * @param value         input string
     * @param minValue      number of minimum characters
     * @param maxValue      number of maximum characters
     * @param isIgnoreSpace ignoring space or empty string
     * @return boolean
     */
    public boolean rangeLength(String value, int minValue, int maxValue, boolean isIgnoreSpace) {
        String string = String.valueOf(value);
        if (isIgnoreSpace) {
            return string.trim().length() >= minValue && string.trim().length() <= maxValue;
        }
        return string.length() >= minValue && string.length() <= maxValue;
    }

    /**
     * Check minimum integer value.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @return boolean
     */
    public boolean minValue(int value, int minValue) {
        return value >= minValue;
    }

    /**
     * Check minimum float value.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @return boolean
     */
    public boolean minValue(float value, float minValue) {
        return value >= minValue;
    }

    /**
     * Check minimum double value.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @return boolean
     */
    public boolean minValue(double value, double minValue) {
        return value >= minValue;
    }

    /**
     * Check maximum integer value.
     *
     * @param value    input string
     * @param maxValue number of maximum value
     * @return boolean
     */
    public boolean maxValue(int value, int maxValue) {
        return value <= maxValue;
    }

    /**
     * Check maximum float value.
     *
     * @param value    input string
     * @param maxValue number of maximum value
     * @return boolean
     */
    public boolean maxValue(float value, float maxValue) {
        return value <= maxValue;
    }

    /**
     * Check maximum double value.
     *
     * @param value    input string
     * @param maxValue number of maximum value
     * @return boolean
     */
    public boolean maxValue(double value, double maxValue) {
        return value <= maxValue;
    }

    /**
     * Check integer value in range.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @param maxValue number of maximum value
     * @return boolean
     */
    public boolean rangeValue(int value, int minValue, int maxValue) {
        return value >= minValue && value <= maxValue;
    }

    /**
     * Check float value in range.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @param maxValue number of maximum value
     * @return boolean
     */
    public boolean rangeValue(float value, float minValue, float maxValue) {
        return value >= minValue && value <= maxValue;
    }


    /**
     * Check double value in range.
     *
     * @param value    input string
     * @param minValue number of minimum value
     * @param maxValue number of maximum value
     * @return boolean
     */
    public boolean rangeValue(double value, double minValue, double maxValue) {
        return value >= minValue && value <= maxValue;
    }


    /**
     * Check if string is not member of collection data.
     *
     * @param value   input string
     * @param dataSet collection of string data
     * @return boolean
     */
    public boolean isUnique(String value, String[] dataSet) {
        for (String data : dataSet) {
            if (data.equals(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if integer is not member of collection data.
     *
     * @param value   input integer
     * @param dataSet collection of integer data
     * @return boolean
     */
    public boolean isUnique(int value, int[] dataSet) {
        for (int data : dataSet) {
            if (data == value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if a string member of data set, ignoring number of data matched.
     *
     * @param value   input string
     * @param dataSet collection of string data
     * @return boolean
     */
    public boolean isMemberOf(String value, String[] dataSet) {
        for (String data : dataSet) {
            if (data.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a integer member of data set, ignoring number of data matched.
     *
     * @param value   input string
     * @param dataSet collection of integer data
     * @return boolean
     */
    public boolean isMemberOf(int value, int[] dataSet) {
        for (int data : dataSet) {
            if (data == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Custom rule by passing regular expression.
     *
     * @param value input string
     * @param regex rule
     * @return boolean
     */
    public boolean isValid(String value, String regex) {
        return value.matches(regex);
    }

    /**
     * Build slug from string title like "The beautiful day in 2019" turns "the-beautiful-day-in-2019"
     *
     * @param title article title
     * @return slug string
     */
    public String createSlug(String title, String replaceWith, boolean isLowerCase) {
        String trimmed = title.trim();
        String slug = trimmed
                .replaceAll("[^a-zA-Z0-9-]", replaceWith)
                .replaceAll("-+", replaceWith)
                .replaceAll("^-|-$", "");

        if (isLowerCase) {
            slug = slug.toLowerCase();
        }

        return slug;
    }

}
