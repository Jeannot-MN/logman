package com.jmn.logman.service.util.security;

public class PasswordCriterion {

    public static final int MINIMUM_LENGTH = 8;

    public enum Type {
        MINIMUM_LENGTH_CRITERIA("Password must have at least " + MINIMUM_LENGTH + " characters."),
        UPPERCASE_CRITERIA("Password must have at least one uppercase character."),
        LOWERCASE_CRITERIA("Password must have at least one lowercase character."),
        NUMERIC_CRITERIA("Password must have at least one numeric character."),
        SPECIAL_CHARACTERS_CRITERIA("Password must have at least one special character, like \"!@#$%^&*{}()`~\""),
        BLACKLISTED_CRITERIA("Password must be complex.");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    private final Type type;
    private final boolean valid;

    public PasswordCriterion(Type type, boolean valid) {
        this.type = type;
        this.valid = valid;
    }

    public Type getType() {
        return type;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "PasswordCriterion{" +
                "type=" + type +
                ", valid=" + valid +
                '}';
    }
}
