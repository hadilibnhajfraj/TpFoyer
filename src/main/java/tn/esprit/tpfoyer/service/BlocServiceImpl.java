package tn.esprit.tpfoyer.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
@Slf4j
public class BlocServiceImpl implements IBlocService {


    private final BlocRepository blocRepository;

    @Override
    public List<Bloc> retrieveAllBlocs() {
        List<Bloc> listB = blocRepository.findAll();
        log.info("Taille totale des blocs : {}", listB.size());
        return listB;
    }

    @Transactional
    public List<Bloc> retrieveBlocsSelonCapacite(long capacite) {
        return blocRepository.findAll().stream()
                .filter(b -> b.getCapaciteBloc() >= capacite)
                .toList();
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
        // Use blocId directly for retrieval and deletion
        Bloc bloc = blocRepository.findById(blocId)
                .orElseThrow(() -> new IllegalArgumentException("Bloc avec l'ID " + blocId + " non trouvé"));

        blocRepository.deleteById(bloc.getIdBloc());
        log.info("Bloc with ID {} has been removed successfully.", blocId);
    }


    public List<Bloc> trouverBlocsSansFoyer() {
        return blocRepository.findByFoyerIsNull();
    }


    public List<Bloc> trouverBlocsParNomEtCap(String nomBloc, long capaciteBloc) {
        return blocRepository.findByNomBlocAndCapaciteBloc(nomBloc, capaciteBloc);
    }

}
