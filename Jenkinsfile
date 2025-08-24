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

      stage('Install Docker CLI') {
          sh """
          # Update packages
          apt-get update -y

          # Install dependencies
          apt-get install -y ca-certificates curl gnupg lsb-release

          # Add Docker GPG key
          mkdir -p /etc/apt/keyrings
          curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg

          # Add Docker repository
          echo "deb [arch=\$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \$(lsb_release -cs) stable" \
              | tee /etc/apt/sources.list.d/docker.list > /dev/null

          # Install Docker CLI
          apt-get update -y
          apt-get install -y docker-ce-cli docker-buildx-plugin docker-compose-plugin

          # Check version
          docker --version
          """
      }

    // Stage: Run UI Tests
    stage('Run UI Tests') {
        sh """
        sudo systemctl start docker
        sudo systemctl enable docker
        sudo systemctl status docker

        pwd
        whoami
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
