package org.opoo.apps;

import java.io.Serializable;

public class EntityDescriptor implements Serializable, AppsObject {
	private static final long serialVersionUID = 2484994368211790978L;
	protected long objectId;
    protected int objectType;

    /**
     * Constructor for serialization use only.
     */
    public EntityDescriptor() {}
    
    public EntityDescriptor(int objectType, long objectId) {
		super();
		this.objectType = objectType;
		this.objectId = objectId;
	}
    
    /**
     * Creates an {@link EntityDescriptor} instance from an existing JiveObject.
     *
     * @param bean The AppsObject to create an EntityDescriptor for.
     */
    public EntityDescriptor(AppsObject bean) {
        objectType = bean.getObjectType();
        objectId = bean.getId();
    }
	public Long getId(){
    	return objectId;
    }
    public void setId(long id){
    	this.objectId = id;
    }
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	
	 /**
     * Returns true if this entity descriptor references a valid object. False otherwise.
     *
     * @return true if this entity descriptor references a valid object. False otherwise.
     */
    public boolean isValid() {
        return objectId > 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof EntityDescriptor)) {
            return false;
        }

        EntityDescriptor that = (EntityDescriptor) o;

        if (objectId != that.objectId) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (objectType != that.objectType) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + objectType;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("EntityDescriptor");
        sb.append("{objectId=").append(objectId);
        sb.append(", objectType=").append(objectType);
        sb.append('}');
        return sb.toString();
    }
}
