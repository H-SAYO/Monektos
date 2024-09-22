pipeline {
    agent any
    
    environment {
        JAVA_HOME = tool name: 'JDK 17', type: 'jdk'
        MAVEN_HOME = tool name: 'Maven 3', type: 'maven'  // Using Jenkins-managed Maven
        PATH = "${JAVA_HOME}/bin;${MAVEN_HOME}/bin;${env.PATH}" // Add Maven and Java to PATH
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
