package com.outscape.noon.repository.search;

import com.outscape.noon.domain.ExtendUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ExtendUser entity.
 */
public interface ExtendUserSearchRepository extends ElasticsearchRepository<ExtendUser, Long> {
}
