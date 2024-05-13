package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.DotnetTestStep
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'NetPipeline_Test1'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("NetPipeline_Test1")) {
    expectSteps {
        dotnetTest {
            id = "dotnet"
            projects = "TestSuite1/TestSuite1.csproj"
            sdk = "8"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
            }
        }
    }
    steps {
        update<DotnetTestStep>(0) {
            clearConditions()
            workingDir = "dot-net-pipeline"
        }
    }
}
