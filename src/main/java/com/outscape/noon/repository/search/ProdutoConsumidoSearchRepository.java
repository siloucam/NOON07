package com.outscape.noon.repository.search;

import com.outscape.noon.domain.ProdutoConsumido;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProdutoConsumido entity.
 */
public interface ProdutoConsumidoSearchRepository extends ElasticsearchRepository<ProdutoConsumido, Long> {
}
