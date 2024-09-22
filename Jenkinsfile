pipeline {
    agent any
    
    environment {
        JAVA_HOME = tool name: 'JDK 17', type: 'jdk'
        PATH = "${JAVA_HOME}/bin;${env.PATH}"
    }
    
    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Set up JDK 17') {
            steps {
                script {
                    env.JAVA_HOME = tool name: 'JDK 17'
                    env.PATH = "${env.JAVA_HOME}/bin;${env.PATH}"
                }
            }
        }

        stage('Start PostgreSQL') {
            steps {
                bat 'docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=monektos -p 5432:5432 -d postgres:14'
                bat 'ping -n 21 127.0.0.1 > nul'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Notify GitHub') {
            steps {
                script {
                    def status = currentBuild.result == 'SUCCESS' ? 'success' : 'failure'
                    def description = currentBuild.result == 'SUCCESS' ? 'Build passed! 🎉' : 'Build failed! ❌'
                    
                    // Notify GitHub status
                    githubNotify context: 'continuous-integration/jenkins', status: status, description: description

                    // Comment on PR
                    def pr = currentBuild.rawBuild.getCause(hudson.model.Cause$PullRequest)
                    if (pr && status == 'success') {
                        def comment = "Build successful! 🎉"
                        githubNotify comment: comment
                    }
                }
            }
        }
    }
    
    post {
        always {
            echo 'Cleaning up...'
            // Clean up Docker container if needed
            bat 'docker stop postgres'
            bat 'docker rm postgres'
        }
    }
}
