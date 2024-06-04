pipeline{
    agent any

    environment{
        dockerImageName = "dmitrykaplan/api"
        dockerImage = ""
        TIMESTAMP = sh(script: 'date +%s', returnStdout: true).trim()
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
                    sh('kubectl set image deployments/api-server-deployment api-server=api:$TIMESTAMP')
                    sh 'kubectl rollout restart deployment api-server-deployment'
                }
            }
        }
    }
}