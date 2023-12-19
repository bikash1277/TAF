package common;


import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.InputStream;
import java.io.OutputStream;

public class CommonClass {
    public final Logger logger = Logger.getLogger(getClass().getSimpleName());
    public final static String TESTDATA_SHEET_PATH = "src/test/resources/data/excel/testdata.xlsx";
    public static int lngPageLoadTimeOut = 60;
    public static int lngElementDetectionTimeOut = 30;

    public static int RESPONSE_STATUS_CODE_200 = 200;
    public static int RESPONSE_STATUS_CODE_201 = 201;
    public static int RESPONSE_STATUS_CODE_400 = 400;
    public static int RESPONSE_STATUS_CODE_404 = 404;
    public static int RESPONSE_STATUS_CODE_500 = 500;
    public static int RESPONSE_STATUS_CODE_403 = 403;
    public static WebDriver driver;
    public static JavascriptExecutor javascriptExecutor;
    public static InputStream inputStream;
    public static OutputStream outputStream;
    public static Workbook workbook;
    public static Sheet sheet;
    public static Row row;
    public static Cell cell;

}
