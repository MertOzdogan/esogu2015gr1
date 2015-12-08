package tr.com.mergentech.mergenerp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tr.com.mergentech.mergenbase.entity.BaseEntity;

@Entity(name = "maas")
public class Salary extends BaseEntity {
	private static final long serialVersionUID = -4131017637756081291L;

	@Column(name = "ID", nullable = false)
	private Long ID;
	
	@Column(name = "miktar")
	private double miktar;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "m_baslangicTar")
	private Date m_baslangicTar;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "olusturmaTar")
	private Date olusturmaTar;
	
	@Column(name = "olusturanID")
	private Long olusturanID;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "guncellemeTar")
	private Date guncellemeTar;
	
	@Column(name = "guncelleyenID")
	private Long guncelleyenID;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public double getMiktar() {
		return miktar;
	}

	public void setMiktar(double miktar) {
		this.miktar = miktar;
	}

	public Date getM_baslangicTar() {
		return m_baslangicTar;
	}

	public void setM_baslangicTar(Date m_baslangicTar) {
		this.m_baslangicTar = m_baslangicTar;
	}

	public Date getOlusturmaTar() {
		return olusturmaTar;
	}

	public void setOlusturmaTar(Date olusturmaTar) {
		this.olusturmaTar = olusturmaTar;
	}

	public Long getOlusturanID() {
		return olusturanID;
	}

	public void setOlusturanID(Long olusturanID) {
		this.olusturanID = olusturanID;
	}

	public Date getGuncellemeTar() {
		return guncellemeTar;
	}

	public void setGuncellemeTar(Date guncellemeTar) {
		this.guncellemeTar = guncellemeTar;
	}

	public Long getGuncelleyenID() {
		return guncelleyenID;
	}

	public void setGuncelleyenID(Long guncelleyenID) {
		this.guncelleyenID = guncelleyenID;
	}
	
	
}
