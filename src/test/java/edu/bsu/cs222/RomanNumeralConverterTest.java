package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class RomanNumeralConverterTest {
    RomanNumeralConverter converter = new RomanNumeralConverter();

    @ParameterizedTest
    @CsvSource({"I,1","i,1","V,5","v,5","X,10","x,10","L,50","l,50",
            "C,100","c,100","D,500","d,500","M,1000","m,1000",
            "II,2","XX,20","III,3","IV,4","VI,6","IX,9","VX,5",
            "XI,11","XV,15","IVX,6","VIX,4"})
    public void testConverter(String numeral, int expected) {
        int actual = converter.convert(numeral);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"Mearns","iI","IIIIIV","VVX","XVV","VIIIII"})
    @NullAndEmptySource
    public void testInvalidInput(String numeral) {
        Assertions.assertThrows(RuntimeException.class, () -> converter.convert(numeral));
    }

    @ParameterizedTest
    @CsvSource({"1,5", "5,10", "10,50", "50,100"})
    public void testNextDigit(int digit, int expected) {
        Assertions.assertEquals(expected, converter.nextDigit(digit));
    }
}
