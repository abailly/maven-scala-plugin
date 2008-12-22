package org.scala_tools.maven.plexus.converters

import mojo.annotations._

@goal("dummy") @phase("compile") @requiresDependencyResolution
class DummyScalaMojo {
  @parameter
  var dummyVar : String = _
  @parameter
  var otherVar : Int = _
}
