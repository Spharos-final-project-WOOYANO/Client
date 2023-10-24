pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Client'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    cd Client
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    cd server
                    docker stop Client-Service || true
                    docker rm Client-Service || true
                    docker rmi Client-Service-Img || true
                    docker build -t Client-Service-Img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --name Client-Service -p 8080:8000 Client-Service-Img'
            }
        }
    }
}
