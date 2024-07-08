pipeline {
  agent any
  environment {
    PATH = "opt/maven3/bin:$PATH"
  }
  options {
    retry(3)
  }
  stages {
    stage("Checkout SCM") {
        steps {
            git branch: 'main', credentialsId: '7d1ace5b-9743-4c46-93c1-758115b60473', url: 'https://github.com/abarna137/RestAssuredFramework'
        }
    }
    stage("Compile") {
        steps {
            echo "mvn clean compile"
        }
    }
    stage("Build") {
        steps {
            echo "mvn install"
        }
    }
    stage("Test") {
        steps {
            echo "mvn test"
        }
    }
    stage("Deploy") {
        steps {
            bat "mvn package"
        }
    }
  }
}

