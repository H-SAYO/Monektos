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
            def description = currentBuild.result == 'SUCCESS' ? 'Build successful! 🎉' : 'Build failed. ❌'
            def context = 'continuous-integration/jenkins'
            def sha = bat(script: 'git rev-parse HEAD', returnStdout: true).trim()

            // Make sure to remove any unwanted characters if present
            sha = sha.replaceAll(/[^0-9a-f]/, '')

            def response = httpRequest(
                httpMode: 'POST',
                url: "https://api.github.com/repos/${env.GITHUB_REPOSITORY}/statuses/${sha}",
                contentType: 'APPLICATION_JSON',
                requestBody: "{\"state\": \"${status}\", \"description\": \"${description}\", \"context\": \"${context}\"}",
                customHeaders: [[name: 'Authorization', value: "token ${GITHUB_TOKEN}"]],
                validResponseCodes: '200'
            )
            echo "GitHub status update response: ${response.status}"
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
