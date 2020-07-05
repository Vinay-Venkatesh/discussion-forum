package com.forum.units;

import java.util.Date;

import com.forum.util.Utility;

public abstract class AbstractEntity {

	private Date created;
	private long id;

	/**
	 *
	 * getId that requires no parameters and
	 * returns the id of this Abstract entity
	 *
	 * @return the id of this Abstract entity
	 */
	public long getId() {
		return id;
	}
	
	/**
	 *
	 * setId that takes in a parameter and
	 * sets the id of this Abstract entity to the parameter.
	 *
	 *
	 * @param id: the id of this Abstract entity
	 */
	
	public void setId(Long lastEntry) {
		this.id = lastEntry;
		
	}
	
	/**
	 *
	 * An abstract method named autoGenerateId.
	 *
	 * This method doesn't require any parameters and returns void
	 *
	 */
	
	public abstract void  autoGenerateId();

	/**
	 *
	 * getCreated that requires no parameters and
	 * returns the date of creation
	 *
	 * @return the id of this Abstract entity
	 */
	
	public Date getCreated() {
		return created;
	}
	
	/**
	 *
	 * setCreated that requires no parameters and
	 * and sets currentDate.
	 *
	 */
	public void setCreated() {
		this.created = Utility.getCurrentDate();
	}
}
