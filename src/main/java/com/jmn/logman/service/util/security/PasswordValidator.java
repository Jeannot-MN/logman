
package com.jmn.logman.service.util.security;

import static com.jmn.logman.service.util.security.PasswordCriterion.Type.LOWERCASE_CRITERIA;
import static com.jmn.logman.service.util.security.PasswordCriterion.Type.MINIMUM_LENGTH_CRITERIA;
import static com.jmn.logman.service.util.security.PasswordCriterion.Type.NUMERIC_CRITERIA;
import static com.jmn.logman.service.util.security.PasswordCriterion.Type.SPECIAL_CHARACTERS_CRITERIA;
import static com.jmn.logman.service.util.security.PasswordCriterion.Type.UPPERCASE_CRITERIA;

public class PasswordValidator {

    private static final String UPPERCASE = "(.*[A-Z].*)";
    private static final String LOWERCASE = "(.*[a-z].*)";
    private static final String NUMERIC = "(.*[0-9].*)";
    private static final String SPECIAL_CHARACTERS = "(.*[!,@,#,$,%,^,&,*,{,},(,),`,~].*$)";

    // TODO: Should we read this from config file and is this list long enough?
    /*private static final List<String> BLACKLISTED_PASSWORDS = Lists.newArrayList("123",
            "456",
            "789",
            "qwerty",
            "user",
            "admin",
            "password",
            "111",
            "222",
            "333",
            "444",
            "555",
            "666",
            "777",
            "888",
            "999",
            "nedbank",
            "avo",
            "auto",
            "vehicle",
            "dealer"
    );*/

//    private static final LevenshteinDistance BLACKLISTED_PASSWORD_COMPARATOR = new LevenshteinDistance(5);

    public static PasswordDecision execute(String password) {
        if (password != null) {
            PasswordDecision decision = new PasswordDecision();
            decision.setValid(true);

            checkForMinimumLength(password, decision);
            checkForUppercaseCharacter(password, decision);
            checkForLowercaseCharacter(password, decision);
            checkForNumericCharacter(password, decision);
            checkForSpecialCharacter(password, decision);
//            checkForBlacklistedPasswords(password, decision);

            return decision;
        } else {
            return nullPasswordDecision();
        }
    }

    private static void checkForMinimumLength(String password, PasswordDecision decision) {
        if (password.length() < PasswordCriterion.MINIMUM_LENGTH) {
            decision.setValid(false);
            decision.getCriteria().add(new PasswordCriterion(MINIMUM_LENGTH_CRITERIA, false));
        } else {
            decision.getCriteria().add(new PasswordCriterion(MINIMUM_LENGTH_CRITERIA, true));
        }
    }

    private static void checkForUppercaseCharacter(String password, PasswordDecision decision) {
        matchCriterionPattern(
                UPPERCASE_CRITERIA
                , UPPERCASE
                , password
                , decision
        );
    }

    private static void checkForLowercaseCharacter(String password, PasswordDecision decision) {
        matchCriterionPattern(
                LOWERCASE_CRITERIA
                , LOWERCASE
                , password
                , decision
        );
    }

    private static void checkForNumericCharacter(String password, PasswordDecision decision) {
        matchCriterionPattern(
                NUMERIC_CRITERIA
                , NUMERIC
                , password
                , decision
        );
    }

    private static void checkForSpecialCharacter(String password, PasswordDecision decision) {
        matchCriterionPattern(
                SPECIAL_CHARACTERS_CRITERIA
                , SPECIAL_CHARACTERS
                , password
                , decision
        );
    }


    /*private static void checkForBlacklistedPasswords(String password, PasswordDecision decision) {
        //  distance.apply("password", BLACKLISTED_PASSWORDS)   = 0
        //  distance.apply("Password", BLACKLISTED_PASSWORDS)   = 0
        //  distance.apply("P@ssword!", BLACKLISTED_PASSWORDS)  = 3
        //  distance.apply("P@ssw0rd!", BLACKLISTED_PASSWORDS)  = 4
        //  distance.apply("PASSWORD", BLACKLISTED_PASSWORDS)   = 0
        //  distance.apply("peter", BLACKLISTED_PASSWORDS)      = -1
        if (BLACKLISTED_PASSWORDS.stream()
                .anyMatch(e -> password.toLowerCase().contains(e))) {
            decision.setValid(false);
            decision.getCriteria().add(new PasswordCriterion(BLACKLISTED_CRITERIA, false));
        } else if (BLACKLISTED_PASSWORDS.stream()
                .map(e -> BLACKLISTED_PASSWORD_COMPARATOR.apply(e, password.toLowerCase()))
                .collect(Collectors.toList())
                .stream().anyMatch(score -> score >= 0)) {
            decision.setValid(false);
            decision.getCriteria().add(new PasswordCriterion(BLACKLISTED_CRITERIA, false));
        } else {
            decision.getCriteria().add(new PasswordCriterion(BLACKLISTED_CRITERIA, true));
        }
    }*/

    private static PasswordDecision nullPasswordDecision() {
        PasswordDecision decision = new PasswordDecision();

        decision.setValid(false);
        decision.getCriteria().add(new PasswordCriterion(MINIMUM_LENGTH_CRITERIA, false));

        decision.getCriteria().add(new PasswordCriterion(UPPERCASE_CRITERIA, false));

        decision.getCriteria().add(new PasswordCriterion(LOWERCASE_CRITERIA, false));

        decision.getCriteria().add(new PasswordCriterion(NUMERIC_CRITERIA, false));

        decision.getCriteria().add(new PasswordCriterion(SPECIAL_CHARACTERS_CRITERIA, false));

        return decision;
    }

    private static void matchCriterionPattern(PasswordCriterion.Type criterionType, String pattern, String password, PasswordDecision decision) {
        if (!password.matches(pattern)) {
            decision.setValid(false);
            decision.getCriteria().add(new PasswordCriterion(criterionType, false));
        } else {
            decision.getCriteria().add(new PasswordCriterion(criterionType, true));
        }
    }
}
