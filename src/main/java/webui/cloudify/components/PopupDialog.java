package webui.cloudify.components;

import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webui.tests.components.abstracts.AbstractComponent;

import java.util.List;

/**
 * User: eliranm
 * Date: 12/3/13
 * Time: 1:18 PM
 *
 * // found by div.x-window-plain.x-window-dlg
 *
 * Use with @Absolute and @LazyLoad and then do
 *
 * @FindBy("div.x-window-plain.x-window-dlg")
 * PopupDialog dialog;
 *
 *
 * public void doSomethingThatRequiresDialogApproval(){
 *     // do something
 *
 *     //approve dialog
 *     dialog.load().clickYes();
 *
 *     // do more..
 * }
 *
 *
 */
public class PopupDialog extends AbstractComponent<PopupDialog> {

    private static Logger logger = LoggerFactory.getLogger( PopupDialog.class );
    /**
     * click "Yes"
     * @param label
     */
    public void click(final String label){
        logger.info("clicking dialog's option : " + label);
        final WebElement rootElement = webElement;
        WebElement element = (WebElement) waitFor.predicate(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                List<WebElement> buttons = rootElement.findElements(By.cssSelector("button"));
                if (!CollectionUtils.isEmpty(buttons)){
                    for (WebElement element : buttons) {
                        if ( label.equalsIgnoreCase(element.getText())){
                            return element;
                        }
                    }

                }
                return null;
            }
        });

        element.click();
    }

    public void clickYes( ){
        click("Yes");
    }

    public void clickNo(){
        click("No");
    }
}
