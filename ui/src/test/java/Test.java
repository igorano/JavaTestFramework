import org.junit.jupiter.api.Test;

class ChromeTest extends base {

    @Test
    void openGoogle() throws InterruptedException {
        driver.navigate().to("https://google.com");
        Thread.sleep(1000);
    }

}