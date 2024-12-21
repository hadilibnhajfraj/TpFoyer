package tn.esprit.tpfoyer;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllChambres() {
        // Arrange
        Chambre chambre1 = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        Chambre chambre2 = new Chambre(2L, 102L, TypeChambre.DOUBLE, null, null);
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre1, chambre2));

        // Act
        List<Chambre> chambres = chambreService.retrieveAllChambres();

        // Assert
        assertNotNull(chambres);
        assertEquals(2, chambres.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveChambre() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Act
        Chambre result = chambreService.retrieveChambre(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveChambreNotFound() {
        // Arrange
        when(chambreRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chambreService.retrieveChambre(1L));
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddChambre() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.addChambre(chambre);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    public void testRemoveChambre() {
        // Act
        chambreService.removeChambre(1L);

        // Assert
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRecupererChambresSelonTyp() {
        // Arrange
        Chambre chambre1 = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        Chambre chambre2 = new Chambre(2L, 102L, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(chambre1, chambre2));

        // Act
        List<Chambre> result = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TypeChambre.SIMPLE, result.get(0).getTypeC());
        verify(chambreRepository, times(1)).findAllByTypeC(TypeChambre.SIMPLE);
    }

    @Test
    public void testTrouverChambreSelonEtudiant() {
        // Arrange
        Chambre chambre = new Chambre(123456L, 101L, TypeChambre.SIMPLE, null, null); // Set idChambre to 123456L
        when(chambreRepository.trouverChselonEt(123456L)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.trouverChambreSelonEtudiant(123456L);

        // Assert
        assertNotNull(result);
        assertEquals(123456L, result.getIdChambre()); // Expect 123456L here
        verify(chambreRepository, times(1)).trouverChselonEt(123456L);
    }


    @Test
    public void testTrouverChambreSelonEtudiantNotFound() {
        // Arrange
        when(chambreRepository.trouverChselonEt(123456L)).thenReturn(null);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chambreService.trouverChambreSelonEtudiant(123456L));
        verify(chambreRepository, times(1)).trouverChselonEt(123456L);
    }



    @Test
    public void testModifyChambre() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.modifyChambre(chambre);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }
}
