package org.scala_tools.maven.mojo.extractor

import scala.tools.nsc._
import scala.tools.nsc.reporters._

class MojoExtractorCompiler {
  //Method to extract mojo description from a source file.
  def extract(sourceFiles : String*) = {
      //helper method to initialize settings
	  def initialize : (Settings, Reporter) = {
	    val settings = new Settings();
        //TODO - Set settings
        val reporter = new ConsoleReporter(settings);
	    (settings,reporter)
	  }
	  //helper method to execute presentation compiler
	  def execute(settings : Settings, reporter : Reporter) = {
		  val compiler = new Global(settings, reporter) {
		    override def onlyPresentation = true
		  }
	      val run = new compiler.Run
	      run.compile(sourceFiles.toList)       
          //Extract mojo description
          def extractMojos(unit : compiler.CompilationUnit) {
          }
          for(unit <- run.units if !unit.isJava) {
            extractMojos(unit)
          }
	  }
      
    val (settings, reporter) = initialize
    execute(settings, reporter)
  } 
}


