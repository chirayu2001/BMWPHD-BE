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
                if (searchMap.get("dam1") != null && !"".equals(searchMap.get("dam1"))) {
                    predicateList.add(cb.like(root.get("dam1").as(String.class), "%" + (String) searchMap.get("dam1") + "%"));
                }
                if (searchMap.get("sire1") != null && !"".equals(searchMap.get("sire1"))) {
                    predicateList.add(cb.like(root.get("sire1").as(String.class), "%" + (String) searchMap.get("sire1") + "%"));
                }
                if (searchMap.get("dam2") != null && !"".equals(searchMap.get("dam2"))) {
                    predicateList.add(cb.like(root.get("dam2").as(String.class), "%" + (String) searchMap.get("dam2") + "%"));
                }
                if (searchMap.get("sire2") != null && !"".equals(searchMap.get("sire2"))) {
                    predicateList.add(cb.like(root.get("sire2").as(String.class), "%" + (String) searchMap.get("sire2") + "%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
