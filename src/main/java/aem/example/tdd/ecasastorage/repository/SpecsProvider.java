package aem.example.tdd.ecasastorage.repository;

import aem.example.tdd.ecasastorage.entity.Color;
import aem.example.tdd.ecasastorage.entity.Product;
import aem.example.tdd.ecasastorage.entity.Product_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
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
                    default:
                        break;
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}