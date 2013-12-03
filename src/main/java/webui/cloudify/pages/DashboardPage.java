package webui.cloudify.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import webui.tests.components.abstracts.AbstractComponent;

/**
 * User: guym
 * Date: 3/7/13
 * Time: 8:16 AM
 */
@Component
public class DashboardPage extends AbstractComponent<DashboardPage> {


    @FindBy(id = "gs-tab-item-recipes-button")
    private WebElement recipesButton;

    @FindBy(id = "gs-tab-item-topology-button")
    private WebElement applicationsButton;

    @Autowired
    private RecipesRepositoryPage recipesRepositoryPage;

    @Autowired
    private ApplicationsPage applicationsPage;

    public ApplicationsPage gotoApplications() {
        applicationsButton.click();
        return applicationsPage.load();
    }

    public RecipesRepositoryPage gotoRecipes() {
        recipesButton.click();
        return recipesRepositoryPage.load();
    }


    @FindBy(css="#gs-about-button")
    private WebElement aboutButton;

    @Autowired
    private ComplexLoginPage loginPage;

    @FindBy(css="#gs-logout-button")
    private WebElement logoutButton;

    @Bean
    public DashboardPage dashboardPage(){
        return new DashboardPage();
    }

    public WebElement getAboutButton() {
        return aboutButton;
    }

    public DashboardPage clickLogout(){
        logoutButton.click();
        return this;
    }

    public ComplexLoginPage logout(){
        logoutButton.click();
//        closeDialog( "yes" ); // TODO fill in the missing functionality
        return loginPage.load();
    }


    public void setLoginPage( ComplexLoginPage loginPage ) {
        this.loginPage = loginPage;
    }
}
