package co.edu.uniandes.dse.ParcialPractico1_202520;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.services.PlanetaService;
import jakarta.persistence.EntityManager;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
class PlanetaServiceTest {

    @Autowired
    private PlanetaService planetaService;

    @Autowired
    private EntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void clearData() {
        entityManager.createQuery("DELETE FROM PlanetaEntity").executeUpdate();
    }

    @Test
    void testCreatePlanetaExitoso() throws IllegalOperationException {
        PlanetaEntity entity = factory.manufacturePojo(PlanetaEntity.class);

        entity.setNombre("Tatooine I");
        entity.setPoblacion(5000L);

        PlanetaEntity result = planetaService.createPlaneta(entity);
        assertNotNull(result);
        assertEquals(entity.getNombre(), result.getNombre());
    }

    @Test
    void testCreatePlanetaNombreInvalido() {
        assertThrows(IllegalOperationException.class, () -> {

            PlanetaEntity entity = factory.manufacturePojo(PlanetaEntity.class);
            entity.setNombre("PlanetaX");
            entity.setPoblacion(100L);
            planetaService.createPlaneta(entity);
        });
    }
}

