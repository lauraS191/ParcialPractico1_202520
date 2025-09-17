package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.PlanetaRepository;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.SistemaSolarRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SistemaSolarPlanetaService {

    @Autowired
    private SistemaSolarRepository sistemaSolarRepository;

    private PlanetaRepository planetaRepository;

    @Transactional
    public PlanetaEntity asociarPlanetaASistemaSolar(Long sistemaId, Long planetaId)
            throws EntityNotFoundException, IllegalOperationException {

        SistemaSolar sistema = sistemaSolarRepository.findById(sistemaId)
                .orElseThrow(() -> new EntityNotFoundException("Sistema solar no se encuantra"));

        PlanetaEntity planeta = planetaRepository.findById(planetaId)
                .orElseThrow(() -> new EntityNotFoundException("Planeta no se encuentra"));

        long poblacionTotal = planeta.getPoblacion();
        List<PlanetaEntity> planetasExistentes = sistema.getPlanetas();
        for (PlanetaEntity p : planetasExistentes) {
            poblacionTotal += p.getPoblacion();
        }

        double ratioActual = (double) poblacionTotal / sistema.getStormtroopersAsignados();

        if (ratioActual < sistema.getRatioFuerzaMinimo()) {
            throw new IllegalOperationException("El ratio actual no puede ser menor al ratio mínimo del sistema solar");
        }

        sistema.getPlanetas().add(planeta);
        planeta.setSistemaSolar(sistema);

        sistemaSolarRepository.save(sistema);
        return planetaRepository.save(planeta);

    }
}

