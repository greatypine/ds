package com.guoanshequ.dc.das.model.base;

public abstract class PMSEntity extends OptLockEntity {

	/** The Constant serialVersionUID. */
	protected static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + this.getId();
	}
}
