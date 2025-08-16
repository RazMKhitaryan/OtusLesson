pipeline {
    agent { label 'maven' }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Choose browser for the tests')
        string(name: 'BRANCH', defaultValue: 'project-with-spring-testng', description: 'Git branch to run tests from')
    }

    stages {
        stage('Test Allure CLI') {
            steps {
                sh "allure --version"
            }
        }

        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: "${params.BRANCH}"]],
                          userRemoteConfigs: [[url: 'https://github.com/RazMKhitaryan/OtusLesson.git']]
                ])
            }
        }

        stage('Run Tests') {
            steps {
                sh """
                mvn clean test \
                    -Dbrowser=${params.BROWSER} \
                    -DbaseUrl=https://otus.ru \
                    -Dmode=remote \
                    -Durl=http://45.132.17.22/wd/hub \
                    -DthreadCount=3 \
                    -Dsurefire.testFailureIgnore=true || true
                """
            }
        }
    }

    post {
        always {
            echo "Publishing Allure results..."
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
            echo "Pipeline finished"
        }
    }
}
