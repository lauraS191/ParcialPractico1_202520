package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.SistemaSolarRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SistemaSolarService {

    @Autowired
    private SistemaSolarRepository sistemaSolarRepository;

    @Transactional
    public SistemaSolar createSistemaSolar(SistemaSolar sistemaSolar) throws IllegalOperationException {
        log.info("Creando sistema solar...");

        if (sistemaSolar.getNombre() == null || sistemaSolar.getNombre().length() >= 31) {
            throw new IllegalOperationException("El nombre del sistema solar se extiende a lo establecido");
        }

        if (sistemaSolar.getRatioFuerzaMinimo() == null || sistemaSolar.getRatioFuerzaMinimo() < 0.2 || sistemaSolar.getRatioFuerzaMinimo() > 0.6) {
            throw new IllegalOperationException("El ratio debe estar en el rango establecido ( 0,2 y menor o igual a 0,6)");
        }

        if (sistemaSolar.getStormtroopersAsignados() == null || sistemaSolar.getStormtroopersAsignados() <= 1000) {
            throw new IllegalOperationException("El número de Stormtropers debe ser mayor a 1000");
        }

        return sistemaSolarRepository.save(sistemaSolar);
    }
}

