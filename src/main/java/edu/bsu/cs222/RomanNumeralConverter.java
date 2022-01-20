package edu.bsu.cs222;

import java.util.Arrays;
import java.util.stream.Stream;

public class RomanNumeralConverter {
    public int convert(String numeral) throws Exception {
        validate(numeral);
        return convertToStream(numeral)
                .mapToInt(RomanNumeralDigit::getValue)
                .reduce(0, this::addDigits);
    }

    private int addDigits(int first, int second) {
        return first >= second ? first + second : second - first;
    }

    private Stream<RomanNumeralDigit> convertToStream(String numeral) {
        return Arrays.stream(numeral
                .toUpperCase()
                .split(""))
                .map(RomanNumeralDigit::valueOf);
    }

    private void validate(String numeral) throws Exception {
        if (numeral.length() > 0) {
            char[] validCharacters = {'i', 'I', 'v', 'V', 'x', 'X', 'l',
                    'L', 'c', 'C', 'd', 'D', 'm', 'M'};
            Arrays.sort(validCharacters);
            char[] chars = numeral.toCharArray();
            boolean caseMode = Character.isLowerCase(chars[0]);
            for (char character : chars) {
                if (caseMode != Character.isLowerCase(character)) {
                    throw new Exception("Inconsistent case");
                }
                if (Arrays.binarySearch(validCharacters,character) < 0) {
                    throw new Exception("Invalid character found in numeral");
                }
            }
        } else {
            throw new Exception("Numeral must have at least one character");
        }
    }
}
