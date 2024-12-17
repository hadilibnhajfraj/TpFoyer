package tn.esprit.tpfoyer.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ChambreServiceImpl implements IChambreService {

    ChambreRepository chambreRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        log.info("In Method retrieveAllChambres : ");
        log.info("Out of retrieveAllChambres : ");
        return chambreRepository.findAll();
    }

    public Chambre retrieveChambre(Long chambreId) {
        Chambre c = chambreRepository.findById(chambreId) .orElseThrow(() -> new EntityNotFoundException("Bloc with ID " + chambreId + " not found"));
        return c;
    }

    public Chambre addChambre(Chambre c) {
        Chambre chambre = chambreRepository.save(c);
        return chambre;
    }

    public Chambre modifyChambre(Chambre c) {
        Chambre chambre = chambreRepository.save(c);
        return c;
    }

    public void removeChambre(Long chambreId) {
        chambreRepository.deleteById(chambreId);
    }







    public List<Chambre> recupererChambresSelonTyp(TypeChambre tc)
    {
        return chambreRepository.findAllByTypeC(tc);
    }






















    public Chambre trouverchambreSelonEtudiant(long cin) {
       //

        return chambreRepository.trouverChselonEt(cin);
    }
}
