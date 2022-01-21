package edu.bsu.cs222;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RomanNumeralConverter {

    public int convert(String numeral) throws Exception {
        validate(numeral);
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

    private void validate(String numeral) throws Exception {
        checkForZeroLengthInput(numeral);
        checkForInconsistentCapitalization(numeral);
        checkForInvalidSubstringLength(numeral);
    }

    private void checkForInconsistentCapitalization(String numeral) throws Exception{
        char[] chars = numeral.toCharArray();
        boolean caseMode = Character.isLowerCase(chars[0]);
        for (char character : chars) {
            if (caseMode != Character.isLowerCase(character)) {
                throw new Exception("Inconsistent case");
            }
        }
    }

    private void checkForZeroLengthInput(String numeral) throws Exception {
        if (numeral.length() <= 0)
            throw new Exception("Numeral must have at least one character");
    }

    private void checkForInvalidSubstringLength(String numeral) throws Exception {
        int repeats = 1;
        char previous = numeral.charAt(0);
        for (char character:
             numeral.substring(1).toCharArray()) {
            if (character == previous) {
                repeats++;
            } else {
                if (repeats * RomanNumeralDigit.valueOf(Character.toString(previous)).getValue() >=
                        RomanNumeralDigit.valueOf(Character.toString(character)).getValue() &&
                        RomanNumeralDigit.valueOf(Character.toString(previous)).getValue() <
                        RomanNumeralDigit.valueOf(Character.toString(character)).getValue()) {
                    throw new Exception("Invalid numeral");
                } else {
                    repeats = 1;
                }
            }
            previous = character;
        }
    }
}
