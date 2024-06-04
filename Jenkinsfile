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