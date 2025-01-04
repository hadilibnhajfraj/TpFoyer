package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

public class FoyerServiceTestImpl {

    private static final Logger logger = LoggerFactory.getLogger(FoyerServiceTestImpl.class);

    private static final String FOYER_TEST = "Foyer Test";  // Constante utilisée dans le code
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

        // Utilisation de la constante FOYER_TEST pour nommer le foyer
        foyer = new Foyer(1L, FOYER_TEST, 100, universite, blocs);
    }

    @Test
    void testGetFoyerById() {
        // Configurer le comportement du mock
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Appeler le service
        Foyer result = foyerService.retrieveFoyer(1L);

        // Vérifier les résultats avec des logs au lieu de System.out.println
        if (result != null) {
            logger.info("Foyer name: {}", result.getNomFoyer());
            logger.info("Foyer capacity: {}", result.getCapaciteFoyer());
            assert result.getNomFoyer().equals(FOYER_TEST) : "Foyer name doesn't match!";
            assert result.getCapaciteFoyer() == 100 : "Foyer capacity doesn't match!";
        } else {
            logger.warn("Foyer not found!");
        }

        // Vérifier les interactions avec le mock
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveFoyer() {
        // Configurer le comportement du mock
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        // Appeler le service
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Vérification manuelle avec des logs
        if (savedFoyer != null) {
            logger.info("Saved Foyer ID: {}", savedFoyer.getIdFoyer());
            logger.info("Saved Foyer name: {}", savedFoyer.getNomFoyer());
        } else {
            logger.error("Failed to save the Foyer!");
        }

        // Vérifier les interactions avec le mock
        verify(foyerRepository, times(1)).save(foyer);
    }
}
