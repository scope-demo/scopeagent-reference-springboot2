pipeline {
    agent any

    stages {
       stage('Build') {
            parallel {
                stage('JDK11') {
                    steps {
                        sh 'echo build'
                        sh 'JDK=11-jdk COMMIT=${GIT_COMMIT} docker-compose -p 11-jdk-${GIT_COMMIT} build --build-arg JDK="11-jdk"'
                    }
                }
            }
        }

        stage('Test'){
            parallel {
                stage('JDK11') {
                    steps {
                        sh 'echo test'
                        sh 'IS_CI=true JDK=11-jdk JAVA_PROFILE=java11 COMMIT=${GIT_COMMIT} docker-compose -p 11-jdk-${GIT_COMMIT} up --exit-code-from=scopeagent-reference-springboot2 scopeagent-reference-springboot2'
                    }
                }
            }
        }
    }

    post {
        always {
            /*sh 'JDK=8-jdk COMMIT=${GIT_COMMIT} docker-compose -p 8-jdk-${GIT_COMMIT} down -v'*/
            sh 'JDK=11-jdk COMMIT=${GIT_COMMIT} docker-compose -p 11-jdk-${GIT_COMMIT} down -v'
            /* sh '''
               pwd
               ls
               ls ./artifacts
               ls ./artifacts/surefire-reports
            '''
            archiveArtifacts artifacts: 'artifacts/surefire-reports *//*' */
        }
    }

}