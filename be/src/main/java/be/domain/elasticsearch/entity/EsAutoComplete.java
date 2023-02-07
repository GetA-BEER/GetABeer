package be.domain.elasticsearch.entity;

import javax.persistence.Id;

import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "auto_complete")
public class EsAutoComplete implements Persistable {

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
