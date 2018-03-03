package com.outscape.noon.repository.search;

import com.outscape.noon.domain.Cliente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Cliente entity.
 */
public interface ClienteSearchRepository extends ElasticsearchRepository<Cliente, Long> {
}
