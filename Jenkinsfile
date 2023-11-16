pipeline {
    agent any
    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Client'
            }
        }
	stage('Secret-File Download') {
	    steps {
	        withCredentials([
		    file(credentialsId: 'Client-Secret-File', variable:'clientSecret')
		    ])
            {
                sh 'cp \$clientSecret ./src/main/resources/application-secret.yml'
            }
  	    }
	}
        stage('Build'){
            steps{
                script {
                    sh '''
                        pwd
                        chmod +x ./gradlew
                        ./gradlew clean build

                    '''

                }

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
                sh 'docker run --restart=always --network spharos-network -e EUREKA_URL="${EUREKA_URL}" -d --name client-service client-service-img:latest'
            }
        }
    }
}
