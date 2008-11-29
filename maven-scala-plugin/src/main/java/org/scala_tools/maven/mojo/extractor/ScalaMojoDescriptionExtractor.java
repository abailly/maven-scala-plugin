package org.scala_tools.maven.mojo.extractor;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.descriptor.InvalidPluginDescriptorException;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.project.MavenProject;
import org.apache.maven.tools.plugin.extractor.ExtractionException;
import org.apache.maven.tools.plugin.extractor.MojoDescriptorExtractor;
import org.apache.maven.tools.plugin.util.PluginUtils;


/**
 * This class is used to rip Mojo description information out of a .scala file.
 * 
 * @author J. Suereth
 */
public class ScalaMojoDescriptionExtractor implements MojoDescriptorExtractor {

	/**
	 * This is responsible for pulling mojo descriptions off of scala files in the project.
	 */
	public List execute(MavenProject project, PluginDescriptor pluginDescriptor)
			throws ExtractionException, InvalidPluginDescriptorException {
		
		System.err.println("Looking for Scala source roots...");
		List<MojoDescriptor> mojoDescriptions = new ArrayList<MojoDescriptor>();
		//TODO - parse through scala file and rip out MOJO annotations
		
		for(String root : (List<String>)project.getCompileSourceRoots()) {
			System.err.println("Looking in [" + root +"] for scala mojos...");
			for(String source : PluginUtils.findSources(root, "**/*.scala")) {
				System.err.println("Checking source [" + source +"] as mojo");
				MojoDescriptor tmp = extractMojoDescription(source, project, pluginDescriptor);
				if(tmp != null) {
					mojoDescriptions.add(tmp);
				}
			}
		}
		
		return mojoDescriptions;
	}
	/**
	 * Attempt to extract the mojo description from a particular file.
	 * @param source
	 * @param project
	 * @param pluginDescriptor
	 */
	private MojoDescriptor extractMojoDescription(String source, MavenProject project,
			PluginDescriptor pluginDescriptor) {
		MojoDescriptor descriptor = new MojoDescriptor();
		//TODO - Pull this from inside the .scala file.
		descriptor.setPluginDescriptor(pluginDescriptor);
		//For now let's use a lame algorithm just to test to see if this will work.
		descriptor.setDescription("Testing Scala extraction mojo");
		descriptor.setGoal("echo");
		descriptor.setExecuteGoal("echo");
		descriptor.setExecutePhase("process-sources");
		descriptor.setPhase("process-sources");
		descriptor.setLanguage("scala");
		descriptor.setComponentConfigurator("scala");
		descriptor.setVersion(project.getModelVersion());
		descriptor.setImplementation("org.scala_tools.mojo.TestMojo");
		System.err.println("Analyzing: " + source);		
		return descriptor;
	}
	
}
