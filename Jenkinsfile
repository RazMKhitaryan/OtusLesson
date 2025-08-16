pipeline {
    agent { label 'maven' }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Choose browser for the tests'
        )

        // Git parameter (requires Git Parameter Plugin)
        gitParameter(
            branch: '',
            branchFilter: 'origin/(.*)',
            defaultValue: 'project-with-spring-testng',
            description: 'Select a Git branch to build',
            name: 'BRANCH',
            quickFilterEnabled: true,
            selectedValue: 'DEFAULT',
            sortMode: 'DESCENDING',
            type: 'PT_BRANCH'
        )
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
                results: [[path: 'allure-results']]
            ])
            echo "Pipeline finished"
        }
    }
}
