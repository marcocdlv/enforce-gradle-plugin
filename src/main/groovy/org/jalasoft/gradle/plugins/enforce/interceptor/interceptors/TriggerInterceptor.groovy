/*
 * Copyright (c) Jalasoft Corporation. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.jalasoft.gradle.plugins.enforce.interceptor.interceptors

import org.jalasoft.gradle.plugins.enforce.interceptor.commands.Trigger
import org.jalasoft.gradle.plugins.enforce.utils.ManagementFile
import org.jalasoft.gradle.plugins.enforce.utils.salesforce.MetadataComponents
import org.jalasoft.gradle.plugins.enforce.interceptor.MetadataInterceptor

/**
 * Implements methods to manage interceptors and load the triggers to truncate
 */
class TriggerInterceptor extends MetadataInterceptor {

    /**
     * Loads the trigger files to truncate
     */
    @Override
    void loadFiles(String sourcePath) {
        ManagementFile managementFile = new ManagementFile(sourcePath)
        files = managementFile.getFilesByFileExtension(MetadataComponents.TRIGGERS.extension)
    }

    /**
     * Loads interceptors by default
     */
    @Override
    void loadInterceptors() {
        addInterceptor(org.jalasoft.gradle.plugins.enforce.interceptor.Interceptor.TRUNCATE_TRIGGERS.id, new Trigger().execute)
    }
}
