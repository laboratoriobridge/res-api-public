package br.ufsc.bridge.res.util.json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonPathProperty {

	String value();

	Group group() default Group.EMPTY;

	Class<? extends JsonPathValueConverter> converter() default DummyJsonPathValueConverter.class;

	@AllArgsConstructor
	enum Group {
		EMPTY(""),
		CARACTERIZACAO_DO_ATENDIMENTO("$.content[?(@.name.value == 'Caracterização do atendimento')]"),

		ADMISSAO_DO_PACIENTE(".items[?(@.name.value == 'Admissão do paciente')]"),

		LOCALIZACAO_ATRIBUIDA_AO_PACIENTE(Group.ADMISSAO_DO_PACIENTE.path + ".data.items[?(@.name.value == 'Localização atribuída ao paciente')]"),

		INSTITUICAO(Group.LOCALIZACAO_ATRIBUIDA_AO_PACIENTE.path + ".items[?(@.name.value == 'Instituição')]"),

		OBSERVACOES("$.content[?(@.name.value == 'Observações')]"),

		OBSERVACOES_MEDICOES(Group.OBSERVACOES.path + ".items[?(@.name.value == 'Medições')]"),

		OBSERVACOES_INFORMACOES_ADICIONAIS(Group.OBSERVACOES.path + ".items[?(@.name.value == 'Informações adicionais')]"),

		;

		@Getter
		private String path;
	}

}
