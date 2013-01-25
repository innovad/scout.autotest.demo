scout.autotest.demo
===================

Scout Sample Application with JUnit Tests, Maven Surfire Test Execution and Maven Tycho Build

Build:
- [![Build Status](https://buildhive.cloudbees.com/job/innovad/job/scout.autotest.demo/badge/icon)](https://buildhive.cloudbees.com/job/innovad/job/scout.autotest.demo/)
- Public Build on <a href="https://buildhive.cloudbees.com/job/innovad/job/scout.autotest.demo/">Buildhive</a>

How to:

- Fetch the GIT repository into a Eclipse 4.2 Juno/Scout 3.9 workspace
- Eclipse Scout 3.9 Milestone Update Site: http://download.eclipse.org/scout/releases/3.9
- In directory org.eclipse.scout.autotest.parent, start "mvn clean install"

Install Derby DB
- This sample app required the Demo DB to be installed on C:\DerbyDB (Windows)
- Download: http://wiki.eclipse.org/images/b/be/TutorialMiniCrmWorkspaceDerbyDB.zip
- Info: http://wiki.eclipse.org/Scout_DatabaseDevelopmentPerspective

Test Server
- for client integration tests, a test server is started with maven jetty
- port is 18080, jetty is terminated after mvn execution

Some useful links:

Eclipse Scout Maven Tycho Build
- Wiki: http://wiki.eclipse.org/Scout/Tutorial/3.8/Maven_Tycho_Build
- Forum Discussion: <a href="http://www.eclipse.org/forums/index.php/t/446393/">Thread 1</a>, <a href="http://www.eclipse.org/forums/index.php/t/447603/">Thread 2</a>

Eclipse Scout Testing
- Forum: http://www.eclipse.org/forums/index.php/mv/msg/262115/757087/#msg_757087

Maven
- Maven Tycho Reference Card: http://wiki.eclipse.org/Tycho/Reference_Card
- Maven Jetty Plugin: http://docs.codehaus.org/display/JETTY/Maven+Jetty+Plugin (currently I am using Jetty 6)
