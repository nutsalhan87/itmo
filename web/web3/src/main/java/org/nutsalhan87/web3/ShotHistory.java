package org.nutsalhan87.web3;

import jakarta.annotation.PostConstruct;
import jakarta.el.ELContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ShotHistory implements Serializable {
    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void processAndAddShot() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Coordinates coordinates = (Coordinates) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(elContext, null, "coordinates");
        Shot shot = HitHandler.process(coordinates.getX(), coordinates.getY(), coordinates.getR());
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(shot);
            session.getTransaction().commit();
        }
    }

    public List<Shot> getHistory() {
        List<Shot> history = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            history = session.createQuery("from Shot").list();
            session.getTransaction().commit();
        }
        return history;
    }
}
