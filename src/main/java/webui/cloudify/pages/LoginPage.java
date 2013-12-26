package webui.cloudify.pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import webui.tests.annotations.FirstDisplayed;
import webui.tests.components.abstracts.AbstractComponent;

import java.util.List;

/**
 * User: guym
 * Date: 3/6/13
 * Time: 5:31 PM
 * <p/>
 * Since we are migrating from GWT login to a new login page we should support all modes possible modes.
 */
@Component
public class LoginPage extends AbstractComponent<ComplexLoginPage> {


    @Autowired
    private DashboardPage dashboardPage;

    @Autowired(required = false)
    private String rootUrl = "http://localhost:8099";

    private static Logger logger = LoggerFactory.getLogger(ComplexLoginPage.class);


    @FindBy(css = "body")
    private WebElement body;

    @FirstDisplayed
    @FindBy(css = "#username-input, input[name='username']")
    private WebElement usernameInput;

    @FirstDisplayed
    @FindBy(css = "#password-input, input[name='password']")
    private WebElement passwordInput;

    @FirstDisplayed
    @FindBy(css = "#login_button button, input[type='submit']")
    private WebElement submit;


    public LoginPage gotoPage() {
        logger.info("loading login page [{}]", rootUrl);
        webDriver.get(rootUrl);
        load();
        return this;
    }

    public boolean isLoginWelcomeMessageVisible() {
        String bodyText = body.getText().toLowerCase();
        return bodyText.contains("welcome") && bodyText.contains("please log in");
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    private WebElement getVisible(List<WebElement> elements) {
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        throw new RuntimeException(String.format("no visible element from list %s", elements));
    }

    public DashboardPage login() {
        return login(null, null);
    }

    public DashboardPage login(String username, String password) {
        logger.info(String.format("logging in with %s, %s", username, password));
        waitFor.elements(submit);
        if (!StringUtils.isEmpty(username)) {
            usernameInput.sendKeys(username);
            logger.info("typed username");
        }
        if (!StringUtils.isEmpty(password)) {
            passwordInput.sendKeys(password);
            logger.info("typed password");
        }
        submit.click();
        return dashboardPage.load();
    }
}
