pipeline {
    agent any
    
    environment {
        JAVA_HOME = tool(name: 'JDK 17', type: 'jdk')
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
                    env.JAVA_HOME = tool(name: 'JDK 17')
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
    }
    
    post {
        success {
            script {
                // Notify GitHub of successful build
                def commitSha = env.GIT_COMMIT  // Jenkins auto-populates this variable
                bat """
                    curl -H "Authorization: token ${GITHUB_TOKEN}" ^
                    -d "{\\"state\\": \\"success\\", \\"target_url\\": \\"${env.BUILD_URL}\\", \\"description\\": \\"Build succeeded\\", \\"context\\": \\"continuous-integration/jenkins\\"}" ^
                    https://api.github.com/repos/H-SAYO/Monektos/statuses/${commitSha}
                """
            }
        }
        
        failure {
            script {
                // Notify GitHub of failed build
                def commitSha = env.GIT_COMMIT
                bat """
                    curl -H "Authorization: token ${GITHUB_TOKEN}" ^
                    -d "{\\"state\\": \\"failure\\", \\"target_url\\": \\"${env.BUILD_URL}\\", \\"description\\": \\"Build failed\\", \\"context\\": \\"continuous-integration/jenkins\\"}" ^
                    https://api.github.com/repos/H-SAYO/Monektos/statuses/${commitSha}
                """
            }
        }
        
        always {
            echo 'Cleaning up...'
            // Clean up Docker container if needed
            bat 'docker stop postgres'
            bat 'docker rm postgres'
        }
    }
}
