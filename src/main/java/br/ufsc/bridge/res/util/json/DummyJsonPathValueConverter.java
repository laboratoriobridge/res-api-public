package br.ufsc.bridge.res.util.json;

public class DummyJsonPathValueConverter<T, I> implements JsonPathValueConverter<T, I> {
	@Override
	public T convert(I value) {
		return (T) value;
	}
}
