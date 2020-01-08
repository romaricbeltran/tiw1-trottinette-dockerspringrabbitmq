package tiw1.SpringBootTP3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tiw1.SpringBootTP3.model.Emprunt;
import tiw1.SpringBootTP3.repository.EmpruntRepository;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class EmpruntServiceImpl implements EmpruntService<Emprunt> {

//    @PersistenceContext
//    private EntityManager em;
    private final EmpruntRepository empruntRepository;
    //private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-pu");

//    public static EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }

    @Autowired
    public EmpruntServiceImpl(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    @Override
    public Optional<Emprunt> get(long id) {
        return empruntRepository.findById(id);
    }

//    @Override
//    public Optional<Emprunt> get(long id) {
//        EntityManager em = getEntityManager();
//
//        try {
//            Emprunt t = em
//                    .createNamedQuery("empruntById", Emprunt.class)
//                    .setParameter("id", id)
//                    .getSingleResult();
//            return Optional.ofNullable(t);
//        } catch (NoResultException e) {
//            return null;
//        }
//    }

        @Override
    public List<Emprunt> getAll() {
        return empruntRepository.findAll();
    }
//    @Override
//    public List<Emprunt> getAll() {
//        EntityManager em = getEntityManager();
//
//        List<Emprunt> emprunts = em.createNamedQuery("allEmprunts", Emprunt.class).getResultList();
//        return emprunts;
//    }

    @Override
    @Transactional
    public void save(Emprunt emprunt) {
        empruntRepository.save(emprunt);
    }

//    @Override
//    @Transactional
//    public void save(Emprunt emprunt) {
//        EntityManager em = getEntityManager();
//
//        em.getTransaction().begin();
//        if (emprunt.getId() != null) {
//            em.merge(emprunt);
//        } else {
//            em.persist(emprunt);
//        }
//        em.getTransaction().commit();
//    }

    @Override
    public void delete(Emprunt emprunt) {
        if(empruntRepository.findById(emprunt.getId()).isPresent()) {
            empruntRepository.deleteById(emprunt.getId());
        }
    }

//    @Override
//    public void delete(Emprunt emprunt) {
//        EntityManager em = getEntityManager();
//
//        Emprunt persisted = findById(emprunt.getId());
//        if (persisted != null) {
//            em.getTransaction().begin();
//            em.remove(persisted);
//            em.getTransaction().commit();
//        }
//    }
//
//    @Override
//    public Emprunt findById(long id) {
//        EntityManager em = getEntityManager();
//
//        return em.find(Emprunt.class, id);
//    }
//
//    public List getEmpruntByDate(Date date) {
//        EntityManager em = getEntityManager();
//
//        List<Emprunt> emprunts = em.createNamedQuery("empruntByDate", Emprunt.class).setParameter("date", date).getResultList();
//        return emprunts;
//    }
}
