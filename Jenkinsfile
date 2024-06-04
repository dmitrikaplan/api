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
    }
}