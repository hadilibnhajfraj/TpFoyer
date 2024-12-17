package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantServiceImpl; // Correction : utilisation d'EtudiantServiceImpl

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisation d'un objet Etudiant pour les tests
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Doe");
        etudiant.setPrenomEtudiant("John");
        etudiant.setCinEtudiant(12345678L);
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Arrange
        Etudiant etudiant2 = new Etudiant(2L, "Smith", "Jane", 87654321L, null, null);
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant, etudiant2));

        // Act
        List<Etudiant> result = etudiantServiceImpl.retrieveAllEtudiants();

        // Assert
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantServiceImpl.retrieveEtudiant(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Doe", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testAddEtudiant() {
        // Arrange
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantServiceImpl.addEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testModifyEtudiant() {
        // Arrange
        etudiant.setNomEtudiant("UpdatedName");
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantServiceImpl.modifyEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("UpdatedName", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testRemoveEtudiant() {
        // Act
        etudiantServiceImpl.removeEtudiant(1L);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRecupererEtudiantParCin() {
        // Arrange
        long cin = 12345678L;
        when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantServiceImpl.recupererEtudiantParCin(cin);

        // Assert
        assertNotNull(result);
        assertEquals(cin, result.getCinEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
    }
}