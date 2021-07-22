package org.opoo.apps.module;


/**
 * 简单模块实现类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class DummyModule implements Module<DummyModule> {
	private String name;

	public DummyModule(String name) {
		this.name = name;
	}

	public void destroy() {
	}

	public void init() {
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass()) {
			return false;
		} else {
			DummyModule that = (DummyModule) o;
			return name == null ? that.name != null : !name.equals(that.name);
		}
	}

	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DummyModule");
		sb.append("{name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
