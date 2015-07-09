/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package org.fundacionjala.gradle.plugins.enforce.tasks.salesforce.deployment

import org.fundacionjala.gradle.plugins.enforce.utils.Constants
import org.fundacionjala.gradle.plugins.enforce.utils.Util
import org.fundacionjala.gradle.plugins.enforce.utils.salesforce.OrgValidator

/**
 * Deletes files into an org using metadata API
 */
class Delete extends Deployment {
    public ArrayList<File> filesToDeleted
    public String files, excludes

    /**
     * Sets description and group task
     * @param description is description tasks
     * @param group is the group typeName the task
     */
    Delete() {
        super(Constants.DESCRIPTION_DELETE_TASK, Constants.DEPLOYMENT)
        filesToDeleted = new ArrayList<File>()
        taskFolderName = Constants.DIR_DELETE_FOLDER
    }

    /**
     * Executes the task
     */
    @Override
    void runTask() {
        createDeploymentDirectory(taskFolderPath)
        addFiles()
        validateFilesInOrg()
        showFilesToDelete()
        if( System.console().readLine("\n"+Constants.QUESTION_CONTINUE_DELETE) == Constants.YES_OPTION ) {
            createDestructive()
            createPackageEmpty()
            executeDeploy(taskFolderPath, Constants.START_DELETE_TASK, Constants.SUCCESSFULLY_DELETE_TASK)
        }
        else {
            logger.quiet(Constants.PROCCES_DELETE_CANCELLED)
        }
    }

    /**
     * Initializes all task parameters
     * @param properties the task properties
     * @return A map of all task parameters
     */
    void loadParameters() {
        if (Util.isValidProperty(parameters, Constants.PARAMETER_FILES)) {
            files = parameters[Constants.PARAMETER_FILES]
        }
        if (Util.isValidProperty(parameters, Constants.PARAMETER_EXCLUDES)) {
            excludes = parameters[Constants.PARAMETER_EXCLUDES]
        }
    }

    /**
     * Adds all files into an org
     */
    def addFiles() {
        filter.excludeFiles.addAll([Constants.PACKAGE_FILE_NAME])
        filesToDeleted = getClassifiedFiles(files, excludes).get(Constants.VALID_FILE)
    }

    /**
     * Filter the files into Org
     */
    def validateFilesInOrg() {
        if(!parameters.get(Constants.PARAMETER_VALIDATE_ORG).equals(Constants.FALSE_OPTION)) {
            filesToDeleted = OrgValidator.getValidFiles(credential,filesToDeleted,projectPath)
        }
    }

    /**
     * Shows files to delete
     */
    def showFilesToDelete() {
        def limit = 15
        ArrayList<File> showFiles = filesToDeleted.findAll { File file ->
            !file.getName().endsWith("xml")
        }
        def numComponentes = showFiles.size()

        logger.quiet("*********************************************")
        logger.quiet("            Components to delete             ")
        logger.quiet("*********************************************")
        if(numComponentes == 0) {
            logger.quiet(Constants.NOT_FILES_DELETED)
        }
        else if(numComponentes > limit) {
            showFiles.groupBy { File file ->
                file.getParentFile().getName()
            }.each { group, files ->
                logger.quiet("[ " + files.size() + " ] " + group)
            }
            logger.quiet(numComponentes+" components")
        }
        else {
            showFiles.each { File file ->
                logger.quiet( Util.getRelativePath(file, projectPath))
            }
        }
        logger.quiet("*********************************************")
    }

    /**
     * Creates packages to all files which has been deleted
     */
    def createDestructive() {
        writePackage(taskDestructivePath, filesToDeleted)
        combinePackageToUpdate(taskDestructivePath)
    }

    /**
     * Create a package empty
     */
    def createPackageEmpty() {
        writePackage(taskPackagePath, [])
    }
}