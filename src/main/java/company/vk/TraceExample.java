package company.vk;

import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class TraceExample {
    public static void main(String[] args) {
        // trace click on link on main page

        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                     .setHeadless(false)
                     .setSlowMo(100))) {
            BrowserContext context = browser.newContext();
            // Start tracing before creating / navigating a page.
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
            try (Page page = context.newPage()) {
                page.navigate("https://ok.ru");
                page.click("a.anon-toolbar-nav_link-item-link");
                // Stop tracing and export it into a zip archive.
                context.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get("trace.zip")));
            }
        }
    }
}
