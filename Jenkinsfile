pipeline{
    agent any

    environment{
        dockerImageName = "dmitrykaplan/api:1"
        dockerImage = ""
        JWT = credentials('jwt-kube')
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
                    sh "kubectl --token=${env.JWT} --server=https://192.168.49.2:8443 --validate=false apply -f api-server.yaml"
                }
            }
        }
    }
}