package utils;

public class ExcelUtilsDemo {

    public static void main(String[] args){
        ExcelUtils excel = new ExcelUtils();
        int rowCount = excel.getRowCount();
        int columnCount = excel.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String cellData = excel.getCellData(i, j);
                System.out.print(cellData + " | ");
            }
            System.out.println();
        }
    }
}
