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
		List<MojoDescriptor> mojoDescriptions = new ArrayList<MojoDescriptor>();
		//TODO - parse through scala file and rip out MOJO annotations
		
		for(String root : (List<String>)project.getCompileSourceRoots()) {
			for(String source : PluginUtils.findSources(root, "*.scala")) {
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
		//For now let's use a lame algorithm just to test to see if this will work.
		return null;
	}
	
}
