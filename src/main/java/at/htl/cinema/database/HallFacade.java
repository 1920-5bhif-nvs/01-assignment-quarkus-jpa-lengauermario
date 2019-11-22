package at.htl.cinema.database;


import at.htl.cinema.model.Hall;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class HallFacade extends Facade<Hall> {
    @Override
    public List<Hall> getAll() {
        return em.createNamedQuery("Hall.findAll", Hall.class).getResultList();
    }

    @Override
    public Hall getById(Long id) {
        return em.find(Hall.class, id);
    }

    public List<Hall> getByCinemaId(Long id) {
        return em.createNamedQuery("Hall.findAll", Hall.class).getResultList().stream().filter(x -> x.getCinema().getId() == id).collect(Collectors.toList());
    }

    @Override
    public Hall update(Hall hall) {
        return em.merge(hall);
    }

    @Override
    public void delete(Hall hall) {
        em.remove(hall);
        em.flush();
    }
}
