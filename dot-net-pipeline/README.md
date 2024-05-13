# TeamCity Sample .NET App

This is a sample console solution that features the main project and two projects with unit tests. The .teamcity folder stores Kotlin configuration file that sets up the following pipeline (build chain):


```Plain Text
                               Test1
                             /       \
Build App --- NuGet Pack ---          --- DotCover Report
                             \       /
                               Test2
```

## Technical Information / Requirements

* Platform: Cross-platform (.NET 8)
* Language: C#
* Testing framework: MSTest
* Code coverage: JetBrains dotCover (bundled in TeamCity)


## Configuration 1: Build

This configuration launches the TeamCity .NET runner with the `build` command.

```Kotlin
steps {
   dotnetBuild {
      id = "dotnet"
      projects = "SampleDotNetProj.sln"
      sdk = "8"
   }
}
```

## Configuration 2: NuGet Pack

The second configuration uses the same .NET runner and targets the same solution, but runs the `pack` command that builds a NuGet package.

```Kotlin
steps {
   dotnetPack {
      name = "Pack"
      id = "Pack_1"
      projects = "SampleDotNetProj"
      configuration = "Debug"
      outputDir = "SampleDotNetProj/package"
      versionSuffix = "1.0.0"
      sdk = "8"
      param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%")
   }
}

dependencies {
   snapshot(Build) { }
}
```

This action is organized into a separate configuration for educational purposes. You can move the `pack` command to the 2nd step of the first build configuration.


## Configuration 3 and 4: Test1 and Test2

These configurations run all unit tests from the "TestSuite1" and "TestSuite2" projects. The dotCover tool performs code coverage analysis and publishes generated .dcvr snapshot files as an artifact.

Note that both configurations have snapshot dependencies to the same configuration (NuGet Pack), which means they can run simultaneously (in parallel on different agents).

The "TestSuite2" project tests are always failing.

```Kotlin
artifactRules = "%teamcity.agent.home.dir%/temp/agentTmp/*.dcvr=>Snapshot1"

steps {
   dotnetTest {
      id = "dotnet"
      projects = "TestSuite1/TestSuite1.csproj"
      sdk = "8"
      coverage = dotcover {
        toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
      }
   }
}
dependencies {
   snapshot(NuGetPack) { }
}
```


## Configuration 5: Test Report

This configuration retrieves .dcvr files published by the two Test configurations, and runs the TeamCity dotCover runner to merge them in a consolidated report.

Note that snapshot dependencies are configured to run this build even if previous configurations fail.

```Kotlin
steps {
   dotCover {
      name = "dotCover"
      id = "dotCover"
      toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
      snapshotPaths = "%teamcity.build.workingDir%/*/*.dcvr"
   }
}

dependencies {
   dependency(Test1) {
      snapshot { onDependencyFailure = FailureAction.IGNORE }
      artifacts { artifactRules = "+:*/*.dcvr" }
   }
   dependency(Test2) {
      snapshot { onDependencyFailure = FailureAction.IGNORE }
      artifacts { artifactRules = "+:*/*.dcvr" }
   }
}
```
