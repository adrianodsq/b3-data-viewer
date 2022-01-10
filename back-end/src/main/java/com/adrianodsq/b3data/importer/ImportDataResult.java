package com.adrianodsq.b3data.importer;

import java.io.Serializable;

public class ImportDataResult implements Serializable {

    int success = 0;
    int failed = 0;

    public void addSuccess(){
        success++;
    }

    public void addFailure(){
        failed++;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }
}
