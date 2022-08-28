pipeline {
  agent any
  stages {
    stage('Build') {
      agent {
        docker {
          image 'maven:3-alpine AS build'
        }
      }
      steps {
        sh 'mvn clean package -DskipTests -P FullStack'
      }
    stage('server') {
      steps {
        sh 'docker build -t auth_image .'
      }
    }
    stage('docker') {
      steps {
        sh 'docker-compose up --build -d'
      }
    }

  }
}
