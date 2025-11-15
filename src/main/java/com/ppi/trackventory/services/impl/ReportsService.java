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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Stock;
import com.ppi.trackventory.models.TransactionDetails;
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
    
    @Autowired
    private TransactionDetailsService transactionDetailsService;

    public byte[] generateReportTransactions(List<Transactions> transactions) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Reporte Transacciones");

            // 👉 Encabezados de la transacción
            Row header = sheet.createRow(0);
            String[] headers = {
                    "ID", "Fecha", "Comprador", "Vendedor",
                    "Tipo de Transacción", "Origen", "Estado",
                    "Producto", "Color", "Tienda", "Cantidad", "Descuento %", "Total Detalle"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            for (Transactions t : transactions) {

                // 📌 Obtener los detalles de la transacción
                List<TransactionDetails> details = transactionDetailsService.getDetailsByTransaction(t);

                if (details == null || details.isEmpty()) {
                    // Si no hay detalles, igual se imprime la fila base
                    Row row = sheet.createRow(rowIdx++);
                    fillTransactionColumns(row, t, sdf);

                } else {
                    // Si hay detalles, se imprime una fila por cada uno
                    for (TransactionDetails det : details) {
                        Row row = sheet.createRow(rowIdx++);
                        
                        // 🧾 columnas de la transacción
                        fillTransactionColumns(row, t, sdf);

                        // 🧾 columnas de detalle
                        row.createCell(7).setCellValue(det.getStock().getId().getVariation().getProduct().getName());
                        row.createCell(8).setCellValue(det.getStock().getId().getVariation().getColor().getName());
                        row.createCell(9).setCellValue(det.getStock().getId().getStore().getName());
                        row.createCell(10).setCellValue(det.getQuantity());
                        row.createCell(11).setCellValue(det.getDiscount_percentage());
                        row.createCell(12).setCellValue(det.getTotal() != null ? det.getTotal().doubleValue() : 0);
                    }
                }
            }

            // Ajustar ancho automático
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new IOException("Error generando reporte: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para no repetir código
    private void fillTransactionColumns(Row row, Transactions t, SimpleDateFormat sdf) {
        row.createCell(0).setCellValue(t.getId());
        row.createCell(1).setCellValue(t.getDate() != null ? sdf.format(t.getDate()) : "N/A");
        row.createCell(2).setCellValue(t.getBuyer() != null ? (t.getBuyer().getName() + " " + t.getBuyer().getLastName()) : "N/A");
        row.createCell(3).setCellValue(t.getSeller() != null ? (t.getSeller().getName() + " " + t.getSeller().getLastName()) : "N/A");
        row.createCell(4).setCellValue(t.getTransactionType() != null ? t.getTransactionType().getName() : "N/A");
        row.createCell(5).setCellValue(t.getTransactionOrigin() != null ? t.getTransactionOrigin().getName() : "N/A");
        row.createCell(6).setCellValue(t.getEnabled() != null && t.getEnabled() ? "Activa" : "Inactiva");
    }

}

