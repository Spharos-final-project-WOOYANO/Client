pipeline {
    agent any
    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Client'
            }
        }
	stage('Secret-File Download'){
	    steps {
	        withCredentials([
		    file(credentialsId: 'Admin-Secret-File', variable: 'adminsecret')
		    ])
		{
		    sh 'cp \$adminsecret ./src/main/resources/application-secret.yml'
		}
	    }
	}
        stage('Build'){
            steps{
                script {
                    sh '''
                        pwd
                        chmod +x ./gradlew
                        ./gradlew build -x test
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
                sh 'docker run --network spharos-network --name client-service client-service-img'
            }
        }
    }
}

