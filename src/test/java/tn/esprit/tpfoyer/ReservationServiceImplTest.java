package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setIdReservation("1");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
    }

    @Test
    void retrieveAllReservations() {
        // Arrange
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        // Act
        List<Reservation> reservations = reservationService.retrieveAllReservations();

        // Assert
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void retrieveReservation() {
        // Arrange
        when(reservationRepository.findById("1")).thenReturn(Optional.of(reservation));

        // Act
        Reservation foundReservation = reservationService.retrieveReservation("1");

        // Assert
        assertNotNull(foundReservation);
        assertEquals("1", foundReservation.getIdReservation());
        verify(reservationRepository, times(1)).findById("1");
    }

    @Test
    void addReservation() {
        // Arrange
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation savedReservation = reservationService.addReservation(reservation);

        // Assert
        assertNotNull(savedReservation);
        assertEquals("1", savedReservation.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void modifyReservation() {
        // Arrange
        reservation.setEstValide(false);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation updatedReservation = reservationService.modifyReservation(reservation);

        // Assert
        assertNotNull(updatedReservation);
        assertFalse(updatedReservation.isEstValide());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void removeReservation() {
        // Act
        reservationService.removeReservation("1");

        // Assert
        verify(reservationRepository, times(1)).deleteById("1");
    }

    @Test
    void trouverResSelonDateEtStatus() {
        // Arrange
        Date testDate = new Date();
        boolean testStatus = true;
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(testDate, testStatus))
                .thenReturn(Arrays.asList(reservation));

        // Act
        List<Reservation> reservations = reservationService.trouverResSelonDateEtStatus(testDate, testStatus);

        // Assert
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        verify(reservationRepository, times(1))
                .findAllByAnneeUniversitaireBeforeAndEstValide(testDate, testStatus);
    }
}
