package br.ufsc.bridge.res.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sort {

	ASC(""),
	DESC("-");

	private String code;
}
