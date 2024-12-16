package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceImplTest {

    private static final String BLOC_A = "Bloc A";
    private static final String BLOC_B = "Bloc B";

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

        // Assert
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, BLOC_A, 100, null, null);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc result = blocService.retrieveBloc(1L);

        // Assert
        assertNotNull(result);
        assertEquals(BLOC_A, result.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, BLOC_A, 100, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.addBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals(BLOC_A, result.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testModifyBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, "Bloc Updated", 150, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.modifyBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals(150, result.getCapaciteBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        // Act
        blocService.removeBloc(1L);

        // Assert
        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRetrieveBlocsSelonCapacite() {
        // Arrange
        Bloc bloc1 = new Bloc(1L, BLOC_A, 100, null, null);
        Bloc bloc2 = new Bloc(2L, BLOC_B, 200, null, null);
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(150);

        // Assert
        assertEquals(1, result.size());
        assertEquals(BLOC_B, result.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAll();
    }
}
