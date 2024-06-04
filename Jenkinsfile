pipeline{
    agent any
    stages{
        stage("docker build"){
            agent{
                dockerfile{
                    args '-t api:1'
                }
            }
            steps{
                echo 'DOCKER BUILD STARTED......................................'
            }
        }
    }
}