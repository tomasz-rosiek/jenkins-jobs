package uk.gov.hmrc.jenkinsjobs.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.Builder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.variables.JdkEnvironmentVariable

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.BuildDescriptionPublisher.buildDescriptionByRegexPublisher
import static uk.gov.hmrc.jenkinsjobs.domain.builder.JobBuilders.jobBuilder
import static uk.gov.hmrc.jenkinsjobs.domain.publisher.Publishers.defaultHtmlReportsPublisher
import static uk.gov.hmrc.jenkinsjobs.domain.publisher.Publishers.defaultJUnitReportsPublisher
import static uk.gov.hmrc.jenkinsjobs.domain.step.Steps.sbtCleanTestItTestDistPublish

final class SbtFrontendJobBuilder implements Builder<Job> {

    private JobBuilder jobBuilder

    SbtFrontendJobBuilder(String name, String repository = "hmrc/${name}", JdkEnvironmentVariable jdkEnvironmentVariable) {
        jobBuilder = jobBuilder(name, repository, jdkEnvironmentVariable).
                                withSteps(sbtCleanTestItTestDistPublish()).
                                withPublishers(defaultHtmlReportsPublisher(),
                                               buildDescriptionByRegexPublisher('.*sbt git versioned as ([\\w\\d\\.\\-]+)'),
                                               defaultJUnitReportsPublisher())
    }

    Job build(DslFactory dslFactory) {
        jobBuilder.build(dslFactory)
    }
}