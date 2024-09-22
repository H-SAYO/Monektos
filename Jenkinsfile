pipeline {
    agent any  // Use the default Jenkins agent

    environment {
        DB_USERNAME = 'postgres'
        DB_PASSWORD = 'postgres'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from Git
                git branch: 'main', url: 'https://github.com/H-SAYO/Monektos.git'
            }
        }

        stage('Set up JDK 17') {
            steps {
                // Set up JDK 17
                script {
                    def javaHome = tool name: 'JDK 17', type: 'jdk'
                    env.JAVA_HOME = javaHome
                    env.PATH = "${javaHome}/bin:${env.PATH}"
                }
            }
        }

        stage('Start PostgreSQL') {
            steps {
                // Start PostgreSQL service
                script {
                    bat '''
                    docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=monektos -p 5432:5432 -d postgres:14
                    timeout /t 20
                    '''
                }
            }
        }

        stage('Build with Maven') {
            steps {
                // Build the project with Maven
                sh 'mvn -B package --file pom.xml'
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests with Maven
                sh 'mvn -B test --file pom.xml'
            }
        }

        stage('Cleanup') {
            steps {
                // Stop and remove PostgreSQL container
                sh 'docker stop postgres'
                sh 'docker rm postgres'
            }
        }
    }
}
