package br.ufsc.bridge.res.dab.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.joda.time.LocalTime;

@Getter
@AllArgsConstructor
public enum ResABTurnoEnum {

	MANHA("at0.166"),
	TARDE("at0.167"),
	NOITE("at0.168");

	private String codigo;

	public static ResABTurnoEnum getByCodigo(String codigo) {
		for (ResABTurnoEnum value : values()) {
			if (value.getCodigo().equals(codigo)) {
				return value;
			}
		}
		return null;
	}

	public static ResABTurnoEnum getTurnoByHora(Date hora) {
		if (isTurnoMatutino(hora)) {
			return MANHA;
		}
		if (isTurnoVespertino(hora)) {
			return TARDE;
		}
		return NOITE;
	}

	public static boolean isTurnoMatutino(Date date) {
		return timeBetweenTimes(new LocalTime(date.getTime()), new LocalTime(6, 0, 0, 0), new LocalTime(11, 59, 0, 0));
	}

	public static boolean isTurnoVespertino(Date date) {
		return timeBetweenTimes(new LocalTime(date.getTime()), new LocalTime(12, 0, 0, 0), new LocalTime(17, 59, 0, 0));
	}

	public static boolean timeBetweenTimes(LocalTime time, LocalTime inicio, LocalTime fim) {
		return inicio.compareTo(time) <= 0 && time.compareTo(fim) <= 0;
	}

}
