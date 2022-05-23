import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class PlayWrightTest {

    public static final String BASE_URL = "http://0.0.0.0:8000/playwright.html";

    public enum BrowserName {
        Firefox, Chromium, Webkit
    }

    public enum ClientType {
        Desktop(new com.microsoft.playwright.Browser.NewContextOptions()
                .setViewportSize(800, 600)),
        Mobile(new com.microsoft.playwright.Browser.NewContextOptions()
                .setViewportSize(375, 667).setDeviceScaleFactor(2));

        private final Browser.NewContextOptions contextOptions;

        ClientType(Browser.NewContextOptions contextOptions) {
            this.contextOptions = contextOptions;
        }

        Browser.NewContextOptions options() {
            return contextOptions;
        }
    }

    protected Page page;
    protected TestInfo testInfo;
    private Browser browser;
    private BrowserContext context;

    private Playwright playwright;

    private static final long startTime = System.currentTimeMillis();

    public void setup(BrowserName browserName, ClientType clientType) {
        playwright = Playwright.create();
        browser = (switch (browserName) {
            case Firefox -> playwright.firefox();
            case Chromium -> playwright.chromium();
            case Webkit -> playwright.webkit();
        }).launch();
        context = browser.newContext(clientType.contextOptions);
        page = newPage(this.context);
        navigate();
    }

    @BeforeEach
    void injectTestInfo(TestInfo testInfo) {
        this.testInfo = testInfo;
    }

    @AfterEach
    public void cleanup() {
        if (page != null) {
            page.close();
            context = null;
        }
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            page = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
        testInfo = null;
    }

    protected void navigate() {
        navigate("");
    }

    protected void navigate(String url) {
        page.navigate(BASE_URL + url);
    }

    protected Page newPage(BrowserContext browserContext) {
        return browserContext.newPage();
    }
}
