pipeline {
  agent any
  stages {
  stage('Build') {
      steps {
        script {
          echo 'mvn clean package -DskipTests -P FullStack'
        }
      }
    }
  stage('Test') {
      steps {
        script {
          echo 'mvn test'
        }
      }
    }
  stage('server') {
      steps {
        script {
          echo 'docker build image -t auth_image .'
        }
      }
    }
  stage('docker') {
      steps {
        script {
          echo 'docker-compose up --build -d'
        }
      }
    }

  }
}
