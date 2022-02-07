package edu.bsu.cs222;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RomanNumeralConverter {

    public int convert(String numeral) {
        validate(numeral);
        return addSequence(numeral);
    }

    public int addSequence(String numeral) {
        return convertToIntStream(numeral)
                .reduce(0, this::addDigits);
    }

    private int addDigits(int first, int second) {
        return first >= second ? first + second : second - first;
    }

    private IntStream convertToIntStream(String numeral) {
        return convertToDigitStream(numeral)
                .mapToInt(RomanNumeralDigit::getValue);
    }

    private Stream<RomanNumeralDigit> convertToDigitStream(String numeral) {
        return Arrays.stream(numeral
                .toUpperCase()
                .split(""))
                .map(RomanNumeralDigit::valueOf);
    }

    private void validate(String numeral) throws RuntimeException {
        checkForZeroLengthInput(numeral);
        checkForInconsistentCapitalization(numeral);
        checkForInvalidSubstringLength(numeral);
    }

    private void checkForInconsistentCapitalization(String numeral) throws RuntimeException{
        char[] chars = numeral.toCharArray();
        boolean caseMode = Character.isLowerCase(chars[0]);
        for (char character : chars) {
            if (caseMode != Character.isLowerCase(character)) {
                throw new RuntimeException("Inconsistent case");
            }
        }
    }

    private void checkForZeroLengthInput(String numeral) throws RuntimeException {
        if (numeral.length() <= 0) {
            throw new RuntimeException("Numeral must have at least one character");
        }
    }

    private void checkForInvalidSubstringLength(String numeral) throws RuntimeException {
        // the regex here matches all groups of contiguous characters. Thanks Stackoverflow!
        Arrays.stream(numeral.toUpperCase().split("(?<=(.))(?!\\1)"))
                .filter(group -> !group.startsWith("M"))
                .forEach(group -> {
                    RomanNumeralDigit currentDigit = RomanNumeralDigit.valueOf(group.substring(0,1));
                    if (addSequence(group) >= nextDigit(currentDigit.getValue())) {
                        throw new RuntimeException("Invalid substring in numeral");
                    }
                });
    }

    public int nextDigit(int digit) {
        return Integer.toString(digit).startsWith("5") ? digit * 2 : digit * 5;
    }
}
