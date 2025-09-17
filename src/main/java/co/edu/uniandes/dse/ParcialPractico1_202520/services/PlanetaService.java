package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.PlanetaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanetaService {

    @Autowired
    private PlanetaRepository planetaRepository;

    @Transactional
    public PlanetaEntity createPlaneta(PlanetaEntity planeta) throws IllegalOperationException {
        log.info("Creando planeta");

        if (planeta.getNombre() == null ||
           !(planeta.getNombre().endsWith(" I") || planeta.getNombre().endsWith(" II") || planeta.getNombre().endsWith(" III"))) {
            throw new IllegalOperationException("El nombre del planeta debe terminar con I, II o III");
        }

        if (planeta.getPoblacion() == null || planeta.getPoblacion() <= 0) {
            throw new IllegalOperationException("La población debe ser mayor que 0");
        }

        return planetaRepository.save(planeta);
    }
}

