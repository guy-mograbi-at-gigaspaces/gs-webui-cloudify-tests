package tests.repository;

import org.cloudifysource.setup.manager.CloudifyTestBean;
import org.junit.After;import org.junit.Assert;import org.junit.Before;import org.junit.Ignore;import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import webui.cloudify.pages.ApplicationsPage;
import webui.cloudify.pages.LoginPage;
import webui.cloudify.pages.RecipesRepositoryPage;

import java.lang.String;

/**
 * User: eliranm
 * Date: 6/25/13
 * Time: 12:26 PM
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()
public class RecipesRepositoryTest {

    private static Logger logger = LoggerFactory.getLogger( RecipesRepositoryTest.class );

    @Autowired
    private RecipesRepositoryTestConf conf;

    @Autowired
    private CloudifyTestBean cloudifyManager;

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private ApplicationsPage applicationsPage;

    @Before
    public void before(){
        logger.info( "bootstrapping" );
        cloudifyManager.bootstrap();
    }

    @After
    public void after(){
        logger.info( "tearing down" );
        cloudifyManager.teardown();
    }

    @Test
    public void installApplicationTest() {
        logger.info( "install appclication test" );
        RecipesRepositoryPage recipesRepositoryPage = loginPage.gotoPage().login().gotoRecipes().applications().install("helloworld");
        // TODO fill in the missing functionality
/*
        Assert.assertTrue(
                recipesRepositoryPage.isTextInPopups(conf.progressDialogText),
                String.format("expecting to see [%s] in popup with text [%s]", conf.progressDialogText, recipesRepositoryPage.findFirstDisplayedWindowDialog().getText() )
        );
*/
    }

    @Test
    public void applicationInstallationProgressTest(){
        logger.info( "application installation progress test" );
        loginPage.gotoPage().login().gotoRecipes().applications().install("helloworld")
//                .closeDialog("yes") // TODO fill in the missing functionality
        ;
        applicationsPage.load().progressTab();
        Assert.assertTrue(
                String.format("expecting recipe path [%s] to contain [%s]", applicationsPage.progressPath().toLowerCase(), "helloworld"),
                applicationsPage.progressPath().toLowerCase().contains("helloworld"));
        Assert.assertTrue(
                String.format("expecting console text [%s] to contain [%s]", applicationsPage.progressText(), conf.progressConsoleText),
                applicationsPage.progressText().contains(conf.progressConsoleText));
    }

    public static class RecipesRepositoryTestConf {
        public String progressDialogText;
        public String progressConsoleText;

        public void setProgressDialogText(String progressDialogText) {
            this.progressDialogText = progressDialogText;
        }
        public void setProgressConsoleText(String progressConsoleText) {
            this.progressConsoleText = progressConsoleText;
        }
    }

}
