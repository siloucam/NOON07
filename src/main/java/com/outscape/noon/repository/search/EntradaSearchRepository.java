package com.outscape.noon.repository.search;

import com.outscape.noon.domain.Entrada;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Entrada entity.
 */
public interface EntradaSearchRepository extends ElasticsearchRepository<Entrada, Long> {
}
