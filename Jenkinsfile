pipeline{
    agent any
    stages{
        stage("docker build"){
            steps{
                echo 'DOCKER BUILD STARTED......................................'
                sh 'docker build -t api:1 .'
                echo 'DOCKER BUILD FINISHED......................................'
            }
        }

        stage("update image in kubernetes"){
            steps{
                sh 'minikube image load api:1'
            }
        }

        stage("update kubernetes deployment"){
            steps{
                sh 'kubectl apply -f api-server.yaml'
            }
        }

        stage("restart api-server pods"){
            steps{
                sh 'kubectl rollout restart deployment api-server-deployment'
            }
        }
    }
}