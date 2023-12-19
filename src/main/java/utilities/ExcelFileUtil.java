package utilities;

import common.CommonClass;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelFileUtil extends CommonClass {
    public String fileName = "";

    public ExcelFileUtil() {
        super();
        inputStream = null;
        outputStream = null;
        workbook = null;

    }

    public Workbook checkFile(String fileName) {
        try {
            fileName = fileName.trim();
            if (fileName.isEmpty()) {
                throw new Exception("No file name specified...");
            } else {
                if (!(new File(fileName)).exists()) {
                    throw new Exception("File does not Exists");
                } else {
                    if (fileName.toLowerCase().endsWith("xlsx")) {
                        workbook = new XSSFWorkbook();
                    } else if (fileName.toLowerCase().endsWith("xls")) {
                        workbook = new HSSFWorkbook();
                    } else {
                        logger.error("Invalid File Name!!!");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
            workbook = null;
        }
        return workbook;
    }

    public void createNewExcelWorkbook(String fileName) {
        try {
            fileName = fileName.trim();
            workbook = checkFile(fileName);
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            logger.info("Created file " + fileName + " successfully!!!!");
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.error("Exception Occurred :" + e.getMessage());
            }
        }
    }

    public void save(String fileName) {
        try {
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
        } catch (IOException e) {
            logger.error("Exception Occurred :" + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.error("Exception Occurred :" + e.getMessage());
            }
        }
    }

    public Object[][] getTestData(String sheetName) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(TESTDATA_SHEET_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            workbook = WorkbookFactory.create(file);
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
        sheet = workbook.getSheet(sheetName);

        Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
                data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
            }
        }

        return data;

    }

}
/*
    public void openExcelWorkbook(String fileName) {
        try {
            workbook = checkFile(fileName);
            inputStream = new FileInputStream(fileName);
            this.fileName = fileName;
//            workbook=WorkbookFactory.create(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------


    // ----------------------------------------------------------------

    public void saveAs(String sFileName) {
        try {
            workbook = checkFile(fileName);
            outputStream = new FileOutputStream(sFileName);
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createSheet(String sSheetName, String sWorkbook) {
        try {
            sSheetName = sSheetName.trim();
            if (sSheetName.isEmpty()) {
                throw new Exception("Sheet name not specified");

            }

            Sheet oSheet;
            openExcelWorkbook(sWorkbook);
            oSheet = workbook.getSheet(sSheetName);
            if (oSheet != null) {
                throw new Exception("Sheet already Exist");
            }
            workbook.createSheet();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // --------------------------------------------------------------------

    public int getRowCountOfSheet(String sSheetName) {
        try {
            sSheetName = sSheetName.trim();
            if (sSheetName.isEmpty()) {
                throw new Exception("Sheet name not specified");

            }
            Sheet oSheet;
            oSheet = workbook.getSheet(sSheetName);
            System.out.println("Sheet Name: " + sSheetName);
            if (oSheet == null) {
                throw new Exception("Sheet does not Exist");
            }

            return oSheet.getLastRowNum();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // ------------------------------------------------------------------------

    public int getCellCount(String sSheetName, int iRow) {
        try {
            sSheetName = sSheetName.trim();
            if (sSheetName.isEmpty()) {
                throw new Exception("Sheet name not specified");

            }

            Sheet oSheet;

            oSheet = workbook.getSheet(sSheetName);
            if (oSheet == null) {
                throw new Exception("Sheet doesnot Exist");
            }

            if (iRow < 1) {
                throw new Exception("ROw Index start from 1");
            }

            Row oRow;

            oRow = oSheet.getRow(iRow - 1);

            if (oRow == null) {
                return 0;
            } else {
                return oRow.getLastCellNum() + 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // ---------------------------------------------------------------------

    public String getCellCData(String sSheetName, int iRow, int iCell) { // irow=2,icell = 2

        try {
            sSheetName = sSheetName.trim();
            if (sSheetName.isEmpty()) {
                throw new Exception("Sheet name not specified");
            }
            oSheet = workbook.getSheet(sSheetName);

            if (oSheet == null) {
                throw new Exception("Sheet does not Exist");
            }
            if (iRow < 1 || iCell < 1) {
                throw new Exception("Row & Cell Index start from 1");
            }
            oRow = oSheet.getRow(iRow - 1);

            if (oRow == null) {
                return "";
            }
            oCell = oRow.getCell(iCell - 1);
            if (oCell == null) {
                // fill the return string as per your requirement
                return "";
            } else {
                CellType type = oCell.getCellTypeEnum();

                if (type == CellType.NUMERIC) {
                    return String.valueOf((long) oCell.getNumericCellValue());
                } else {
                    return oCell.getStringCellValue();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    // ----------------------------------------------------------------

    public void setCellCData(String sSheetName, int iRow, int iCell,
                             String sValue) {
        try {
            sSheetName = sSheetName.trim();
            if (sSheetName.isEmpty()) {
                throw new Exception("Sheet name not specified");

            }

            Sheet oSheet;

            oSheet = workbook.getSheet(sSheetName);
            if (oSheet == null) {
                throw new Exception("Sheet doesnot  Exist");
            }

            if (iRow < 1 || iCell < 1) {
                throw new Exception("Row & Cell Index start from 1");
            }

            Row oRow;

            oRow = oSheet.getRow(iRow - 1);

            if (oRow == null) {
                oSheet.createRow(iRow - 1);
                oRow = oSheet.getRow(iRow - 1);
            }

            Cell oCell;
            oCell = oRow.getCell(iCell - 1);
            if (oCell == null) {
                oRow.createCell(iCell - 1);
                oCell = oRow.getCell(iCell - 1);
            }

            oCell.setCellValue(sValue);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }*/


