package tests;

import org.cloudifysource.setup.manager.CloudifyTestBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import webui.cloudify.pages.ApplicationsPage;
import webui.cloudify.pages.LoginPage;
import webui.tests.utils.Assert;

import java.util.List;

/**
 * User: eliranm
 * Date: 6/25/13
 * Time: 12:26 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:tests/SecuredActionsTest-context.xml"})
public class SecuredActionsTest {


    @Autowired(required=false)
    private Conf conf = new Conf();

    private static Logger logger = LoggerFactory.getLogger( SecuredActionsTest.class );

    @Autowired
    private CloudifyTestBean cloudifyManager;

    @Autowired
    private LoginPage loginPage;


    @Before
    public void before(){
        logger.info( "bootstrapping" );
        cloudifyManager.bootstrap();
        cloudifyManager.installApplication();
    }

    @After
    public void after(){
        logger.info( "tearing down" );
        cloudifyManager.teardown();
    }

    @Test
    public void uninstallApplicationTest() {
        logger.info("uninstall application test");

        ApplicationsPage applicationsPage = loginPage.gotoPage().login().gotoApplications();
        applicationsPage.load();

        List<String> installedApplications = applicationsPage.listApplications();
        Assert.assertContains( String.format("application [%s] is not installed", conf.applicationName ), installedApplications, conf.applicationName );

        logger.info("uninstalling application [{}]...", conf.applicationName);
        applicationsPage.uninstallApplication(conf.applicationName);
        installedApplications = applicationsPage.listApplications();
        logger.info("uninstalled [{}], installed applications are now [{}]", conf.applicationName, installedApplications);

        Assert.assertTrue(
                !installedApplications.contains(conf.applicationName),
                String.format(
                        "application [%s] should no longer appear in the applications combo box items [%s]",
                        conf.applicationName, installedApplications));
    }

    @Test
    public void uninstallServiceTest() {
        logger.info( "uninstall service test" );


        ApplicationsPage applicationsPage = loginPage.gotoPage().login().gotoApplications();
        List<String>  installedServices = applicationsPage.listServices( conf.serviceApplicationName );

        logger.info("installed services are [{}]", installedServices);
        Assert.assertContains(String.format("service [%s] is not installed", conf.serviceName), installedServices, conf.serviceName);

        logger.info("uninstalling service [{}]...", conf.serviceName);

        applicationsPage.selectApplication(conf.serviceApplicationName)
                .uninstallService(conf.serviceName)
                .approveUninstall()
//                .closeDialog("yes")
                .waitForServiceUninstall(conf.serviceName);


        installedServices = applicationsPage.listServices( conf.applicationName );
        logger.info("uninstalled [{}], installed services are now [{}]",  conf.serviceName, installedServices);


        Assert.assertTrue(
                !installedServices.contains( conf.serviceName),
                String.format(
                        "service [%s] should no longer appear under the installed services [%s]",
                        conf.serviceName, installedServices));

    }

    public static class Conf {
        public String applicationName = "helloworld";
        public String serviceName = "tomcat";
        public String serviceApplicationName = "default";  // the application name for 'serviceName'

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public void setServiceApplicationName(String serviceApplicationName) {
            this.serviceApplicationName = serviceApplicationName;
        }
    }

}
