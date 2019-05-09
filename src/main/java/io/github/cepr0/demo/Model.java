package io.github.cepr0.demo;

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

	@Id private Integer id;
	@Version private Integer version;
	private String text;

	@Tolerate
	public Model(Integer id) {
		this.id = id;
		text = "text " + id;
	}
}
