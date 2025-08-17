import groovy.json.JsonSlurper

pipeline {
    agent { label 'maven2' }

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

    triggers {
        // Trigger on SCM changes (after every merge/push)
        pollSCM('* * * * *') // checks every minute, adjust as needed

        // Trigger every day at 21:00
        cron('0 21 * * *')
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
                try {
                    // Read and parse the summary JSON safely
                    def summaryFile = readFile('allure-report/widgets/summary.json')
                    def summary = new JsonSlurper().parseText(summaryFile)

                    def total = summary.statistic.total ?: 0
                    def passed = summary.statistic.passed ?: 0

                    def message = """✅ Web Test Execution Finished
                    Passed: ${passed}/${total}
                    """

                    // Send message to Telegram
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
