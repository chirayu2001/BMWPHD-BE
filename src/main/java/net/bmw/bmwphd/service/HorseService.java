package net.bmw.bmwphd.service;

import net.bmw.bmwphd.dao.HorseDao;
import net.bmw.bmwphd.domain.Horse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
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

    public Horse findById(String horseId) {
        return horseDao.findById(horseId).get();
    }

    public void update(String horseId, Horse updatedHorse) {
        updatedHorse.setId(horseId);
        horseDao.save(updatedHorse);
    }

    private Specification<Horse> createSpecification(Map searchMap) {

        return new Specification<Horse>() {

            @Override
            public Predicate toPredicate(Root<Horse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    //predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("id").as(String.class)),
                            "%" + (String) searchMap.get("id").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                    //predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name").toString().toLowerCase() + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("name").as(String.class)),
                            "%" + (String) searchMap.get("name").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("dam1") != null && !"".equals(searchMap.get("dam1"))) {
                    //predicateList.add(cb.like(root.get("dam1").as(String.class), "%" + (String) searchMap.get("dam1") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("dam1").as(String.class)),
                            "%" + (String) searchMap.get("dam1").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("sire1") != null && !"".equals(searchMap.get("sire1"))) {
                    //predicateList.add(cb.like(root.get("sire1").as(String.class), "%" + (String) searchMap.get("sire1") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("sire1").as(String.class)),
                            "%" + (String) searchMap.get("sire1").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("dam2") != null && !"".equals(searchMap.get("dam2"))) {
                    //predicateList.add(cb.like(root.get("dam2").as(String.class), "%" + (String) searchMap.get("dam2") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("dam2").as(String.class)),
                            "%" + (String) searchMap.get("dam2").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("maneuver_score") != null && !"".equals(searchMap.get("maneuver_score"))) {
                    //predicateList.add(cb.like(root.get("maneuver_score").as(String.class), "%" + (String) searchMap.get("maneuver_score") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("maneuver_score").as(String.class)),
                            "%" + (String) searchMap.get("maneuver_score").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("lte") != null && !"".equals(searchMap.get("lte"))) {
                    //predicateList.add(cb.like(root.get("lte").as(String.class), "%" + (String) searchMap.get("lte") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("lte").as(String.class)),
                            "%" + (String) searchMap.get("lte").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("pe") != null && !"".equals(searchMap.get("pe"))) {
                    //predicateList.add(cb.like(root.get("pe").as(String.class), "%" + (String) searchMap.get("pe") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("pe").as(String.class)),
                            "%" + (String) searchMap.get("pe").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("show") != null && !"".equals(searchMap.get("show"))) {
                    //predicateList.add(cb.like(root.get("show").as(String.class), "%" + (String) searchMap.get("show") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("show").as(String.class)),
                            "%" + (String) searchMap.get("show").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("horse_class") != null && !"".equals(searchMap.get("horse_class"))) {
                    //predicateList.add(cb.like(root.get("horse_class").as(String.class), "%" + (String) searchMap.get("horse_class") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("horse_class").as(String.class)),
                            "%" + (String) searchMap.get("horse_class").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("level") != null && !"".equals(searchMap.get("level"))) {
                    //predicateList.add(cb.like(root.get("level").as(String.class), "%" + (String) searchMap.get("level") + "%"));
                    predicateList.add(cb.like(
                            cb.lower(root.get("level").as(String.class)),
                            "%" + (String) searchMap.get("level").toString().toLowerCase() + "%"));
                }
                if (searchMap.get("riderList") != null && !"".equals(searchMap.get("riderList"))) {
                    Join join = root.join("riderList");
                    return cb.equal(join.get("riderId"), searchMap.get("riderList"));
//                    predicateList.add(cb.like(
//                            cb.lower(root.get("level").as(String.class)),
//                            "%" + (String) searchMap.get("level").toString().toLowerCase() + "%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
