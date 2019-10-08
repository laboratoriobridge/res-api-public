package br.ufsc.bridge.res.util.json;

public interface JsonPathValueConverter<T, I> {

	T convert(I value);

}
