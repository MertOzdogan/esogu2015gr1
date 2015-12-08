package tr.com.mergentech.mergenerp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import tr.com.mergentech.mergenbase.entity.BaseEntity;

@Entity(name = "zimmet")
public class debit extends BaseEntity {
	private static final long serialVersionUID = -4131017637756081291L;
	@Column(name = "ID", nullable = false)
	private Long ID;
	@Column(name = "zimmetIsim")
	private String zimmetIsim;
	
	@Column(name = "zimmetAciklama")
	private String zimmetAciklama;
	
	@Column(name = "olusturanID")
	private Long olusturanID;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "olusturmaTar")
	private Date olusturmaTar;
	
	@Column(name = "guncelleyenID")
	private Long guncelleyenID;


	@Temporal(TemporalType.DATE)
	@Column(name = "guncellemeTar")
	private Date guncellemeTar;


	public Long getID() {
		return ID;
	}


	public void setID(Long iD) {
		ID = iD;
	}


	public String getZimmetIsim() {
		return zimmetIsim;
	}


	public void setZimmetIsim(String zimmetIsim) {
		this.zimmetIsim = zimmetIsim;
	}


	public String getZimmetAciklama() {
		return zimmetAciklama;
	}


	public void setZimmetAciklama(String zimmetAciklama) {
		this.zimmetAciklama = zimmetAciklama;
	}


	public Long getOlusturanID() {
		return olusturanID;
	}


	public void setOlusturanID(Long olusturanID) {
		this.olusturanID = olusturanID;
	}


	public Date getOlusturmaTar() {
		return olusturmaTar;
	}


	public void setOlusturmaTar(Date olusturmaTar) {
		this.olusturmaTar = olusturmaTar;
	}


	public Long getGuncelleyenID() {
		return guncelleyenID;
	}


	public void setGuncelleyenID(Long guncelleyenID) {
		this.guncelleyenID = guncelleyenID;
	}


	public Date getGuncellemeTar() {
		return guncellemeTar;
	}


	public void setGuncellemeTar(Date guncellemeTar) {
		this.guncellemeTar = guncellemeTar;
	}
	
}
