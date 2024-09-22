pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    def os = System.properties['os.name'].toLowerCase()
                    echo "Detected OS: ${os}"

                    if (os.contains("linux")) {
                        // Execute Maven install on Linux
                        sh "mvn clean install"
                    } else {
                        // Execute Maven install on Windows
                        bat "mvn clean install"
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    def os = System.properties['os.name'].toLowerCase()
                    echo "Detected OS: ${os}"

                    if (os.contains("linux")) {
                        // Execute tests on Linux
                        sh "mvn test"
                    } else {
                        // Execute tests on Windows
                        bat "mvn test"
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    def os = System.properties['os.name'].toLowerCase()
                    echo "Detected OS: ${os}"

                    if (os.contains("linux")) {
                        // Execute deployment on Linux
                        sh "mvn deploy"
                    } else {
                        // Execute deployment on Windows
                        bat "mvn deploy"
                    }
                }
            }
        }
    }
}
