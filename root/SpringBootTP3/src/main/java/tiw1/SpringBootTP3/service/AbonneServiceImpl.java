package tiw1.SpringBootTP3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.service.spi.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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

@Component
public class AbonneServiceImpl implements AbonneService<Abonne>, Startable {

    private static final Logger LOGGER_ABONNE = LoggerFactory.getLogger(AbonneServiceImpl.class);
    private final String ABONNES_JSON = "abonnes.json";
    private Path path = Paths.get(ABONNES_JSON);
    private ObjectMapper mapper = new ObjectMapper();
    private static List<Abonne> abonnes = new ArrayList<>();

    @Override
    public Optional get(long id) {
        System.out.println("ID !!!!! " + id);
        for (Abonne abonne : abonnes) {
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
        System.out.println("TEST !!!!!!!!!!!!!!!!!!!!!! ! ! ! ! " + get(abonne.getId()));
        //LOGGER_ABONNE.debug("abonne: {}, class: {}", abonnes, abonnes.getClass());
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

    @Override
    public Abonne findById(Long id) {
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

    @Override
    public void read() throws IOException {
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
}