pipeline{
    agent any

    environment{
        dockerImageName = "dmitrykaplan/api:1"
        dockerImage = ""
        JWT = credentials('jwt-kube')
        KUBECONFIG = '/home/newton/.minikube/machines/minikube/config.json'
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
                    sh "kubectl config use-context minikube;KUBECONFIG=${KUBECONFIG}; kubectl get pods"
                }
            }
        }
    }
}