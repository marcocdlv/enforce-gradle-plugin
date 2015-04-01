/*
 * Copyright (c) Jalasoft Corporation. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.jalasoft.gradle.plugins.enforce.unittest.Apex

class ApexTestItem {

    boolean complete

    ArrayList<ApexRunTestResult> apexTestResults

    public ApexTestItem() {
        apexTestResults = new ArrayList<ApexRunTestResult>()
    }
}
