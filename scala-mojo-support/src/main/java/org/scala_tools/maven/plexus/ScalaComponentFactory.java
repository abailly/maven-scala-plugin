package org.scala_tools.maven.plexus;

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.factory.AbstractComponentFactory;
import org.codehaus.plexus.component.factory.ComponentFactory;
import org.codehaus.plexus.component.factory.ComponentInstantiationException;
import org.codehaus.plexus.component.repository.ComponentDescriptor;

/**
 * This is a component factory for creating scala components
 * @author J. Suereth
 */
public class ScalaComponentFactory extends AbstractComponentFactory implements
		ComponentFactory {

	public ScalaComponentFactory() {
	}

	/**
	 * Create a new instance of a component using "scala" insantiation rules.
	 * 
	 * TODO - Handle "Objects" instead of just classes.
	 */
	@Override
	public Object newInstance(ComponentDescriptor componentDescriptor,
			ClassRealm classRealm, PlexusContainer container)
			throws ComponentInstantiationException {
		componentDescriptor.setComponentConfigurator( "scala" );
		//TODO - Do we want to try to inject things into the constructor.
		try {
			return createScalaObject(componentDescriptor.getImplementation(), classRealm);
		} catch (Exception e) {
			throw new ComponentInstantiationException("Trouble instantiating plexus component: " + componentDescriptor.getDescription(),e);
		}
	}
	/**
	 * Creates a Scala object.
	 * 
	 * @param name
	 *          The classname of the scala class to instantiate
	 * @param classRealm
	 *          The "realm" to insantiate classes with.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object createScalaObject(String name, ClassRealm classRealm) throws ClassNotFoundException, InstantiationException, IllegalAccessException {		
		Class<?> type = classRealm.loadClass(name);		
		return type.newInstance();
	}

}
