package net.bmw.bmwphd.service;

import net.bmw.bmwphd.dao.HorseDao;
import net.bmw.bmwphd.domain.Horse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HorseService {
    private HorseDao horseDao;

    public HorseService(HorseDao horseDao) {
        this.horseDao = horseDao;
    }

    public List<Horse> findAll() {
        return horseDao.findAll();
    }

    public List<Horse> findSearch(Map whereMap) {
        Specification<Horse> specification = createSpecification(whereMap);
        return horseDao.findAll(specification);
    }

    private Specification<Horse> createSpecification(Map searchMap) {

        return new Specification<Horse>() {

            @Override
            public Predicate toPredicate(Root<Horse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
                }
                if (searchMap.get("dam") != null && !"".equals(searchMap.get("dam"))) {
                    predicateList.add(cb.like(root.get("dam").as(String.class), "%" + (String) searchMap.get("dam") + "%"));
                }
                if (searchMap.get("sire") != null && !"".equals(searchMap.get("sire"))) {
                    predicateList.add(cb.like(root.get("sire").as(String.class), "%" + (String) searchMap.get("sire") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
