package tiw1.SpringBootTP3.repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;
import tiw1.SpringBootTP3.model.Abonne;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class AbonneRepository {
    private static final Logger _log = LoggerFactory.getLogger(AbonneRepository.class);

    private ObjectMapper mapper = new ObjectMapper();
    private final String ABONNES_JSON = "abonnes.json";
    private Path path = Paths.get(ABONNES_JSON);


    private List<Abonne> abonnes = new ArrayList<>();

    public AbonneRepository() {
        try {
            read();
        } catch (IOException e) {
            _log.error("Didn't found abonnes.json", e);
        }
    }

    public Optional<Abonne> get(long id) {
        for (Abonne abonne : abonnes) {
            if (abonne.getId() == id) {
                return Optional.of(abonne);
            }
        }
        return Optional.empty();
    }

    public List getAll() {
        return abonnes;
    }

    public void save(Abonne abonne) throws IOException {
        // TODO check for duplicates
        _log.debug("abonne: {}, class: {}", abonnes, abonnes.getClass());
        if (get(abonne.getId()).isEmpty()) {
            abonnes.add(abonne);
        }
        persist();
    }

    public void delete(Abonne abonne) throws IOException {
        Abonne temp = findById(abonne.getId());
        abonnes.remove(temp);
        persist();
    }

    private Abonne findById(Long id) {
        return abonnes.stream().filter(a -> (id.equals(a.getId()))).findFirst().orElse(null);
    }

    private void persist() throws IOException {
        String temp = mapper.writeValueAsString(abonnes);

        // TODO : pb d'écriture en UTF-8
        OutputStream fos = Files.newOutputStream(path);
        PrintWriter writer = new PrintWriter(fos);
        // DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        // outStream.writeUTF(temp);
        writer.print(temp);
        writer.close();
        fos.close();
    }

    private void read() throws IOException {
        List<String> read = Files.readAllLines(path, StandardCharsets.UTF_8);
        String str = String.join("", read);
        abonnes = new ArrayList<>(Arrays.asList(mapper.readValue(str, Abonne[].class)));
    }

}

