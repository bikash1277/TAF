package Base;

public class BaseClass extends BrowserFactory {

    public BaseClass() {
        super();
    }

    public void navigateToUrl(String url, String browser) {
        driver = initBrowser(browser);
        driver.get(url);
    }

    public String getScreenshot() {
		/*File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}*/
        return "path";
    }

}
