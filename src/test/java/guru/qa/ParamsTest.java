package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.Arrays.asList;


public class ParamsTest {

    @ValueSource(strings = {"JUnit 5", "TestNG"})
    @ParameterizedTest(name = "При поиске в яндексе по запросу {0} в результатах отображается текст {0}")
    void yaTestCommon(String testData) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        $$("li.serp-item").find(text(testData)).shouldBe(visible);
    }

//    @CsvFileSource(resources = "test_data/1.csv")
    @CsvSource(value = {
            "JUnit 5, team’s statement on the war in Ukraine",
            "TestNG, is a testing framework inspired from JUnit and NUnit"
    })
    @ParameterizedTest(name = "При поиске в яндексе по запросу {0} в результатах отображается текст {1}")
    void yaTestComplex(String searchData, String expectedResult) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(searchData);
        $("button[type='submit']").click();
        $$("li.serp-item").find(text(expectedResult)).shouldBe(visible);
    }

    static Stream<Arguments> yaTestVeryComplexDataProvider() {
        return Stream.of(
                Arguments.of("JUnit 5", asList("JUnit 5", "framework")),
                Arguments.of("TestNG", asList("JUnit", "framework"))
        );
    }

    @MethodSource(value = "yaTestVeryComplexDataProvider")
    @ParameterizedTest(name = "При поиске в яндексе по запросу {0} в результатах отображается текст {1}")
    void yaTestVeryComplex(String searchData, List<String> expectedResult) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(searchData);
        $("button[type='submit']").click();
        $$("li.serp-item").shouldHave(CollectionCondition.texts(expectedResult));
    }

    @EnumSource(Sex.class)
    @ParameterizedTest
    void enumTest(Sex sex) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(sex.desc);
        $("button[type='submit']").click();
        $$("li.serp-item").find(text(sex.desc)).shouldBe(visible);
    }


    public String toString() {
        return super.toString();
    }
}
