// package be.global.config;
//
// import org.elasticsearch.client.RestHighLevelClient;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.elasticsearch.client.ClientConfiguration;
// import org.springframework.data.elasticsearch.client.RestClients;
// import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
// import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
// import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
// import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
// import be.domain.elasticsearch.repository.BeerSearchRepository;
//
// @Configuration
// @EnableElasticsearchRepositories(basePackageClasses = BeerSearchRepository.class)
// public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
//
// 	@Value("${elasticsearch.host}")
// 	private String host;
// 	@Value("${elasticsearch.port}")
// 	private int port;
//
// 	@Bean
// 	@Override
// 	public RestHighLevelClient elasticsearchClient() {
//
// 		final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
// 			.connectedTo(host + ":" + port)
// 			// .usingSsl()        // connect to https
// 			.withBasicAuth("elastic", "changeme")
// 			.build();
//
// 		return RestClients.create(clientConfiguration).rest();
// 	}
//
// 	@Bean
// 	public ElasticsearchOperations elasticsearchOperations() {
// 		return new ElasticsearchRestTemplate(elasticsearchClient());
// 	}
// }
