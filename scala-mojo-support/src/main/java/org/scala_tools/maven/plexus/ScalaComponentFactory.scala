package org.scala_tools.maven.plexus

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.converters.lookup.ConverterLookup;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.component.factory.AbstractComponentFactory;
import org.codehaus.plexus.component.factory.ComponentFactory;
import org.codehaus.plexus.component.factory.ComponentInstantiationException;
import org.codehaus.plexus.component.repository.ComponentDescriptor;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.util.StringUtils;

/**
 * This class is used to instantiate new components in plexus
 */
class ScalaComponentFactory extends AbstractComponentFactory {
  
  //Override "newInstance" method.  Note: AnyRef = java.lang.Object
  override def newInstance(componentDescriptor : ComponentDescriptor, 
                           classRealm : ClassRealm, 
                           container : PlexusContainer ) : AnyRef = {
    //Helper method to load  a scala class from our "realm"
    def loadScalaClass(className : String) = classRealm.loadClass(className)
    //Helper method to create a new scala object using our 'class realm'
    def createScalaObject(className : String) = loadScalaClass(className).newInstance.asInstanceOf[AnyRef]
    
    try {
      componentDescriptor.setComponentConfigurator( "scala" );
      //TODO - See if we need to inject into the constructor somehow
      //TODO - See if we need to inject vars
      createScalaObject(componentDescriptor.getImplementation)
    } catch {
      case t : Throwable =>
        throw new ComponentConfigurationException("Problem creating new scala component", t);
    }
  }
  
  
}
