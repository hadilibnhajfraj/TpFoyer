package tn.esprit.tpfoyer.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j // Simple Logging Façade for Java
public class BlocServiceImpl implements IBlocService {

    private final BlocRepository blocRepository;

    @Scheduled(fixedRate = 30000) // Executes every 30 seconds
    public void logAllBlocs() {
        List<Bloc> listB = blocRepository.findAll();
        log.info("Taille totale des blocs : {}", listB.size());
        for (Bloc b : listB) {
            log.info("Bloc : {}", b);
        }
    }

    @Override
    public List<Bloc> retrieveAllBlocs() {
        List<Bloc> listB = blocRepository.findAll();
        log.info("Taille totale des blocs : {}", listB.size());
        return listB;
    }



    @Transactional
    public List<Bloc> retrieveBlocsSelonCapacite(long capacite) {
        List<Bloc> listB = blocRepository.findAll();
        List<Bloc> listBselonC = new ArrayList<>();
        for (Bloc b : listB) {
            if (b.getCapaciteBloc() >= capacite) {
                listBselonC.add(b);
            }
        }
        return listBselonC;
    }

    @Transactional
    public Bloc retrieveBloc(Long blocId) {
        if (blocId == null) {
            throw new IllegalArgumentException("L'ID du bloc ne doit pas être null");
        }
        return blocRepository.findById(blocId)
                .orElseThrow(() -> new IllegalArgumentException("Bloc avec l'ID " + blocId + " non trouvé"));
    }

    public Bloc addBloc(Bloc bloc) {
        if (bloc == null) {
            throw new IllegalArgumentException("Le bloc à ajouter ne doit pas être null");
        }
        return blocRepository.save(bloc);
    }

    public Bloc modifyBloc(Bloc bloc) {
        if (bloc == null) {
            throw new IllegalArgumentException("Le bloc à modifier ne doit pas être null");
        }
        return blocRepository.save(bloc);
    }

    public void removeBloc(Long blocId) {
        Optional<Bloc> blocOpt = blocRepository.findById(blocId);
        log.info("Bloc found: {}", blocOpt.isPresent()); // Use logger instead of System.out.println
        if (blocOpt.isPresent()) {
            blocRepository.deleteById(blocId);
            log.info("Bloc with ID {} has been removed successfully.", blocId);
        } else {
            throw new IllegalArgumentException("Bloc avec l'ID " + blocId + " non trouvé");
        }
    }



    public List<Bloc> trouverBlocsSansFoyer() {
        return blocRepository.findAllByFoyerIsNull();
    }

    public List<Bloc> trouverBlocsParNomEtCap(String nomBloc, long capacite) {
        if (nomBloc == null || nomBloc.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du bloc ne doit pas être null ou vide");
        }
        return blocRepository.findAllByNomBlocAndCapaciteBloc(nomBloc, capacite);
    }
}
