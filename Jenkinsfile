pipeline{
    agent any

    environment{
        DOCKER_IMAGE_NAME = "dmitrykaplan/api"
        dockerImage = ""
        TIMESTAMP = sh(script: 'date +%s', returnStdout: true).trim()
        KUBECONFIG = credentials('kube-config-path')
    }

    stages{
        stage("docker build"){
            steps{
               script {
                    dockerImage = docker.build dockerImage
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
                        dockerImage.push(TIMESTAMP)
                    }
                }
            }
        }

        stage("update configs"){
            steps{
                sh 'kubectl apply -f api-server.yaml'
            }
        }

        stage("deploying api-gateway"){
            steps {
                script {
                    sh('KUBECONFIG=${KUBECONFIG}')
                    sh('kubectl set image deployments/api-server-deployment api-server=$DOCKER_IMAGE_NAME:$TIMESTAMP')
                }
            }
        }
    }
}