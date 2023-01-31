package com.jmn.logman.service.util.security;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGenerator {

    public static String generate() {
        String password = generateRandomWord();
        if (!PasswordValidator.execute(password).isValid()) {
            return generate();
        }
        return password;
    }

    private static String generateRandomWord() {
        String specialCharacters = RandomStringUtils.random(1, '@', '#', '$', '%');
        String upperCaseLetters = RandomStringUtils.random(5, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(5, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(5);
        String totalChars = RandomStringUtils.randomAlphanumeric(20);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(totalChars)
                .concat(specialCharacters);
        List<Character> resultChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(resultChars);
        return resultChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
