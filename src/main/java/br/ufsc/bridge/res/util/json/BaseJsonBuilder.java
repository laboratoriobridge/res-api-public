package br.ufsc.bridge.res.util.json;

import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.DocumentContext;

public class BaseJsonBuilder<T extends BaseJsonBuilder<?>> {

	protected T parent;

	protected DocumentContext document;

	protected List<BaseJsonBuilder<?>> children;

	public BaseJsonBuilder(T parent, String fileName) {
		this.document = ResJsonUtils.getJsonDocument(fileName);
		this.children = new ArrayList<>();
		this.parent = parent;
		if (parent != null) {
			parent.children.add(this);
		}
	}

	public BaseJsonBuilder(String fileName) {
		this(null, fileName);
	}

	public String getJsonString() {
		this.json();
		return this.document.jsonString();
	}

	public Object json() {
		for (BaseJsonBuilder<?> child : this.children) {
			Object childJson = child.json();
			if (childJson != null) {
				this.document.add(this.childJsonPath(), childJson);
			}
		}
		return this.document.json();
	}

	protected String childJsonPath() {
		return null;
	}

	public T close() {
		return this.parent;
	}

}
