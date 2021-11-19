package com.demo.services.excel;

import com.demo.entities.Boy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService{

    public void writeData(String pathname, String templateFile, List<Boy> listBoy) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        int rowCount = 0;
        int colCount = 0;

        Row row = sheet.createRow(rowCount++);
        Cell cell = row.createCell(colCount++);

        //set header row
        Row templateHeaderRow= getRow(templateFile,0);
        int lastCell = templateHeaderRow.getLastCellNum();
        String[] tittles = new String[lastCell];

        for (int i = 0; i < lastCell; i++) {
            Cell oldCell = templateHeaderRow.getCell(i);
            String header= oldCell.getStringCellValue();

            //set header cell style
            XSSFCellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            cell.setCellStyle(newCellStyle);

            //set header cell value
            cell.setCellValue(oldCell.getStringCellValue());
            tittles[i] = header;
            cell = row.createCell(colCount++);
        }


        //set data rows
        Row templateRow= getRow(templateFile,1);
        for (int i = 0; i < listBoy.size(); i++) {
            Boy boy = listBoy.get(i);
            Map<String, String> element = new HashMap<>();
            element.put("ID", boy.getId()+"");
            element.put("Name", boy.getName() );
            element.put("Age", boy.getAge().toString() );
            element.put("City", boy.getCity() );
            element.put("Height", boy.getHeight().toString() );
            element.put("Weight", boy.getWeight().toString() );
            element.put("Hobbit", boy.getHobbit().toString() );
            element.put("Hair color", boy.getHairColor() );
            element.put("Skill", boy.getSkill() );

            row = sheet.createRow(rowCount++);
            colCount = 0;
            for( int j=0; j< tittles.length; j++ )
            {
                cell = row.createCell(colCount++);
                //set style
                XSSFCellStyle style=workbook.createCellStyle();
                Cell oldCell = templateRow.getCell(j);
                style.cloneStyleFrom(oldCell.getCellStyle());
                cell.setCellStyle(style);

                //set value
                cell.setCellValue(element.get(tittles[j]));
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(pathname)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public void readData(String pathname) throws IOException {

        File file = new File(pathname);
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Cell out = null;
        Iterator<Row> rowIterator = sheet.iterator();


        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                CellType cellType = cell.getCellType();

                switch (cellType) {
                    case _NONE:
                        System.out.print("");
                        System.out.print("\t");
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        System.out.print("\t");
                        break;
                    case BLANK:
                        System.out.print("");
                        System.out.print("\t");
                        break;
                    case FORMULA:

                        System.out.print(cell.getCellFormula());
                        System.out.print("\t");

                        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                        System.out.print(evaluator.evaluate(cell).getNumberValue());
                        break;
                    case NUMERIC:
                        System.out.printf("%-10.0f", cell.getNumericCellValue());
                        System.out.print("\t");
                        break;
                    case STRING:
                        System.out.printf("%-18s", cell.getStringCellValue());
                        System.out.print("\t");
                        break;
                    case ERROR:
                        System.out.print("!");
                        System.out.print("\t");
                        break;
                }

            }
            System.out.println("");
        }
        workbook.close();

    }

    public Row getRow(String pathname,Integer rowNum) throws Exception {
        File file = new File(pathname);

        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.print("File "+pathname +"not found");
            e.printStackTrace();
        }
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(rowNum);
        return headerRow;
    }
}
