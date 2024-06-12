package utils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;


public class ExcelUtils {
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    public ExcelUtils() {
        this(null, null);
    }

    public ExcelUtils(String excelPath) {
        this(excelPath, null);
    }

    public ExcelUtils(String excelPath, String sheetName) {
        if (excelPath == null) {
            String projectPath = System.getProperty("user.dir");
            excelPath = projectPath + "/excel/data.xlsx";
        }
        try {
            workbook = new XSSFWorkbook(excelPath);
            if (sheetName == null) {
                sheet = workbook.getSheetAt(0);
            } else {
                sheet = workbook.getSheet(sheetName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

    }

    public String getCellData(int row, int column) {
        DataFormatter formatter = new DataFormatter();
        Cell cell = sheet.getRow(row).getCell(column);
        return formatter.formatCellValue(cell);
    }

    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public int  getColumnCount() {
        return  sheet.getRow(0).getPhysicalNumberOfCells();
    }
}