package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Universite;

import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class UniversiteServiceImplTest {


    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllUniversites() {
        // Arrange
        Universite universite1 = new Universite(1L, "Universite A", "Address A", null);
        Universite universite2 = new Universite(2L, "Universite B", "Address B", null);
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite1, universite2));

        // Act
        List<Universite> universites = universiteService.retrieveAllUniversites();

        // Assert
        assertEquals(2, universites.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveUniversite() {
        // Arrange
        Universite universite = new Universite(1L, "Universite A", "Address A", null);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversite(1L);

        // Assert
        assertEquals(1L, result.getIdUniversite());
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddUniversite() {
        // Arrange
        Universite universite = new Universite(1L, "Universite A", "Address A", null);
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.addUniversite(universite);

        // Assert
        assertEquals("Universite A", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testModifyUniversite() {
        // Arrange
        Universite universite = new Universite(1L, "Universite A", "Address A", null);
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.modifyUniversite(universite);

        // Assert
        assertEquals("Universite A", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testRemoveUniversite() {
        // Act
        universiteService.removeUniversite(1L);

        // Assert
        verify(universiteRepository, times(1)).deleteById(1L);
    }
}
