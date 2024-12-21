package tn.esprit.tpfoyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BlocServiceImplTest {
    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test de la méthode removeBloc
    @Test
    void testRemoveBlocSuccess() {
        Long blocId = 1L;
        Bloc bloc = new Bloc(blocId, "Bloc A", 100, null, null);

        // Simuler la méthode findById
        when(blocRepository.findById(blocId)).thenReturn(Optional.of(bloc));

        // Appel de la méthode removeBloc
        blocService.removeBloc(blocId);

        // Vérifier que la méthode deleteById a été appelée une seule fois
        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    void testRemoveBlocNotFound() {
        Long blocId = 1L;

        // Simuler l'absence du bloc
        when(blocRepository.findById(blocId)).thenReturn(Optional.empty());

        // Vérifier qu'une exception est levée
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> blocService.removeBloc(blocId)
        );

        assertEquals("Bloc avec l'ID " + blocId + " non trouvé", thrown.getMessage());
    }

    // Test de la méthode retrieveBloc
    @Test
    void testRetrieveBloc() {
        Long blocId = 1L;
        Bloc bloc = new Bloc(blocId, "Bloc A", 100, null, null);

        // Simuler la récupération du bloc
        when(blocRepository.findById(blocId)).thenReturn(Optional.of(bloc));

        // Appel de la méthode
        Bloc retrievedBloc = blocService.retrieveBloc(blocId);

        // Vérification du résultat
        assertNotNull(retrievedBloc);
        assertEquals(blocId, retrievedBloc.getIdBloc());
    }

    @Test
    void testRetrieveBlocNotFound() {
        Long blocId = 1L;

        // Simuler l'absence du bloc
        when(blocRepository.findById(blocId)).thenReturn(Optional.empty());

        // Vérifier qu'une exception est levée
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> blocService.retrieveBloc(blocId)
        );

        assertEquals("Bloc avec l'ID " + blocId + " non trouvé", thrown.getMessage());
    }

    // Test de la méthode retrieveBlocsSelonCapacite
    @Test
    void testRetrieveBlocsSelonCapacite() {
        Bloc bloc1 = new Bloc(1L, "Bloc A", 100, null, null);
        Bloc bloc2 = new Bloc(2L, "Bloc B", 200, null, null);

        // Simuler la récupération des blocs
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Appel de la méthode
        List<Bloc> filteredBlocs = blocService.retrieveBlocsSelonCapacite(150);

        // Vérification du résultat
        assertEquals(1, filteredBlocs.size());
        assertEquals(bloc2, filteredBlocs.get(0));
    }

    // Test de la méthode addBloc
    @Test
    void testAddBloc() {
        Bloc bloc = new Bloc(1L, "Bloc A", 100, null, null);

        // Simuler la sauvegarde du bloc
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Appel de la méthode addBloc
        Bloc addedBloc = blocService.addBloc(bloc);

        // Vérification du résultat
        assertNotNull(addedBloc);
        assertEquals("Bloc A", addedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testAddBlocNull() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> blocService.addBloc(null)
        );

        assertEquals("Le bloc à ajouter ne doit pas être null", thrown.getMessage());
    }

    // Test de la méthode modifyBloc
    @Test
    void testModifyBloc() {
        Bloc bloc = new Bloc(1L, "Bloc A", 100, null, null);

        // Simuler la modification du bloc
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Appel de la méthode modifyBloc
        Bloc modifiedBloc = blocService.modifyBloc(bloc);

        // Vérification du résultat
        assertNotNull(modifiedBloc);
        assertEquals("Bloc A", modifiedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testModifyBlocNull() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> blocService.modifyBloc(null)
        );

        assertEquals("Le bloc à modifier ne doit pas être null", thrown.getMessage());
    }

    // Test de la méthode logAllBlocs


    // Test de la méthode trouverBlocsSansFoyer
    @Test
    void testTrouverBlocsSansFoyer() {
        Bloc bloc1 = new Bloc(1L, "Bloc A", 100, null, null);

        // Simuler la récupération des blocs sans foyer
        when(blocRepository.findByFoyerIsNull()).thenReturn(List.of(bloc1));

        // Appel de la méthode
        List<Bloc> blocs = blocService.trouverBlocsSansFoyer();

        // Vérification du résultat
        assertEquals(1, blocs.size());
        assertEquals(bloc1, blocs.get(0));
    }

    // Test de la méthode trouverBlocsParNomEtCap
    @Test
    void testTrouverBlocsParNomEtCap() {
        Bloc bloc1 = new Bloc(1L, "Bloc A", 100, null, null);

        // Simuler la récupération des blocs
        when(blocRepository.findByNomBlocAndCapaciteBloc("Bloc A", 100)).thenReturn(List.of(bloc1));

        // Appel de la méthode
        List<Bloc> blocs = blocService.trouverBlocsParNomEtCap("Bloc A", 100);

        // Vérification du résultat
        assertEquals(1, blocs.size());
        assertEquals(bloc1, blocs.get(0));
    }
}
