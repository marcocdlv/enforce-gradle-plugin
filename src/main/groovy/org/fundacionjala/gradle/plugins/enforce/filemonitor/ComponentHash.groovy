package org.fundacionjala.gradle.plugins.enforce.filemonitor

class ComponentHash implements Serializable, ComponentComparable<ComponentHash> {
    public String fileName
    public String hash

    public ComponentHash(String fileName, String hash) {
        this.fileName = fileName
        this.hash = hash
    }

    public ComponentHash() {
        this.hash = ""
        this.fileName = ""
    }

    public ResultTracker compare(ComponentHash componentHash) {
        ResultTracker resultTracker = new ResultTracker()
        resultTracker.state = ComponentStates.NOT_CHANGED
        if (componentHash.hash != hash) {
            resultTracker.state = ComponentStates.CHANGED
        }
        return resultTracker
    }
}