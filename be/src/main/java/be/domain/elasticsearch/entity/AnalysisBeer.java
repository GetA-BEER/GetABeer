package be.domain.elasticsearch.entity;

import javax.persistence.Id;

import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(indexName = "#{elasticsearchIndex}")
public class AnalysisBeer implements Persistable {

	@Id
	@Field(type = FieldType.Long)
	Integer id;

	@Field(type = FieldType.Text, name = "query_keyword")
	String queryKeyword;

	@Override
	public Object getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return false;
	}
}
