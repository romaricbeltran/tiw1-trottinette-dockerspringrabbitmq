package tiw1.emprunt.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.model.Abonne;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AbonneDAO implements DAO<Abonne>, Startable {
    private static final Logger LOGGER_ABONNE = LoggerFactory.getLogger(TrottinetteLoader.class);

    private ObjectMapper mapper = new ObjectMapper();
    private final String ABONNES_JSON = "abonnes.json";
    private Path path = Paths.get(ABONNES_JSON);


    private static List<Abonne> abonnes = new ArrayList<>();

    public AbonneDAO() {}

    @Override
    public Optional get(long id) {
        for(Abonne abonne : abonnes) {
            if (abonne.getId() == id) {
                return Optional.of(abonne);
            }
        }
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return abonnes;
    }

    @Override
    public void save(Abonne abonne) throws IOException {
        // TODO check for duplicates
        LOGGER_ABONNE.debug("abonne: {}, class: {}", abonnes, abonnes.getClass());
        if (get(abonne.getId()).isEmpty()) {
            abonnes.add(abonne);
        }
        persist();
    }

    @Override
    public void update(Abonne abonne) throws IOException {
        delete(abonne);
        save(abonne);
        persist();
    }

    @Override
    public void delete(Abonne abonne) throws IOException {
        Abonne temp = findById(abonne.getId());
        abonnes.remove(temp);
        persist();
    }

    private Abonne findById (Long id) {
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

    @Override
    public void start() {
        try {
            read();
            LOGGER_ABONNE.info("Composant AbonneDAO démarré. Objet d'accès aux données : " + this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        LOGGER_ABONNE.info("Composant AbonneDAO stoppé.");
    }
}
