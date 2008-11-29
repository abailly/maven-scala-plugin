package org.scala_tools.maven.plexus.converters;

import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ConfigurationListener;
import org.codehaus.plexus.component.configurator.converters.AbstractConfigurationConverter;
import org.codehaus.plexus.component.configurator.converters.lookup.ConverterLookup;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
/**
 * This class takes configuration and injects it into scala vars.
 * 
 * @author J. Suereth
 *
 */
public class ScalaConfigurationConverter extends AbstractConfigurationConverter {

	@Override
	public boolean canConvert(Class type) {
		// We should be able to handle every java type.
		return true;
	}

	@Override
	public Object fromConfiguration(ConverterLookup converterLookup, PlexusConfiguration configuration, Class type,
            Class baseType, ClassLoader classLoader, ExpressionEvaluator expressionEvaluator,
            ConfigurationListener listener)
			throws ComponentConfigurationException {
		
		Object retValue = fromExpression( configuration, expressionEvaluator, type );
        if ( retValue == null )
        {
            try
            {
                // it is a "composite" - we compose it from its children. It does not have a value of its own
                Class<?> implementation = getClassForImplementationHint( type, configuration, classLoader );

                retValue = instantiateObject( implementation );

                processConfiguration( converterLookup, retValue, classLoader, configuration, expressionEvaluator, listener );
            }
            catch ( ComponentConfigurationException e )
            {
                if ( e.getFailedConfiguration() == null )
                {
                    e.setFailedConfiguration( configuration );
                }

                throw e;
            }
        }
        return retValue;
	}

	public void processConfiguration(ConverterLookup converterLookup,
			Object component, ClassLoader classLoader,
			PlexusConfiguration configuration,
			ExpressionEvaluator expressionEvaluator,
			ConfigurationListener listener) throws ComponentConfigurationException {
		//TODO - Inject into component the configuration properties.
		int items = configuration.getChildCount();

        for ( int i = 0; i < items; i++ )
        {
            PlexusConfiguration childConfiguration = configuration.getChild( i );

            String elementName = childConfiguration.getName();
            //TODO - Make sure the var setter is working correctly and we're passing the correct information to it.
            //TODO - Make sure we can handle "property object" configuration items.
            ScalaVarSetter varSetter = new ScalaVarSetter(fromXML( elementName ), component, converterLookup, listener);
            varSetter.configure(childConfiguration, classLoader, expressionEvaluator);
        }
		
	}

}
