package org.opoo.apps;

public class VersionedEntityDescriptor extends EntityDescriptor {
	private static final long serialVersionUID = -1645533670564986078L;
	protected int version = -1;

	public VersionedEntityDescriptor(AppsObject bean) {
		super(bean);
	}

	public VersionedEntityDescriptor() {
		super();
	}

	public VersionedEntityDescriptor(int objectType, long objectID, int version) {
		super(objectType, objectID);
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		VersionedEntityDescriptor that = (VersionedEntityDescriptor) o;

		if (version != that.version) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + version;
		return result;
	}

	@Override
	public String toString() {
		return "VersionedEntityDescriptor{" + super.toString() + ", version="
				+ version + '}';
	}
}
