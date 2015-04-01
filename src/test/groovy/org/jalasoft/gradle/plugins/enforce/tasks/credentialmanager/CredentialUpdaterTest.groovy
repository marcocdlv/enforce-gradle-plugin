/*
 * Copyright (c) Jalasoft Corporation. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.jalasoft.gradle.plugins.enforce.tasks.credentialmanager

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.jalasoft.gradle.plugins.enforce.EnforcePlugin
import org.jalasoft.gradle.plugins.enforce.wsc.Credential
import spock.lang.Shared
import spock.lang.Specification

class CredentialUpdaterTest extends Specification {

    @Shared
    Project project

    @Shared
    CredentialUpdater credentialUpdater

    @Shared
    Credential credential

    def setup() {
        project = ProjectBuilder.builder().build()
        project.apply(plugin: EnforcePlugin)
        credentialUpdater = project.task('updateCredentialTest', type: CredentialUpdater)
    }

    def "Test should be instance of CredentialEncryptionExecutor"() {
        expect:
        credentialUpdater instanceof CredentialUpdater
    }
}
