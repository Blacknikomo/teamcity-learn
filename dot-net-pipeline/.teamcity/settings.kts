import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dotCover
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetBuild
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetPack
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.03"

project {

    buildType(Test1)
    buildType(Test2)
    buildType(NuGetPack)
    buildType(TestReport)
    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(AbsoluteId("TeamcityLearn_HttpsGithubComBlacknikomoTeamcityLearnRefsHeadsMain"))
    }

    steps {
        dotnetBuild {
            id = "dotnet"
            projects = "SampleDotNetProj.sln"
            workingDir = "dot-net-pipeline"
            sdk = "8"
            param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%")
        }
    }

    features {
        perfmon {
        }
    }
})

object NuGetPack : BuildType({
    name = "NuGet Pack"

    vcs {
        root(AbsoluteId("TeamcityLearn_HttpsGithubComBlacknikomoTeamcityLearnRefsHeadsMain"))
    }

    steps {
        dotnetPack {
            name = "Pack"
            id = "Pack_1"
            projects = "SampleDotNetProj"
            workingDir = "dot-net-pipeline"
            configuration = "Debug"
            outputDir = "SampleDotNetProj/package"
            versionSuffix = "1.0.0"
            sdk = "8"
            param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%")
        }
    }

    dependencies {
        snapshot(Build) {
        }
    }
})

object Test1 : BuildType({
    name = "Test1"

    artifactRules = "%teamcity.agent.home.dir%/temp/agentTmp/*.dcvr=>Snapshot1"

    vcs {
        root(AbsoluteId("TeamcityLearn_HttpsGithubComBlacknikomoTeamcityLearnRefsHeadsMain"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "TestSuite1/TestSuite1.csproj"
            workingDir = "dot-net-pipeline"
            sdk = "8"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
            }
        }
    }

    features {
        perfmon {
        }
    }

    dependencies {
        snapshot(NuGetPack) {
        }
    }
})

object Test2 : BuildType({
    name = "Test2"

    artifactRules = "%teamcity.agent.home.dir%/temp/agentTmp/*.dcvr=>Snapshot2"

    vcs {
        root(AbsoluteId("TeamcityLearn_HttpsGithubComBlacknikomoTeamcityLearnRefsHeadsMain"))
    }

    steps {
        dotnetTest {
            id = "dotnet"
            projects = "TestSuite2/TestSuite2.csproj"
            workingDir = "dot-net-pipeline"
            sdk = "8"
            coverage = dotcover {
                toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
            }
        }
    }

    dependencies {
        snapshot(NuGetPack) {
        }
    }
})

object TestReport : BuildType({
    name = "TestReport"

    vcs {
        root(AbsoluteId("TeamcityLearn_HttpsGithubComBlacknikomoTeamcityLearnRefsHeadsMain"))
    }

    steps {
        dotCover {
            name = "dotCover"
            id = "dotCover"
            toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
            snapshotPaths = """"%teamcity.build.workingDir%/*/*.dcvr""""
        }
    }

    dependencies {
        dependency(Test1) {
            snapshot {
                onDependencyFailure = FailureAction.IGNORE
            }

            artifacts {
                artifactRules = "+:*/*.dcvr"
            }
        }
        dependency(Test2) {
            snapshot {
                onDependencyFailure = FailureAction.IGNORE
            }

            artifacts {
                artifactRules = "+:*/*.dcvr"
            }
        }
    }
})
