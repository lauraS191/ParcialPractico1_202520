package co.edu.uniandes.dse.ParcialPractico1_202520;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.services.SistemaSolarService;
import jakarta.persistence.EntityManager;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
class SistemaSolarServiceTest {

    @Autowired
    private SistemaSolarService sistemaSolarService;

    @Autowired
    private EntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void clearData() {
        entityManager.createQuery("DELETE FROM SistemaSolar").executeUpdate();
    }

    @Test
    void testCreateSistemaSolarExitoso() throws IllegalOperationException {
        SistemaSolar entity = factory.manufacturePojo(SistemaSolar.class);

        entity.setNombre("Andrómeda");
        entity.setRatioFuerzaMinimo(0.3);
        entity.setStormtroopersAsignados(2000L);

        SistemaSolar result = sistemaSolarService.createSistemaSolar(entity);
        assertNotNull(result);
        assertEquals(entity.getNombre(), result.getNombre());
    }

    @Test
    void testCreateSistemaSolarNombreInvalido() {
        assertThrows(IllegalOperationException.class, () -> {
            SistemaSolar entity = factory.manufacturePojo(SistemaSolar.class);

            entity.setNombre("EsteNombreEsExcesivamenteLargoYDebeFallarPorqueTieneMasDeTreintaCaracteres");
            entity.setRatioFuerzaMinimo(0.3);
            entity.setStormtroopersAsignados(2000L);
            sistemaSolarService.createSistemaSolar(entity);
        });

    }
}
