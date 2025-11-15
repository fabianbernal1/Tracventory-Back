package com.ppi.trackventory.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Stock;
import com.ppi.trackventory.models.Transactions;

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
    
 // 📌 NUEVO MÉTODO PARA REPORTE DE TRANSACCIONES
    public byte[] generateReportTransactions(List<Transactions> transactions) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Reporte Transacciones");

            // 👉 Encabezados de la tabla
            Row header = sheet.createRow(0);
            String[] headers = {
                "ID", "Fecha", "Comprador", "Vendedor",
                "Tipo de Transacción", "Origen", "Estado"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // 👉 Rellenar filas
            int rowIdx = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            for (Transactions t : transactions) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(t.getId());
                row.createCell(1).setCellValue(t.getDate() != null ? sdf.format(t.getDate()) : "N/A");
                row.createCell(2).setCellValue(
                        t.getBuyer() != null ? (t.getBuyer().getName() + " " + t.getBuyer().getLastName()) : "N/A"
                );
                row.createCell(3).setCellValue(
                        t.getSeller() != null ? (t.getSeller().getName() + " " + t.getSeller().getLastName()) : "N/A"
                );
                row.createCell(4).setCellValue(
                        t.getTransactionType() != null ? t.getTransactionType().getName() : "N/A"
                );
                row.createCell(5).setCellValue(
                        t.getTransactionOrigin() != null ? t.getTransactionOrigin().getName() : "N/A"
                );
                row.createCell(6).setCellValue(
                        t.getEnabled() != null && t.getEnabled() ? "Activa" : "Inactiva"
                );
            }

            // Ajustar ancho automático
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}

