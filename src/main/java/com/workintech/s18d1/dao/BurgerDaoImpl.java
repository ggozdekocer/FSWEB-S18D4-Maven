package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class BurgerDaoImpl implements BurgerDao {

    private final EntityManager entityManager;

    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public Burger findById(Long id) {

        Burger burger = entityManager.find(Burger.class, id);

        if (burger == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }

        return burger;
    }

    @Override
    public List<Burger> findAll() {
        return entityManager.createQuery("FROM Burger", Burger.class).getResultList();
    }

    @Override
    public List<Burger> findByPrice(Double price) {

        TypedQuery<Burger> query = entityManager.createQuery(
                "FROM Burger b WHERE b.price > :price ORDER BY b.price DESC",
                Burger.class
        );

        query.setParameter("price", price);

        return query.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {

        TypedQuery<Burger> query = entityManager.createQuery(
                "FROM Burger b WHERE b.breadType = :bt ORDER BY b.name ASC",
                Burger.class
        );

        query.setParameter("bt", breadType);

        return query.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {

        TypedQuery<Burger> query = entityManager.createQuery(
                "FROM Burger b WHERE b.contents LIKE :c",
                Burger.class
        );

        query.setParameter("c", "%" + content + "%");

        return query.getResultList();
    }

    @Override
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }

    @Override
    public Burger remove(Long id) {
        Burger burger = entityManager.find(Burger.class, id);

        if (burger == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }

        entityManager.remove(burger);
        return burger;
    }
}