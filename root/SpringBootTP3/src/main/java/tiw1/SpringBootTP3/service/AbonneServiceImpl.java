package tiw1.SpringBootTP3.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tiw1.SpringBootTP3.model.Abonne;
import tiw1.SpringBootTP3.repository.AbonneRepository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class AbonneServiceImpl implements AbonneService {

    private final AbonneRepository abonneRepository;
    private final String ABONNES_JSON = "abonnes.json";
    private Path path = Paths.get(ABONNES_JSON);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AbonneServiceImpl(AbonneRepository abonneRepository) {
        this.abonneRepository = abonneRepository;
    }

    @Override
    public Optional<Abonne> get(long id) {
        return abonneRepository.findById(id);
    }

    @Override
    public List<Abonne> getAll() {
        return abonneRepository.findAll();
    }

    @Override
    public void add() throws IOException {
        read();
        Abonne abonne = new Abonne();
        abonneRepository.save(abonne);
        persist();
    }

    @Override
    public void delete(long id) throws IOException {
        read();
        if(abonneRepository.findById(id).isPresent()) {
            abonneRepository.deleteById(id);
            persist();
        }
    }

    public void persist() throws IOException {
        String temp = mapper.writeValueAsString(abonneRepository.findAll());
        // TODO : pb d'écriture en UTF-8
        FileWriter fileWriter = new FileWriter("src/main/resources/json/abonnes.json");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(temp);
        printWriter.close();
    }

    public void read() throws IOException {
        // read abonnes.json and write to db
        TypeReference<List<Abonne>> typeReference = new TypeReference<List<Abonne>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/abonnes.json");
        try {
            List<Abonne> abonnes = mapper.readValue(inputStream,typeReference);
            abonneRepository.deleteAll();
            abonneRepository.saveAll(abonnes);
            System.out.println("Abonnés récupérés du json");
        } catch (IOException e){
            System.out.println("Impossible de récupérer les abonnés du json " + e.getMessage());
        }
    }
}
