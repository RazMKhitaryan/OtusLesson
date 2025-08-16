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

               sh 'allure generate --clean allure-results'
               echo "allure folder generated"

               script {
                   def summaryFile = 'allure-report/widgets/summary.json'
                   def summaryContent = readFile(summaryFile)

                   def json = new JsonSlurper().parseText(summaryContent)

                   def passedCount = json.statistic.passed
                   def totalCount = json.statistic.total
                   def message = "Allure Report Web run: ${passedCount}/${totalCount} tests passed ✅"

                   def botToken = '8228531250:AAF4-CNqenOBmhO_U0qOq1pcpvMDNY0RvBU'
                   def chatId = '6877916742'
                   sh """
                   curl -s -X POST https://api.telegram.org/bot${botToken}/sendMessage \
                        -d chat_id=${chatId} \
                        -d text="${message}"
                   """
               }

               echo "Pipeline finished"
           }
}
