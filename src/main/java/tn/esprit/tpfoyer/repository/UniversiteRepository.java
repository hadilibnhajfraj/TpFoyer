package tn.esprit.tpfoyer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Universite;


import java.util.List;
@Repository
public interface UniversiteRepository extends JpaRepository<Universite, Long>
{

    // Custom query method to find universities by address
    List<Universite> findAllByAdresse(String adresse);

    // Custom query method to find a university by name
    Universite findByNomUniversite(String nomUniversite);


}
