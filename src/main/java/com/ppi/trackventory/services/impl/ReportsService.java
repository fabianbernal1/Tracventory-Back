package com.ppi.trackventory.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Stock;

@Service
public class ReportsService {

    public byte[] generateReportStock(List<Stock> data) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reporte");

            // Crear el encabezado
            Row headerRow = sheet.createRow(0);
            String[] columnHeaders = {"Cantidad", "Tienda", "Referencia", "Color"}; 
            for (int i = 0; i < columnHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeaders[i]);
            }

            // Llenar los datos
            int rowNum = 1;
            for (Stock entity : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getQuantity());
                row.createCell(1).setCellValue(entity.getId().getStore().getName());
                row.createCell(2).setCellValue(entity.getId().getVariation().getProduct().getProductId());
                row.createCell(3).setCellValue(entity.getId().getVariation().getColor().getName());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}

