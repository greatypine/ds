package com.guoanshequ.dc.das.model.base;

import java.io.Serializable;

public class OptLockEntity implements Serializable {

	private Long id;

    private int version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
