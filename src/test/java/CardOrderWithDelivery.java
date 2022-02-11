import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;


public class CardOrderWithDelivery {

    @BeforeEach
    public void setUp(){
        holdBrowserOpen = true;
        browserSize = "1000x900";
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
}
