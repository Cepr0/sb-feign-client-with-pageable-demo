package io.github.cepr0.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Data
@EqualsAndHashCode(of = "id")
@Entity
class Model {

	@Id
	private Integer id;

	@JsonIgnore
	@Version
	private Integer version;

	private String text;

	@Tolerate
	public Model(Integer id) {
		this.id = id;
		text = "text " + id;
	}
}
