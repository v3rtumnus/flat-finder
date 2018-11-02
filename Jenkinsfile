node {
    stage 'Clone sources'
        git url: 'https://github.com/v3rtumnus/flat-finder.git'

    stage 'Create bootable jar'
        sh './gradlew clean bootJar'

    stage 'Deploy service'
        sh 'sudo service flat-finder stop'
        sh 'cp build/libs/flat-finder.jar /opt/flat-finder/'
        sh 'chmod a+x /opt/flat-finder/flat-finder.jar'
        sh 'sudo service flat-finder start'
}