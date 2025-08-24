import groovy.json.JsonSlurper
import hudson.triggers.SCMTrigger

node('maven') {

    // Parameters and Triggers
    properties([
        parameters([
            choice(
                name: 'BROWSER',
                choices: ['chrome', 'firefox'],
                description: 'Choose browser for the tests'
            ),
            gitParameter(
                name: 'BRANCH',
                branch: '',
                branchFilter: 'origin/(.*)',
                defaultValue: 'project-with-spring-testng',
                description: 'Select a Git branch to build',
                quickFilterEnabled: true,
                selectedValue: 'DEFAULT',
                sortMode: 'DESCENDING',
                type: 'PT_BRANCH'
            )
        ]),
        pipelineTriggers([
            [$class: 'SCMTrigger', scmpoll_spec: '* * * * *'],   // poll every minute
            [$class: 'TimerTrigger', spec: '0 21 * * *']        // daily at 21:00
        ])
    ])

    // Stage: Check Allure CLI
    stage('Check Allure CLI') {
        sh "allure --version"
    }

    // Stage: Checkout
    stage('Checkout') {
        checkout([
            $class: 'GitSCM',
            branches: [[ name: "${params.BRANCH}" ]],
            userRemoteConfigs: [[ url: 'https://github.com/RazMKhitaryan/OtusLesson.git' ]]
        ])
    }

    // Stage: Run UI Tests
    stage('Run UI Tests') {
        sh """
        docker ps
        docker run --rm \
            --name ui_tests_run \
            --network host \
            -e BROWSER=${params.BROWSER} \
            -v ${WORKSPACE}/allure-results:/app/allure-results \
            -v ${WORKSPACE}/allure-report:/app/allure-report \
            localhost:5005/ui_tests:latest
        """
    }

    // Post-processing inside Scripted Pipeline
    stage('Publish Results & Notify') {
        try {
            echo "Publishing Allure results..."
            sh "allure generate ${WORKSPACE}/allure-results -o ${WORKSPACE}/allure-report --clean"

            def summaryFile = readFile('allure-report/widgets/summary.json')
            def summary = new JsonSlurper().parseText(summaryFile)

            def total = summary.statistic.total ?: 0
            def passed = summary.statistic.passed ?: 0

            def message = """✅ Web Test Execution Finished
Passed: ${passed}/${total}
"""

            sh """
                curl -s -X POST https://api.telegram.org/bot<bot_token>/sendMessage \
                     -d chat_id=<chat_id> \
                     -d text="${message}"
            """
        } catch (Exception e) {
            echo "⚠️ Could not read Allure summary or send Telegram notification: ${e.message}"
        }
    }
}
