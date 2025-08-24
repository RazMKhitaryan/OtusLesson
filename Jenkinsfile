import groovy.json.JsonSlurper

pipeline {
    agent { label 'maven' }   // use your custom Maven+Docker agent

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Choose browser for the tests'
        )

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

    triggers {
        pollSCM('* * * * *')   // every minute
        cron('0 21 * * *')     // every day at 21:00
    }

    stages {
        stage('Check Allure CLI') {
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

        stage('Run UI Tests') {
            steps {
                sh """
                docker ps \
                docker run --rm \
                    --name ui_tests_run \
                    --network host \
                    -e BROWSER=${params.BROWSER} \
                    -v /var/run/docker.sock:/var/run/docker.sock \
                    -v ${WORKSPACE}/allure-results:/app/allure-results \
                    -v ${WORKSPACE}/allure-report:/app/allure-report \
                    localhost:5005/ui_tests:latest
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

            script {
                try {
                    def summaryFile = readFile('allure-report/widgets/summary.json')
                    def summary = new JsonSlurper().parseText(summaryFile)

                    def total = summary.statistic.total ?: 0
                    def passed = summary.statistic.passed ?: 0

                    def message = """✅ Web Test Execution Finished
Passed: ${passed}/${total}
"""

                    sh """
                        curl -s -X POST https://api.telegram.org/bot8228531250:AAF4-CNqenOBmhO_U0qOq1pcpvMDNY0RvBU/sendMessage \
                             -d chat_id=6877916742 \
                             -d text="${message}"
                    """
                } catch (Exception e) {
                    echo "⚠️ Could not read allure summary or send Telegram notification: ${e.message}"
                }
            }
        }
    }
}
