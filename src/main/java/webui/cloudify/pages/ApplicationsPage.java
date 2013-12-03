package webui.cloudify.pages;

import com.google.common.base.Predicate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import webui.cloudify.components.ApplicationMap;
import webui.cloudify.components.PopupDialog;
import webui.tests.annotations.Absolute;
import webui.tests.annotations.LazyLoad;
import webui.tests.components.abstracts.AbstractComponent;
import webui.tests.components.gwt.ApplicationsInnerTabs;
import webui.tests.components.gwt.ComboBox;

import java.util.LinkedList;
import java.util.List;

/**
 * User: eliranm
 * Date: 6/25/13
 * Time: 12:57 PM
 */
@Component
public class ApplicationsPage extends AbstractComponent<ApplicationsPage> {

    private static Logger logger = LoggerFactory.getLogger(ApplicationsPage.class);

    @FindBy(id = "gs-graph-application-map")
    private ApplicationMap applicationMap;

    @FindBy(className = "gs-inner-tabs-container")
    private ApplicationsInnerTabs innerTabs;

    @FindBy(id = "gs-button-uninstall-app")
    private WebElement uninstallApplicationButton;

    @FindBy(id = "gs-application-combo-TOPOLOGY")
    private ComboBox comboBox;

    @LazyLoad
    @FindBy(css = "#gs-tab-item-topology-progress")
    private ProgressTab progressTab;

    @Absolute
    @LazyLoad
    @FindBy(css = "div.x-window-plain.x-window-dlg")
    private PopupDialog dialog;


    public ApplicationsPage progressTab() {
        innerTabs.to(ApplicationsInnerTabs.PROGRESS);
        return this;
    }

    public boolean hasApplication ( String name ){
        return comboBox.has( name );
    }

    public ApplicationsPage selectApplication(String name) {
        assert comboBox.has(name) : String.format("application [%s] not found in combobox, cannot select it", name );
        comboBox.select(name);
        return this;
    }

    public String getSelectedApplication() {
        return comboBox.getActiveValue();
    }

    public List<String> listApplications() {
        return comboBox.items();
    }



    /**
     * Gets the services deployed on applicationName.
     * If application does not exist - it returns an empty set.
     *
     * @return A list of service names.
     */
    public List<String> listServices( String applicationName ) {
        List<String> result = new LinkedList<String>();

        if ( !applicationName.equals(getSelectedApplication()) ){
            logger.info("I am currently not on [{}]", applicationName );
            if ( hasApplication( applicationName ) ){
                logger.info("switching to application [{}]", applicationName );
                selectApplication( applicationName );
                result = applicationMap.names();
            }else{
                logger.info("I am not on [{}] and that application doesn't exist.. returning empty", applicationName );
            }
        }else {
            result = applicationMap.names();
        }
        return result;
    }

    /**
     * Uninstalls the currently selected application.
     *
     * @return this (chainable).
     */
    public ApplicationsPage uninstallApplication() {
        uninstallApplicationButton.click();
        return this;
    }

    /**
     * Uninstalls the specified application. If the application is not
     * currently selected, this method will fail silently.
     *
     * @param name The application name.
     * @return this (chainable).
     */
    public ApplicationsPage uninstallApplication(String name) {
        if (getSelectedApplication().equals(name)) {
            uninstallApplication();
        }
        return this;
    }


    private boolean shouldSwitchApplication(String from, String to) {
        return listApplications().contains(to) && !to.equals(from);
    }

    /**
     * Uninstalls the specified service from the current application.
     * If the service is not deployed in the current application,
     * this method will fail silently.
     *
     * @param name The service name.
     * @return this (chainable).
     */
    public ApplicationsPage uninstallService(String name) {
        if (applicationMap.has(name)) {
            applicationMap.uninstall(name);
        }
        return this;
    }

    /**
     * Waits for a service to disappear from the application. Use this method
     * after uninstalling a service to make sure it is no longer deployed.
     *
     * @param name The service name.
     * @return this (chainable).
     */
    public ApplicationsPage waitForServiceUninstall(final String name) {
        new WebDriverWait(webDriver, 20).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver webDriver) {
                return !applicationMap.has(name);
            }
        });
        return this;
    }

    public ApplicationsPage approveUninstall(){
        dialog.load().clickYes();
        return this;
    }

    public ApplicationsPage cancelUninstall(){
        dialog.load().clickNo();
        return this;
    }

    public String progressText() {
        assert progressTab != null : "cannot get progress text when progress tab is not selected";
        return progressTab.console.getText();
    }

    public String progressPath() {
        assert progressTab != null : "cannot get progress path when progress tab is not selected";
        return progressTab.recipePath.getText();
    }


    public static class ProgressTab extends AbstractComponent<ProgressTab> {

        @FindBy(css = ".x-panel-header")
        public WebElement recipePath;

        @FindBy(css = "pre")
        public WebElement console;

    }

}
