package org.scala_tools.maven.plexus.converters;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Test for Scala Reflection Utilities class.
 * @author J. Suereth
 *
 */
public class TestScalaReflectionUtil {
	
	@Test
	public void mustSetVariableInClass() {
		DummyScalaMojo mojo = new DummyScalaMojo();
		ScalaReflectionUtil.inject(mojo, "dummyVar", "HAI");
				
		assertEquals("HAI", mojo.dummyVar());
	}
}
