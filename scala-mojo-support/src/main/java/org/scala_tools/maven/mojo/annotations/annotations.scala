package org.scala_tools.maven.mojo.annotations




// Annotations for var's

/** Used to mark a parameter as required to execute*/
class required extends StaticAnnotation
/** Used to define a parameter as readonly - TODO inject into vals? */
class readOnly extends StaticAnnotation
/** Used to define a parameter from a var field */
class parameter extends StaticAnnotation


//Annotations for Mojos
class phase(val name : String) extends StaticAnnotation
class goal(val name : String) extends StaticAnnotation
class requiresDependencyResolution extends StaticAnnotation

