package reporting;

import base.BaseClass;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import common.CommonClass;

import java.io.IOException;
import java.util.Arrays;

class logger extends CommonClass {
    public static ExtentTest extentTest;
    BaseClass baseClass;

    public void getResult(String stepName, String status) throws IOException {
        if (status.equalsIgnoreCase("Fail")) {
            extentTest.log(Status.FAIL, MarkupHelper.createLabel("Step:- '" + stepName + "' FAILED due to below issues:", ExtentColor.RED));
            extentTest.log(Status.FAIL, Arrays.toString((new Throwable()).getStackTrace()));
            extentTest.log(Status.FAIL, "Snapshot below: ", MediaEntityBuilder.createScreenCaptureFromPath(baseClass.getScreenshot()).build());
        } else if (status.equalsIgnoreCase("Pass")) {
            extentTest.log(Status.PASS, MarkupHelper.createLabel("Step:- '" + stepName + "' PASSED", ExtentColor.GREEN));
            extentTest.log(Status.PASS, "Snapshot below: ", MediaEntityBuilder.createScreenCaptureFromPath(baseClass.getScreenshot()).build());
        } else {
            extentTest.log(Status.SKIP, MarkupHelper.createLabel("Step:- '" + stepName + "' Test Case SKIPPED because the previous step failed", ExtentColor.ORANGE));
        }
    }
}
