import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;


public class CardOrderWithDelivery {

//    WebDriver driver;

    @BeforeAll
    static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
//        holdBrowserOpen = true;
//        driver = new ChromeDriver();
//        browserSize = "1000x900";
        open("http://localhost:9999");  // вопрос: open("http://0.0.0.0:9999");
    }

    @Test
    public void shouldSendSimple() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Москва");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(7));
        $("[data-test-id='name'] input").setValue("Иванов Ванечка");
        $("[data-test-id='phone'] input").setValue("+79998887744");
        $("[data-test-id='agreement']").click();

        $(withText("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldSendWithDash() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Ростов-на-Дону");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(30));
        $("[data-test-id='name'] input").setValue("Иванов-Московский");
        $("[data-test-id='phone'] input").setValue("+09998887744");
        $("[data-test-id='agreement']").click();

        $(withText("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldSendWithSpace() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(3));
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
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] span").shouldHave(Condition.text("Неверно введена дата"));
    }

    @Test
    public void shouldSendTodayData() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(0));
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] span").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldSendTodayPlus2() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(2));
        $(withText("Забронировать")).click();
        $("[data-test-id='date'] span").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldSendFIOEmpty() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(3));
        $("[data-test-id='name'] input").setValue("");
        $(withText("Забронировать")).click();
        $("[data-test-id='name'] span").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldSendFIOEng() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(3));
        $("[data-test-id='name'] input").setValue("Ivanov");
        $(withText("Забронировать")).click();
        $("[data-test-id='name'] span").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldSendFIONumber() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(3));
        $("[data-test-id='name'] input").setValue("Николай 2");
        $(withText("Забронировать")).click();
        $("[data-test-id='name'] span").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldSendPhoneEmpty() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("");
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'] span").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldSendPhoneNotPlus() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("71112223344");
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'] span").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendPhoneManyNumbers() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("+711122233448");
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'] span").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendWithoutAgreement() {
        SetDate date = new SetDate();

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        for (int i = 1; i <= 8; i++) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id='date'] input").setValue(date.getDate(3));
        $("[data-test-id='name'] input").setValue("Иванов Марк Аврелий");
        $("[data-test-id='phone'] input").setValue("+71112223344");
//        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(); //?
    }
}
