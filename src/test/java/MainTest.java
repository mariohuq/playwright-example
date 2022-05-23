import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(value = DisplayNameGenerator.IndicativeSentences.class)
class MainTest extends PlayWrightTest {

    @Test
    void shouldChangeSlides() {
        setup(BrowserName.Chromium, ClientType.Desktop);
        navigate("#1");
        PlaywrightAssertions.assertThat(page.locator(".remark-visible .remark-slide-number"))
                .hasText("1 / 24");
        page.keyboard().press("ArrowRight");
        PlaywrightAssertions.assertThat(page.locator(".remark-visible .remark-slide-number"))
                .hasText("2 / 24");
    }
}