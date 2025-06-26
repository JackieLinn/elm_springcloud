package ynu.jackielinn.business.mapper;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ynu.jackielinn.business.entity.BusinessEsDoc;

import java.util.List;

public interface BusinessEsRepository extends ElasticsearchRepository<BusinessEsDoc, Long> {
    List<BusinessEsDoc> findByBusinessNameContaining(String keyword);
}
