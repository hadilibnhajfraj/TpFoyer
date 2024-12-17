package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class BlocServiceImplTest {

    private static final String BLOC_A = "Bloc A";
    private static final String BLOC_B = "Bloc B";

    private static final Logger logger = LoggerFactory.getLogger(BlocServiceImplTest.class);

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllBlocs() {
        // Arrange
        Bloc bloc1 = new Bloc(1L, BLOC_A, 100, null, null);
        Bloc bloc2 = new Bloc(2L, BLOC_B, 200, null, null);
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> result = blocService.retrieveAllBlocs();

        // Log the result for manual verification
        logger.info("Retrieved blocs: {}", result);

        // Verify repository interaction
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, BLOC_A, 100, null, null);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc result = blocService.retrieveBloc(1L);

        // Log the result for manual verification
        logger.info("Retrieved bloc: {}", result);

        // Verify repository interaction
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, BLOC_A, 100, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.addBloc(bloc);

        // Log the result for manual verification
        logger.info("Added bloc: {}", result);

        // Verify repository interaction
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testModifyBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, "Bloc Updated", 150, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.modifyBloc(bloc);

        // Log the result for manual verification
        logger.info("Modified bloc: {}", result);

        // Verify repository interaction
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        // Arrange: Mock the repository to return an Optional of the Bloc to be deleted
        Bloc bloc = new Bloc(1L, BLOC_A, 100, null, null);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc)); // Mock findById to return the Bloc

        // Act: Call the remove method
        blocService.removeBloc(1L);

        // Verify repository interaction
        verify(blocRepository, times(1)).deleteById(1L);

        // Log output for verification
        logger.info("Removed Bloc with ID 1");
    }



    @Test
    void testRetrieveBlocsSelonCapacite() {
        // Arrange
        Bloc bloc1 = new Bloc(1L, BLOC_A, 100, null, null);
        Bloc bloc2 = new Bloc(2L, BLOC_B, 200, null, null);
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(150);

        // Log the result for manual verification
        logger.info("Retrieved blocs based on capacity: {}", result);

        // Verify repository interaction
        verify(blocRepository, times(1)).findAll();
    }
}
