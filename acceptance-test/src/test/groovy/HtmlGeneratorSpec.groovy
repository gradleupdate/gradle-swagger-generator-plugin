import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification

import static Fixture.cleanBuildDir
import static Fixture.placePetstoreYaml

class HtmlGeneratorSpec extends Specification {

    GradleRunner runner

    def setup() {
        runner = GradleRunner.create()
            .withProjectDir(new File('html-generator'))
            .withPluginClasspath()
            .forwardOutput()
        cleanBuildDir(runner)
    }

    def 'generateSwaggerCode task should generate a document'() {
        given:
        placePetstoreYaml(runner, Fixture.PetstoreYaml.valid)
        runner.withArguments('--stacktrace', 'generateSwaggerCode')

        when:
        runner.build()

        then:
        new File(runner.projectDir, 'build/swagger-html/index.html').exists()
    }

    def 'generateSwaggerCodeHelp task should show help'() {
        given:
        runner.withArguments('--stacktrace', 'generateSwaggerCodeHelp')

        when:
        def result = runner.build()

        then:
        result.output.contains('CONFIG OPTIONS')
    }

}
