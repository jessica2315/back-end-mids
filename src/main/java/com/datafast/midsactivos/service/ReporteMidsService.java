package com.datafast.midsactivos.service;

    import java.io.FileOutputStream;
	import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
	import java.util.Arrays;
import java.util.Date;
import java.util.List;
	import java.util.Map;

import org.apache.poi.ss.usermodel.*;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	import com.datafast.midsactivos.repository.RepositoryMidsActivos;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

	
	@Service
	
	public class ReporteMidsService {
		@Autowired
		RepositoryMidsActivos repositoryMidsActivos;

		public List<Map<String, Object>> consultaMidActivosActualizados() {

			List<Map<String, Object>> registros = null;

			String usuario = "JMUNOZ";

			registros = repositoryMidsActivos.consultaCaja(usuario);

			return registros != null ? registros : new ArrayList<>();
		}

		public void consultaMidActivosActualizados(String nombreArchivo) throws IOException {
			List<String> excludedKeys = Arrays.asList("FECHA_ACTUAL", "USUARIO", "ACTIVO");
			List<Map<String, Object>> registros = repositoryMidsActivos.consultaCaja("JMUNOZ");
			//generaExcelXlsx(nombreArchivo,"CAJA", registros,excludedKeys);
		}

		
		public void consultaMidsPos(String nombreArchivo) throws IOException {
			List<String> excludedKeys = Arrays.asList("FECHA_ACTUAL", "USUARIO", "ACTIVO");
			List<Map<String, Object>> registros = repositoryMidsActivos.consultaPos("JMUNOZ");
			//generaExcelXlsx(nombreArchivo,"POS", registros,excludedKeys);
		}

		
		public void consultaMidActivos(String nombreArchivo) throws IOException {
			List<String> excludedKeys = Arrays.asList("FECHA_ACTUAL", "USUARIO", "ACTIVO");
			// Simulación de la obtención de registros
			List<Map<String, Object>> registrosCaja = repositoryMidsActivos.consultaCaja("JMUNOZ"); // "caja"
			List<Map<String, Object>> registrosPost = repositoryMidsActivos.consultaPos("JMUNOZ");//pos

			SXSSFWorkbook workbook = new SXSSFWorkbook();
			generaHoja(workbook, "CAJA", registrosCaja, excludedKeys);
			generaHoja(workbook, "POS", registrosPost, excludedKeys);

			// Escribir a archivo
			try (FileOutputStream outputStream = new FileOutputStream(nombreArchivo + ".xlsx")) {
				workbook.write(outputStream);
			} finally {
				workbook.dispose(); // Liberar recursos de SXSSFWorkbook
			}
		}
		
		
		
		public void generaHoja(SXSSFWorkbook workbook, String nombreHoja, List<Map<String, Object>> registros,
				List<String> excludedKeys) {
			Sheet sheet = workbook.createSheet(nombreHoja);

			// Crear el estilo para el encabezado (opcional, similar a lo mostrado
			// anteriormente)
			// CellStyle headerStyle = workbook.createCellStyle();
			// Configurar headerStyle...

			// Encabezados dinámicos y creación de filas de datos
			Row headerRow = sheet.createRow(0);
			if (!registros.isEmpty()) {
				int cellIndex = 0;
				Map<String, Object> firstRecord = registros.get(0);
				for (String key : firstRecord.keySet()) {
					if (!excludedKeys.contains(key)) {
						Cell cell = headerRow.createCell(cellIndex++);
						cell.setCellValue(key);
						// cell.setCellStyle(headerStyle);
					}
				}

				int rowIndex = 1;
				for (Map<String, Object> registro : registros) {
		            Row row = sheet.createRow(rowIndex++);
		            cellIndex = 0;
		            for (Map.Entry<String, Object> entry : registro.entrySet()) {
		                if (!excludedKeys.contains(entry.getKey())) {
		                    Cell cell = row.createCell(cellIndex++);
		                    Object value = entry.getValue();
		                    if (value != null) {
		                        if (value instanceof Date) {
		                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		                            cell.setCellValue(dateFormat.format((Date) value));
		                        } else if (value instanceof Double) {
		                            cell.setCellValue((Double) value);
		                        } else if (value instanceof Boolean) {
		                            cell.setCellValue((Boolean) value);
		                        } else {
		                            cell.setCellValue(value.toString());
		                        }
		                    } else {
		                        cell.setCellValue("");
		                    }
		                }
		            }
		        }

				// Ajustar el tamaño de las columnas aquí si se desea
				for (int i = 0; i < headerRow.getLastCellNum(); i++) {
					sheet.autoSizeColumn(i);
				}

			}

		}

}
