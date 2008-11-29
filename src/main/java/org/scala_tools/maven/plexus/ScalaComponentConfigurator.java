package org.scala_tools.maven.plexus;

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.plexus.component.configurator.AbstractComponentConfigurator;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ComponentConfigurator;
import org.codehaus.plexus.component.configurator.ConfigurationListener;
import org.codehaus.plexus.component.configurator.converters.special.ClassRealmConverter;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.scala_tools.maven.plexus.converters.ScalaConfigurationConverter;

/**
 * This class is responsible for Dependency Injection of scala components.
 * 
 * @author J. Suereth
 *
 */
public class ScalaComponentConfigurator extends AbstractComponentConfigurator implements ComponentConfigurator {
	@Override
	public void configureComponent(Object component,
			PlexusConfiguration configuration,
			ExpressionEvaluator expressionEvaluator, ClassRealm containerRealm,
			ConfigurationListener listener)
			throws ComponentConfigurationException {
		converterLookup.registerConverter( new ClassRealmConverter( containerRealm ) );
		
        ScalaConfigurationConverter converter = new ScalaConfigurationConverter();

        //converter.fromConfiguration(converterLookup, configuration, type, baseType, containerRealm.getClassLoader(), expressionEvaluator);
        converter.processConfiguration( converterLookup, component, containerRealm.getClassLoader(), configuration,
                expressionEvaluator, listener );
	}
}
