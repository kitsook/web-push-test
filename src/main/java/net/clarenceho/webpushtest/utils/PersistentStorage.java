package net.clarenceho.webpushtest.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import net.clarenceho.webpushtest.models.SubDetail;
import nl.martijndwars.webpush.Subscription;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Stream;

@Repository
public class PersistentStorage implements Storage {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void createOrUpdateSubscription(Subscription subscription) {
        SubDetail detail;
        try {
            TypedQuery<SubDetail> tq = entityManager.createQuery("from SubDetail WHERE endpoint=?1", SubDetail.class);
            detail = tq.setParameter(1, subscription.endpoint).getSingleResult();
            detail.setAuth(subscription.keys.auth);
            detail.setP256h(subscription.keys.p256dh);
        } catch (NoResultException e) {
            detail = new SubDetail(null, subscription.endpoint, subscription.keys.p256dh, subscription.keys.auth);
        }
        entityManager.persist(detail);
    }

    @Transactional
    @Override
    public void removeSubscription(String endpoint) {
        try {
            TypedQuery<SubDetail> tq = entityManager.createQuery("from SubDetail WHERE endpoint=?1", SubDetail.class);
            SubDetail detail = tq.setParameter(1, endpoint).getSingleResult();
            entityManager.remove(detail);
        } catch (NoResultException e) {
            // ignore
        }
    }

    @Transactional
    @Override
    public Collection<Subscription> getSubscriptions() {
        Stream<SubDetail> details = getAllSubscriptions();
        return details
            .map(d -> new Subscription(d.getEndpoint(), new Subscription.Keys(d.getP256h(), d.getAuth())))
            .toList();
    }

    private Stream<SubDetail> getAllSubscriptions() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubDetail> query = cb.createQuery(SubDetail.class);
        Root<SubDetail> root = query.from(SubDetail.class);
        query.select(cb.construct(SubDetail.class, root.get("id"), root.get("endpoint"), root.get("p256h"), root.get("auth")));
        return entityManager.createQuery(query).getResultStream();
    }
}
