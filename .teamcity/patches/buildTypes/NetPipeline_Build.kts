package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.DotnetBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetBuild
import jetbrains.buildServer.configs.kotlin.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'NetPipeline_Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("NetPipeline_Build")) {
    expectSteps {
        dotnetBuild {
            id = "dotnet"
            projects = "SampleDotNetProj.sln"
            sdk = "8"
            param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%")
        }
    }
    steps {
        update<DotnetBuildStep>(0) {
            clearConditions()
            workingDir = "dot-net-pipeline"
        }
    }
}
