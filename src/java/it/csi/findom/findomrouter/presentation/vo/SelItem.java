/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.vo;

public class SelItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;
	private String tag;
	
	public SelItem() {
		super();
	}

	public SelItem(String chiave, String valore) {
		this();
		setKey(chiave);
		setValue(valore);
	}
	
	public String toString() {
		return "SelItem [key=" + key + ", value=" + value + ", tag=" + tag
				+ "]";
	}
	
	// Sonar : se si fa l'averride del metodo equals allora bisogna fare l'override anche del metodo hashCode
//	public int hashCode(){
//		return super.hashCode();
//		
//	}
//	
	// TODO : serve?
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		SelItem other = (SelItem) obj;
//		if (key == null) {
//			if (other.key != null)
//				return false;
//		} else if (!key.equals(other.key))
//			return false;
//		if (tag == null) {
//			if (other.tag != null)
//				return false;
//		} else if (!tag.equals(other.tag))
//			return false;
//		if (value == null) {
//			if (other.value != null)
//				return false;
//		} else if (!value.equals(other.value))
//			return false;
//		return true;
//	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
