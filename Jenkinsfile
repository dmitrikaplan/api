pipeline{
    agent any

    environment{
        dockerImageName = "dmitrykaplan/api:1"
        dockerImage = ""
    }
    stages{
        stage("docker build"){
            steps{
               script {
                    dockerImage = docker.build dockerImageName
                }
            }
        }

        stage("pushing docker image"){
            environment{
                registryCredential = 'dockerhub-credentials'
            }
            steps{
                script {
                    docker.withRegistry( 'https://registry.hub.docker.com', registryCredential ) {
                        dockerImage.push("1")
                    }
                }
            }

        }

        stage("deploying api-gateway"){
            steps {
                script {
                    JWT = credentials('jwt-kube')
                    echo '${JWT}'
                    sh 'kubectl --token=${JWT} --server=https://192.168.49.2:8443 apply -f api-server.yaml'
                }
            }
        }
    }
}