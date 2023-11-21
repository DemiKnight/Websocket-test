import org.scalajs.linker.interface.ModuleSplitStyle

name := "frontend"
scalaVersion := "3.3.1"
enablePlugins(ScalaJSPlugin)

scalaJSUseMainModuleInitializer := true

/* Configure Scala.js to emit modules in the optimal way to
 * connect to Vite's incremental reload.
 * - emit ECMAScript modules
 * - emit as many small modules as possible for classes in the "livechart" package
 * - emit as few (large) modules as possible for all other classes
 *   (in particular, for the standard library)
 */
scalaJSLinkerConfig ~= {
  _.withModuleKind(ModuleKind.ESModule)
    .withModuleSplitStyle(
      ModuleSplitStyle.SmallModulesFor(List("Frontend")))
}


libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "2.4.0",
  "com.raquo" %%% "laminar" % "16.0.0",
  "com.raquo" %%% "laminar" % "16.0.0"
)
