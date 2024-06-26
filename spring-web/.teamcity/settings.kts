import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle

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

    buildType(Test)
    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(AbsoluteId("TeamcityLearn_HttpsGithubComBlacknikomoTeamcityLearnRefsHeadsMain"))
    }

    steps {
        gradle {
            name = "Build app"
            id = "gradle_runner"
            tasks = "clean build -x test"
            buildFile = "spring-web/build.gradle"
            gradleWrapperPath = "spring-web"
            jdkHome = "%env.JDK_17_0%"
        }
    }
})

object Test : BuildType({
    name = "Test"

    vcs {
        root(AbsoluteId("TeamcityLearn_HttpsGithubComBlacknikomoTeamcityLearnRefsHeadsMain"))
    }

    steps {
        gradle {
            id = "gradle_runner"
            tasks = "build"
            buildFile = "spring-web/build.gradle"
            gradleWrapperPath = "spring-web"
            jdkHome = "%env.JDK_17_0%"
        }
    }

    dependencies {
        snapshot(Build) {
        }
    }
})
