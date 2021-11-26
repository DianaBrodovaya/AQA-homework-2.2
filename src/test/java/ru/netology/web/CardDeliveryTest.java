package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendForm() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldValidateWithNotAdministrativeCentre() {
        $("[data-test-id=city] input").setValue("Бердск");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldValidateCityWithLatinLetters() {
        $("[data-test-id=city] input").setValue("Moscow");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldValidateEmptyCity() {
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldValidateEarlyDate() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("20.11.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] span.input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldValidateInvalidDate() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("31.13.5656");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] span.input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldValidateEmptyDate() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] span.input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldValidateNameWithLatinLetters() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Ivan");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldValidateNameWithNumbers() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван777");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldValidateNameWithForbiddenSymbols() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван&^%$*");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldValidateNameWithHyphens() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров-Водкин");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldValidateEmptyName() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldValidatePhoneFieldWithoutPlus() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("79000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldValidatePhoneFieldWithTooManyNumbers() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+7900000000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldValidatePhoneFieldWithTooFewNumbers() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+7900");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldValidateEmptyPhone() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldValidateAgreement() {
        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").doubleClick().sendKeys("Backspace");
        $("[data-test-id=date] input").setValue("15.12.2021");
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }

    @Test
    void shouldValidateWithAllEmptyFields() {
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

}
