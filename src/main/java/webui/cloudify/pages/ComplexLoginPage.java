package webui.cloudify.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import webui.cloudify.components.LoginForm;
import webui.tests.annotations.LazyLoad;
import webui.tests.components.abstracts.AbstractComponent;

import java.util.List;

/**
 * User: guym
 * Date: 3/6/13
 * Time: 5:31 PM
 *
 *
 * Since we are migrating from GWT login to a new login page we should support all modes possible modes.
 *
 */
@Component
public class ComplexLoginPage extends AbstractComponent<ComplexLoginPage> {

    @Autowired
    private DashboardPage dashboardPage;

    @Autowired(required = false)
    private String rootUrl = "http://localhost:8099";

    private static Logger logger = LoggerFactory.getLogger( ComplexLoginPage.class );

    @LazyLoad
    @FindBy(css="div.form-area, div.gs-login-panel")
    private LoginForm loginForm;

    @FindBy(css = "body")
    private WebElement body;

    public ComplexLoginPage gotoPage(){
        webDriver.get( rootUrl );
        load();
        return this;
    }

    public boolean isLoginWelcomeMessageVisible(){

        final WebElement finalBody = body;


        return (Boolean) waitFor.predicate( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                String bodyText = body.getText().toLowerCase();
                return bodyText.contains( "welcome" ) && bodyText.contains( "please log in" ) ? Boolean.TRUE : null;
            }
        });

    }

    public void setRootUrl( String rootUrl ) {
        this.rootUrl = rootUrl;
    }

    private WebElement getVisible( List<WebElement> elements ){
        for ( WebElement element : elements )
        {
            if ( element.isDisplayed()){
                return element;
            }
        }
        throw new RuntimeException( String.format( "no visible element from list %s", elements ) );
    }

    public DashboardPage login( String username, String password ) {
          logger.info( String.format( "logging in with %s, %s", username, password ) );


          loginForm.username( username ).password( password ).submit();
          return dashboardPage.load();
      }
}
