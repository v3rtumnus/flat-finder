node {
    stage 'Clone sources'
        git url: 'https://github.com/v3rtumnus/flat-finder.git'

    stage 'Create bootable jar'
        sh './gradlew clean bootJar'
}