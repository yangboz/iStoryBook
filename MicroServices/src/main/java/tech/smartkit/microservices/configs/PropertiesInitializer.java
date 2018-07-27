/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.configs;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * Register this with the DispatcherServlet in a ServletInitializer class like:
 * dispatcherServlet.setContextInitializers(new PropertiesInitializer());
 */
public class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
    private static final Logger LOG = LogManager.getLogger(PropertiesInitializer.class);

    /**
     * Runs as appInitializer so properties are wired before spring beans
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext)
    {
        ConfigurableEnvironment env = applicationContext.getEnvironment();

        Object[] activeProfiles = getActiveProfiles(env);

        for (Object profileName : activeProfiles) {
            LOG.info("Loading properties for Spring Active Profile: {}"+profileName.toString());
            try {
                ResourcePropertySource propertySource =
                        new ResourcePropertySource(profileName + "EnvProperties", "classpath:application-" + profileName
                                + ".properties");

                env.getPropertySources().addLast(propertySource);
                LOG.debug("propertySource:" + propertySource.toString());
                // Work-flow setting initialization here.
                //
//                ServerSetting.setPort(Integer.valueOf((String) propertySource.getProperty("server.port")));
//                ServerSetting.setContextPath((String) propertySource.getProperty("server.contextPath"));
            } catch (IOException e) {
                LOG.error("ERROR during environment properties setup - TRYING TO LOAD: " + profileName, e);

                // Okay to silently fail here, as we might have profiles that do
                // not have properties files (like dev1, dev2, etc)
            }
        }
    }

    /**
     * Returns either the ActiveProfiles, or if empty, then the DefaultProfiles from Spring
     */
    protected Object[] getActiveProfiles(ConfigurableEnvironment env)
    {
        Object[] activeProfiles = env.getActiveProfiles();
        if (activeProfiles.length > 0) {
            LOG.info("Using registered Spring Active Profiles: {}"+ StringUtils.join(activeProfiles, ", ").toString());
            return activeProfiles;
        }

        Object[] defaultProfiles = env.getDefaultProfiles();
        LOG.info("No Active Profiles found, using Spring Default Profiles: {}"+ StringUtils.join(defaultProfiles, ", ").toString());
        return defaultProfiles;
    }

}
