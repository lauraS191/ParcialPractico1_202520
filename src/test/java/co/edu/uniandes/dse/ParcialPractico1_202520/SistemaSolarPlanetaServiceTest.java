package co.edu.uniandes.dse.ParcialPractico1_202520;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.services.SistemaSolarPlanetaService;
import jakarta.persistence.EntityManager;


@DataJpaTest
@Transactional
class SistemaSolarPlanetaServiceTest {

    @Autowired
    private SistemaSolarPlanetaService sistemaSolarPlanetaService;

    @Autowired
    private EntityManager entityManager;
    private SistemaSolar sistemaSolar;
    private PlanetaEntity planeta;

    @BeforeEach
    void setUp() {
        entityManager.createQuery("DELETE FROM PlanetaEntity").executeUpdate();
        entityManager.createQuery("DELETE FROM SistemaSolar").executeUpdate();

        sistemaSolar = new SistemaSolar();
        sistemaSolar.setNombre("Alpha");
        sistemaSolar.setRatioFuerzaMinimo(0.3);
        sistemaSolar.setStormtroopersAsignados(5000L);
        entityManager.persist(sistemaSolar);

        planeta = new PlanetaEntity();
        planeta.setNombre("Alderaan I");
        planeta.setPoblacion(2000L);
        entityManager.persist(planeta);
    }

    @Test
    void testAsociacionExitosa() throws EntityNotFoundException, IllegalOperationException {
        PlanetaEntity result = sistemaSolarPlanetaService.asociarPlanetaASistemaSolar(sistemaSolar.getId(), planeta.getId());
        assertNotNull(result.getSistemaSolar());
        assertEquals(sistemaSolar.getId(), result.getSistemaSolar().getId());
    }

    
}

