pipeline{
    agent any

    environment{
        dockerImageName = "dmitrykaplan/api:1"
        dockerImage = ""
        KUBECONFIG = credentials('kube-config-path')
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

        stage("update configs"){
            steps{
                sh 'kubectl apply -f api-server.yaml'
            }
        }

        stage("deploying api-gateway"){
            steps {
                script {
                    sh('KUBECONFIG=${KUBECONFIG}')
                    sh 'kubectl rollout restart deployment api-server-deployment'
                }
            }
        }
    }
}