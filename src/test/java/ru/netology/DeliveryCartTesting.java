package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class DeliveryCartTesting {
    LocalDate date = LocalDate.now().plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void testShouldBeSuccessful() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Челябинск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Елена Долгополова");
        $("[data-test-id='phone'] input").setValue("+79227576995");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $(withText("Успешно!")).shouldBe(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + formatter.format(date)));
    }

    @Test
    void testWithUncorrectCity() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Миасс");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Елена Долгополова");
        $("[data-test-id='phone'] input").setValue("+79227576995");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void testWithoutCity() {
        open("http://localhost:9999");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Елена Долгополова");
        $("[data-test-id='phone'] input").setValue("+79227576995");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void testWithoutName() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Челябинск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79227576995");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void testWithUncorrectName() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Челябинск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Yelena Dolgopolova");
        $("[data-test-id='phone'] input").setValue("+79227576995");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void testWithoutPhone() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Челябинск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Елена Долгополова");
        $("[data-test-id='phone'] input").setValue("");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void testWithUncorrectPhone() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Челябинск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Елена Долгополова");
        $("[data-test-id='phone'] input").setValue("89227576995");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void testWithoutCheckBox() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Челябинск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Елена Долгополова");
        $("[data-test-id='phone'] input").setValue("+79227576995");
        $("[class=button__text]").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
