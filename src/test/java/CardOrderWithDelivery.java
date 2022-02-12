import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CardOrderWithDelivery {

    String getDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    ;

//    WebDriver driver;

    @BeforeEach
    public void setUp() {
//        holdBrowserOpen = true;
//        driver = new ChromeDriver();
//        browserSize = "1000x900";
        open("http://localhost:9999");  // вопрос: open("http://0.0.0.0:9999");
    }

    @Test
    public void shouldSendSimple() {

        String planningDate = getDate(4);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Ванечка");
        $("[data-test-id='phone'] input").setValue("+79998887744");
        $("[data-test-id='agreement']").click();

        $(withText("Забронировать")).click();
//        $("[data-test-id='notification']").shouldHave(Condition.visible, Duration.ofSeconds(15));
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    public void shouldSendWithDash() {

        $("[data-test-id='city'] input").setValue("Ростов-на-Дону");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(30));
        $("[data-test-id='name'] input").setValue("Иванов-Московский");
        $("[data-test-id='phone'] input").setValue("+09998887744");
        $("[data-test-id='agreement']").click();

        $(withText("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldSendWithSpace() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("+49998887744");
        $("[data-test-id='agreement']").click();

        $(withText("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(Condition.visible, Duration.ofSeconds(15));
    }

    // негативные тесты

    @Test
    public void shouldSendCityEmpty() {

        $("[data-test-id='city'] input").setValue("");
        $(withText("Забронировать")).click();
        $("[data-test-id='city'] span").shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldSendCityEng() {

        $("[data-test-id='city'] input").setValue("Moscow");
        $(withText("Забронировать")).click();
        $("[data-test-id='city'] span").shouldHave(Condition.text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldSendCityNotAdmCenter() {

        $("[data-test-id='city'] input").setValue("Реутов");
        $(withText("Забронировать")).click();
        $("[data-test-id='city'] span").shouldHave(Condition.text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void shouldSendWithoutData() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] span").shouldHave(Condition.text("Неверно введена дата"));
    }

    @Test
    public void shouldSendTodayData() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(0));
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] span").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldSendTodayPlus2() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(2));
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] span").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldSendFIOEmpty() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(3));
        $("[data-test-id='name'] input").setValue("");
        $(withText("Забронировать")).click();
        $("[data-test-id='name'] span").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldSendFIOEng() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(3));
        $("[data-test-id='name'] input").setValue("Ivanov");
        $(withText("Забронировать")).click();
        $("[data-test-id='name'] span").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldSendFIONumber() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(3));
        $("[data-test-id='name'] input").setValue("Николай 2");
        $(withText("Забронировать")).click();
        $("[data-test-id='name'] span").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldSendPhoneEmpty() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("");
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'] span").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldSendPhoneNotPlus() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("71112223344");
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'] span").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendPhoneManyNumbers() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("+711122233448");
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'] span").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendWithoutAgreement() {

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("+71112223344");
//        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
//        $("[data-test-id='agreement'].input_invalid").shouldBe(); //? .input_invalid.input_sub
//        $("[data-test-id='agreement']").name().contains("input_invalid");
//        $("[data-test-id='agreement']").data("input_invalid");
        $("[class='checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid']").shouldBe();
    }
}
