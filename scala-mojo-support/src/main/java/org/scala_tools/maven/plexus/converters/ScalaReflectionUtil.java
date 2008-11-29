package org.scala_tools.maven.plexus.converters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Generic Scala Reflection helper methods.
 * 
 * @author J. Suereth
 *
 */
public class ScalaReflectionUtil {
	/**
	 * This method will inject a value into a "var" on a scala object.
	 * @param object
	 *          The scala object
	 * @param var
	 *          The name of the var
	 * @param value
	 *          The value to inject
	 * @throws IllegalArgumentException
	 *          This is thrown if any error occurs (i.e. invalid inputs...)
	 */
	public static void inject(Object object, String var, Object value) {
		//TODO - Handle annotations...
		Class<?> type = value.getClass();
		boolean success = false;
		while(!success && type != null) {
			success = injectTry(object, var, value, type);
			type = type.getSuperclass();
		}
		
	}
	
	private static boolean injectTry(Object object, String var, Object value, Class<?> valueType) throws IllegalArgumentException {
		try {
			Method setVarMethod = object.getClass().getMethod(var + "_$eq", valueType);
			if(setVarMethod != null) {
				//TODO - is this needed...
				if(!setVarMethod.isAccessible()) {
					setVarMethod.setAccessible(true);
				}
				setVarMethod.invoke(object, value);
				return true;
			}
		} catch (SecurityException e) {
			throw new IllegalArgumentException("Attempting to inject on 'secure' class",e);
		} catch (NoSuchMethodException e) {
			//Ignore and return false.
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Attempting to inject inaccessible property!", e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException("Failure on target when injecting resource", e);
		}
		return false;
	}
}
