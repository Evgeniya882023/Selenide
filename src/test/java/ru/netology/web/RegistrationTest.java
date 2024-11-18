package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest {
    @BeforeEach //предварительная настройка
    public void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys("DELETE");

    }

    public String GenerateDate(long addDays) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate newDay = today.plusDays(addDays);
        return newDay.format(formatter);
    }

    @Test //успешное оформление заказа доставки карты
    public void successfullFormTest() {
        String planningDate = GenerateDate(4);
        $("[data-test-id='city'] input").setValue("Брянск");
        $("[data-test-id='date'] input").setValue(GenerateDate(4));
        $("[data-test-id='name'] input").setValue("Петрова-Иванова Анфиса");
        $("[data-test-id='phone'] input").setValue("+79012240154");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + planningDate));
        $(withText("Успешно!")).shouldBe(visible);
    }
}

