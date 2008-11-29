package org.scala_tools.maven.plexus.converters;

import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ConfigurationListener;
import org.codehaus.plexus.component.configurator.converters.ConfigurationConverter;
import org.codehaus.plexus.component.configurator.converters.lookup.ConverterLookup;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;

/**
 * This class is used to inject values from within plexus into scala "mojos"
 * @author J. Suereth
 *
 */
public class ScalaVarSetter {
	   private Object mojo;

	    private String fieldName;

	    private ConverterLookup lookup;

	    private Class<?> setterParamType;

	    private ConfigurationConverter setterTypeConverter;

	    private ConfigurationListener listener;

	    public ScalaVarSetter( String fieldName, Object object, ConverterLookup lookup )
	        throws ComponentConfigurationException
	    {
	        this( fieldName, object, lookup, null );
	    }

	    public ScalaVarSetter( String fieldName, Object object, ConverterLookup lookup,
	                                    ConfigurationListener listener )
	        throws ComponentConfigurationException
	    {
	        this.fieldName = fieldName;
	        this.mojo = object;
	        this.lookup = lookup;
	        this.listener = listener;

	        if ( object == null )
	        {
	            throw new ComponentConfigurationException( "Component is null" );
	        }

	        initSetter();
	    }

	    private void initSetter()
	    {
	        setterParamType = String.class;

	        try
	        {
	            setterTypeConverter = lookup.lookupConverterForType( setterParamType );
	        }
	        catch(ComponentConfigurationException e) { }
	    }

	    private void setValueUsingSetter( Object value )
	        throws ComponentConfigurationException
	    {
	        if ( setterParamType == null )
	        {
	            throw new ComponentConfigurationException( "No setter found" );
	        }

	        String exceptionInfo = mojo.getClass().getName() + "." + fieldName + " = ( "
	            + setterParamType.getClass().getName() + " )";

	        if ( listener != null )
	        {
	            listener.notifyFieldChangeUsingSetter( fieldName, value, mojo );
	        }

	        try
	        {
	        	//TODO - make sure this is correct.
	        	ScalaReflectionUtil.inject(mojo, fieldName, value);
	        }
	        catch ( IllegalArgumentException e )
	        {
	            throw new ComponentConfigurationException( "Invalid parameter supplied while setting '" + value + "' to "
	                + exceptionInfo, e );
	        }
	    }

	    public void configure( PlexusConfiguration config, ClassLoader cl, ExpressionEvaluator evaluator )
	        throws ComponentConfigurationException
	    {
	        Object value = setterTypeConverter.fromConfiguration( lookup, config, setterParamType, mojo.getClass(), cl,
	                                                              evaluator, listener );

	        if ( value != null )
	        {
	            setValueUsingSetter( value );
	            return;
	        }
	    }
}
