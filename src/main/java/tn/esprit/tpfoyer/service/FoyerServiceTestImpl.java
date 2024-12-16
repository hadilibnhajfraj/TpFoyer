package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoyerServiceTestImpl {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisation d'un objet Foyer fictif
        Universite universite = new Universite();
        universite.setIdUniversite(1L);

        Set<Bloc> blocs = new HashSet<>();
        blocs.add(new Bloc());

        foyer = new Foyer(1L, "Foyer Test", 100, universite, blocs);
    }

    @Test
    void testGetFoyerById() {
        // Configurer le comportement du mock
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Appeler le service
        Foyer result = foyerService.retrieveFoyer(1L);

        // Vérifier les résultats
        assertNotNull(result);
        assertEquals("Foyer Test", result.getNomFoyer());
        assertEquals(100, result.getCapaciteFoyer());

        // Vérifier les interactions avec le mock
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveFoyer() {
        // Configurer le comportement du mock
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        // Appeler le service
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Vérifier les résultats
        assertNotNull(savedFoyer);
        assertEquals(1L, savedFoyer.getIdFoyer());
        assertEquals("Foyer Test", savedFoyer.getNomFoyer());

        // Vérifier les interactions avec le mock
        verify(foyerRepository, times(1)).save(foyer);
    }
}