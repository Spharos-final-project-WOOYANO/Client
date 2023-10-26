pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'jenkins-github-access-token', url:'https://github.com/Spharos-final-project-WOOYANO/Client'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    docker stop client-service || true
                    docker rm client-service || true
                    docker rmi client-service-img || true
                    docker build -t client-service-img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --name client-service -p 8001:8000 client-service-img'
            }
        }
    }
}
