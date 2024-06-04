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
                    kubernetesDeploy(configs: "api-server.yaml",)
                }
            }
        }
    }
}