import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class SelenideTestSecond {
    int addDays = 7;

    private String generateDate(String patterns) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(patterns));
    }

    private boolean meetingMonthNext() {
        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        String defaultMonth = generateDate("MM");
        return currentMonth.equals(defaultMonth);
    }

    @Test
    void shouldForm() {
        open("http://localhost:9999/");
        String currentDate = generateDate("dd.MM.yyyy");
        String meetingDay = generateDate("d");
        $("[data-test-id='city'] input").setValue("Ек");
        $$(".menu-item__control").find(Condition.text("Екатеринбург")).click();
        $("[data-test-id='date'] input").click();
        if (!meetingMonthNext()) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").find(Condition.text(meetingDay)).click();
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79023456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }
}
