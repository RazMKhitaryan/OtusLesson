import groovy.json.JsonSlurper

pipeline {
    agent { label 'maven' }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Choose browser for the tests'
        )

        // Git Parameter (requires Git Parameter Plugin)
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
                checkout([
                    $class: 'GitSCM',
                    branches: [[ name: "${params.BRANCH}" ]],
                    userRemoteConfigs: [[ url: 'https://github.com/RazMKhitaryan/OtusLesson.git' ]]
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
                reportBuildPolicy: 'ALWAYS',
                results: [[ path: 'allure-results' ]]
            ])

            sh 'allure generate --clean allure-results'
            echo "Allure report generated"

            script {
                // Read and parse the summary JSON
                def summaryFile = readFile('allure-report/widgets/summary.json')
                def summary = new JsonSlurper().parseText(summaryFile)

                def total = summary.statistic.total
                def passed = summary.statistic.passed

                // Compute pass rate: convert to double and use .round(2)
                def passRate = total > 0
                    ? ((passed * 100.0 / total) as Double).round(2)
                    : 0.0

                def message = """✅ Web Test Execution Finished
Total: ${total}
Passed: ${passed}
Pass Rate: ${passRate}%
"""

                // Send message to Telegram
                sh """
                    curl -s -X POST https://api.telegram.org/bot8228531250:AAF4-CNqenOBmhO_U0qOq1pcpvMDNY0RvBU/sendMessage \
                         -d chat_id=6877916742 \
                         -d text="${message}"
                """
            }
        }
    }
}
