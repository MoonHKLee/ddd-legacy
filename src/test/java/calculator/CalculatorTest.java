package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @DisplayName("빈 문자열 또는 null을 입력할 경우 0을 반환한다.")
    @NullAndEmptySource
    void inputEmptyStringOrNullReturnZero(String text) {
        assertThat(calculator.add(text)).isZero();
    }

    @Test
    @DisplayName("숫자 하나를 문자열로 입력할 경우 해당 숫자를 반환한다")
    void stringToInt() {
        assertThat(calculator.add("2")).isEqualTo(2);
    }

    @ParameterizedTest
    @DisplayName("숫자 두개를 컴마(,) 구분자로 입력할 경우 두 숫자의 합을 반환한다.")
    @CsvSource(value = {"1,2:3", "4,5:9"}, delimiter = ':')
    void comma(String text, int expected) {
        assertThat(calculator.add(text)).isSameAs(expected);
    }

    @Test
    @DisplayName("구분자를 컴마(,) 이외에 콜론(:)을 사용할 수 있다")
    void commaAndColons() {
        assertThat(calculator.add("1,2;3")).isSameAs(6);
    }

    @Test
    @DisplayName("//와 \n 문자 사이에 커스텀 구분자를 지정할 수 있다.")
    void customDelimiter() {
        assertThat(calculator.add("//!\n1!2!3")).isSameAs(6);
    }

    @ParameterizedTest
    @DisplayName("음수를 전달할 경우 IllegalArgumentException이 발생해야 한다")
    @ValueSource(strings = {"-1", "-1,2;3", "//!\n1!-2!3"})
    void negativeNumber(String text) {
        assertThatThrownBy(() -> {
            calculator.add(text);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
