package webui.cloudify.components;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webui.tests.annotations.Absolute;
import webui.tests.annotations.FirstDisplayed;
import webui.tests.components.abstracts.AbstractComponent;
import webui.tests.components.gwt.DropdownMenu;

import java.lang.String;import java.util.ArrayList;
import java.util.List;

/**
 * User: eliranm
 * Date: 7/17/13
 * Time: 2:00 PM
 */

public class ApplicationMap extends AbstractComponent<ApplicationMap> {

    @Absolute
    @FirstDisplayed
    @FindBy(className = "gs-drop-down-menu")
    private DropdownMenu menu;

    public ApplicationMap uninstall(String name) {
        webElement.findElement(By.cssSelector("image[id*=\"node-tool-ACTIONS-\"][id*=\"" + name + "\"]")).click();
        menu.load().selectById("gs-menu-item-uninstall");
        return this;
    }

    public boolean has(String name) {
        try {
            return webElement.findElement(By.cssSelector("path[id^=\"node-shape-path-\"][id$=\"" + name + "\"]")) != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<String> names() {
        List<WebElement> nodePaths = webElement.findElements(By.cssSelector("path[id^=\"node-shape-path-\"]"));
        List<String> names = new ArrayList<String>(nodePaths.size());
        for (WebElement path : nodePaths) {
            names.add(path.getAttribute("id").replaceFirst("node-shape-path-.*\\.", ""));
        }
        return names;
    }
}
