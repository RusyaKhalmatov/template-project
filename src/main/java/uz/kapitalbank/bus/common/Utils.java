package uz.kapitalbank.bus.common;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Rustam Khalmatov
 */


public class Utils {
    public static String generateRandomStringLower(int length) {
        Random RANDOM = new SecureRandom();
        String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}
