# # ScopeAgent Demo Spring Boot 2
Example repository to check how to use ScopeAgent with an [`Spring Boot 2`](https://spring.io/projects/spring-boot) project following [`git-flow`](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow).

### CI integration

This demo repository is using [```Jenkins```](https://jenkins.io/) to execute the build pipeline.

The ```Jenkins``` configuration is kept under the ```Jenkinsfile``` file, which contains the ```Jenkins pipeline``` configuration.

In this case, the pipeline is configured to execute parallel stages for ```JDK11```.

### ScopeAgent installation and execution

ScopeAgent execution is attached to the ```test```, ```integration-test``` and ```verify``` phases of the Maven build phases, through the ```argLine``` property configured in the ```surefire``` and ```failsafe``` maven plugins.

You can check a detail explanation about the installation of the ScopeAgent in the following instructions: 
* [Scope Java Agent instructions](https://scope.undefinedlabs.com/docs/java-installation) 

### Test results dashboard

Once the CI server has executed the build pipeline, all test classes will have been executed in every JDK version declared in the CI server configuration file.

This means that you can check the examples test results following the next instructions:
 
1. Access to [```https://demo.scope.dev/```](https://demo.scope.dev)
2. Log in with your GitHub account.
3. Locate the ```scopeagent-reference-springboot2``` project.

Now you can check the details about every test executed for every branch of the repository executed by CI build pipeline.
