package com.outscape.noon.repository.search;

import com.outscape.noon.domain.Comanda;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Comanda entity.
 */
public interface ComandaSearchRepository extends ElasticsearchRepository<Comanda, Long> {
}
