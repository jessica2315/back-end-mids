package com.datafast.midsactivos.controller;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.datafast.midsactivos.service.ReporteMidsService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ReporteMids")

public class ReporteMidsActivosController {
	
	@Autowired
	ReporteMidsService reporteMidsService;
 
    @GetMapping("/ConsultaMidActivos")
    public ResponseEntity<?> consultaMidActivosActualizados() {
    	ThreadContext.put("sid", UUID.randomUUID().toString());
        String nombreArchivo = "Reporte Mids Actualizado-" + new SimpleDateFormat("dd-MM-yyyy").format(new Date());
      
        try {
            this.reporteMidsService.consultaMidActivos(nombreArchivo);
                      
            String mensajeRespuesta = String.format("El reporte '%s.xlsx' ha sido generado exitosamente.", nombreArchivo);
            // Puedes retornar el nombre del archivo 
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", mensajeRespuesta);
            respuesta.put("nombreArchivo", nombreArchivo + ".xlsx");
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error al generar el reporte.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
