package aem.example.tdd.ecasastorage.repository;

import aem.example.tdd.ecasastorage.entity.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecsProvider {

    public static Specification<Product> filterProduct(Map<String, String> queryParams) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                String key = entry.getKey();
                String param = entry.getValue();
                switch (key) {
                    case Product_.LOT:
                        predicates.add(criteriaBuilder.like(root.get(Product_.lot), param));
                        break;
                    case Product_.IS_FRAGILE:
                        predicates.add(Boolean.parseBoolean(param)
                                ? criteriaBuilder.isTrue(root.get(Product_.isFragile))
                                : criteriaBuilder.isFalse(root.get(Product_.isFragile)));
                        break;
                    case Product_.COLOR:
                        predicates.add(criteriaBuilder.equal(root.get(Product_.color), Color.valueOf(param)));
                        break;
                    case Product_.SECTIONS:
                        SetJoin<Product, SectionItem> joinSectionItem = root.join(Product_.sections);
                        predicates.add(criteriaBuilder.equal(joinSectionItem.get(SectionItem_.section), Long.parseLong(param)));
                        break;
                    default:
                        break;
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
