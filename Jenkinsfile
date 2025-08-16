pipeline {
    agent { label 'maven' }

    stages {
        stage('Test Allure CLI') {
            steps {
                sh "allure --version"
            }
        }

        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: 'project-with-spring-testng']],
                          userRemoteConfigs: [[url: 'https://github.com/RazMKhitaryan/OtusLesson.git']]
                ])
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests; do not fail pipeline even if tests fail
                sh """
                mvn clean test \
                    -Dbrowser=chrome \
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
