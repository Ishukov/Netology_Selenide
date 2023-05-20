import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTestFirst {
    private String generateDate(int addDays, String patterns) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(patterns));
    }

    @Test
    void shouldForm() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "800x900";
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79023456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }
}
