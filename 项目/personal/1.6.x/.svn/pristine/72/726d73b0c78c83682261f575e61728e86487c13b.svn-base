package org.opoo.apps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VersionedExternalEntityDescriptor extends VersionedEntityDescriptor {
	private static final long serialVersionUID = -4352382042787673667L;
	protected String uuid = null;
    protected long longID = -1;
    protected transient Map<String, String> externalIDs;

    /**
     * Constructor for serialization use only.
     */
    public VersionedExternalEntityDescriptor() {
    }

//    public VersionedExternalEntityDescriptor(VersionableContentObject vco) {
//        super(vco);
//    }

    public VersionedExternalEntityDescriptor(int objectType, long objectID, int version) {
        super(objectType, objectID, version);
        if (version < 1) {
            throw new IllegalArgumentException("Minimum version is 1");
        }
        this.externalIDs = new HashMap<String, String>();
    }

    public VersionedExternalEntityDescriptor(AppsObject bean, String uuid) {
        super(bean);
        this.uuid = uuid;
        this.externalIDs = new HashMap<String, String>();
    }

    public VersionedExternalEntityDescriptor(AppsObject bean, long longID) {
        super(bean);
        this.longID = longID;
        this.externalIDs = new HashMap<String, String>();
    }

    public VersionedExternalEntityDescriptor(String extrenalIDtype, String externalID) {
        this.externalIDs = new HashMap<String, String>();
        this.externalIDs.put(extrenalIDtype, externalID);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExternalID(String type) {
        return externalIDs.get(type);
    }

    public Map<String, String> getExternalIDs() {
        Map<String, String> m = externalIDs != null ? externalIDs : new HashMap<String, String>();
        return Collections.unmodifiableMap(m);
    }

    public void setExternalID(String type, String externalID) {
        externalIDs.put(type, externalID);
    }

    public long getLongID() {
        return longID;
    }

    public void setLongID(long longID) {
        this.longID = longID;
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

        VersionedExternalEntityDescriptor that = (VersionedExternalEntityDescriptor) o;

        if (longID != that.longID) {
            return false;
        }

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (int) (longID ^ (longID >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "VersionedExternalEntityDescriptor{" + super.toString() + ", version=" + version + ", uuid='" + uuid
                + '\'' + ", longID='" + longID + '\'' + '}';
    }

    public static VersionedExternalEntityDescriptor copyFrom(VersionedExternalEntityDescriptor e) {
        VersionedExternalEntityDescriptor newEntity = new VersionedExternalEntityDescriptor(e.getObjectType(), e.getId(), e.getVersion());
        newEntity.setLongID(e.getLongID());
        newEntity.setUuid(e.getUuid());
        return newEntity;
    }
}
