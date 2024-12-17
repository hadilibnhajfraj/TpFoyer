package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ChambreServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(ChambreServiceImplTest.class);

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

        // Log the result for manual verification
        logger.info("Retrieved chambres: {}", chambres);

        // Verify repository interaction
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveChambre() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Act
        Chambre result = chambreService.retrieveChambre(1L);

        // Log the result for manual verification
        logger.info("Retrieved chambre: {}", result);

        // Verify repository interaction
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddChambre() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.addChambre(chambre);

        // Log the result for manual verification
        logger.info("Added chambre: {}", result);

        // Verify repository interaction
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    public void testRemoveChambre() {
        // Act
        chambreService.removeChambre(1L);

        // Verify repository interaction
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRecupererChambresSelonTyp() {
        // Arrange
        Chambre chambre1 = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        Chambre chambre2 = new Chambre(2L, 102L, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(chambre1, chambre2));

        // Act
        List<Chambre> chambres = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        // Log the result for manual verification
        logger.info("Retrieved chambres based on type: {}", chambres);

        // Verify repository interaction
        verify(chambreRepository, times(1)).findAllByTypeC(TypeChambre.SIMPLE);
    }

    @Test
    public void testTrouverChambreSelonEtudiant() {
        // Arrange
        Chambre chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.trouverChselonEt(123456L)).thenReturn(chambre);

        // Act
        Chambre result = chambreService.trouverChambreSelonEtudiant(123456L);

        // Log the result for manual verification
        logger.info("Retrieved chambre for student: {}", result);

        // Verify repository interaction
        verify(chambreRepository, times(1)).trouverChselonEt(123456L);
    }
}
