package tests.sanity;

import org.cloudifysource.setup.manager.CloudifyTestBean;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;
import webui.cloudify.pages.DashboardPage;
import webui.cloudify.pages.ComplexLoginPage;
import webui.cloudify.pages.LoginPage;
import webui.tests.setup.configuration.managers.SetupActionsManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

/**
 * User: guym
 * Date: 3/6/13
 * Time: 11:08 PM
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:tests/LicenseTest-context.xml"})
@ContextConfiguration("classpath:tests/LicenseTest-context.xml")
public class LicenseTest extends AbstractTestNGSpringContextTests {

    private static Logger logger = LoggerFactory.getLogger(LicenseTest.class);
                                                    /*
    //@Autowired
    //private LoginPage loginPage;

    //@Autowired
    //private ComplexLoginPage complexLoginPage;

//    @Autowired
//    private CloudifyTestBean cloudifyManager;
                                                  */
    @Autowired
    private SetupActionsManager.TestNg setupManager;

    //@Autowired
    //private LicenseTestConf licenseTestConf;

    @BeforeMethod
    public void beforeTest(){
        logger.info("beforeTest");
        setupManager.executeBeforeTest();
    }
       /*
    @Before
    public void before() {
        logger.info("bootstrapping");
        cloudifyManager.bootstrap();
    }

    @After
    public void after() {
        logger.info("tearing down");
        cloudifyManager.teardown();
    }*/

    @org.testng.annotations.Test
    public void test(){
        logger.info("Running License Test");
    }

    public void setSetupManager(SetupActionsManager.TestNg setupManager) {
        this.setupManager = setupManager;
    }

    @Ignore
    public void complexLicenseTest() {

        logger.info("Complex License Test");
        /*
        DashboardPage dashboard = complexLoginPage.gotoPage().login(licenseTestConf.username, licenseTestConf.password);
        dashboard.getAboutButton().click();
        // TODO comment out and fill in the missing functionality
//        Assert.assertTrue( String.format( "expecting to see %s in popup with text [%s]", licenseTestConf.aboutText, dashboard.findFirstDisplayedWindowDialog().getText() ), dashboard.isTextInPopups(licenseTestConf.aboutText) );
//        dashboard.closeDialog( "OK" ).clickLogout().closeDialog( "yes" );
        loginPage.load();
        Assert.assertTrue("We are now in login page. We should see welcome message", loginPage.isLoginWelcomeMessageVisible());
        */
    }

    @Test
    public void licenseTest() {
        logger.info("license test");
        /*
        DashboardPage dashboard = loginPage.gotoPage().login(licenseTestConf.username, licenseTestConf.password);
        dashboard.openAbout();
        // TODO comment out and fill in the missing functionality
        Assert.assertTrue( String.format( "expecting to see %s in popup with text [%s]", licenseTestConf.aboutText, dashboard.getAboutText() ), dashboard.getAboutText().toLowerCase().contains(licenseTestConf.aboutText) );
        ComplexLoginPage logout = dashboard.closeAbout().logout();
        Assert.assertTrue("We are now in login page. We should see welcome message", logout.isLoginWelcomeMessageVisible());
        */
    }

    @Ignore
    public void failedTest() {
        throw new RuntimeException("I am failing!");
    }

    public static class LicenseTestConf {
        public String username = null;
        public String password = null;
        public String aboutText;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setAboutText(String aboutText) {
            this.aboutText = aboutText;
        }
    }
}
