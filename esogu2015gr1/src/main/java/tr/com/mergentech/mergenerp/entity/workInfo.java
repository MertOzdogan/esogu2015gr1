package tr.com.mergentech.mergenerp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tr.com.mergentech.mergenbase.entity.BaseEntity;

@Entity(name = "isBilgisi")
public class workInfo extends BaseEntity {
	private static final long serialVersionUID = -4131017637756081291L;
	
	@Column(name = "ID", nullable = false)
	private Long ID;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "baslangicTar")
	private Date baslangicTar;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ayrilmaTar")
	private Date ayrilmaTar;
	
	@Column(name = "ayrilmaNeden")
	private String ayrilmaNeden;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "guncellemeTarihi")
	private Date guncellemeTarihi;
	
	@Column(name = "guncelleyenID")
	private Long guncelleyenID;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "olusturmaTarihi")
	private Date olusturmaTarihi;
	
	@Column(name = "olusturanID")
	private Long olusturanID;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Date getBaslangicTar() {
		return baslangicTar;
	}

	public void setBaslangicTar(Date baslangicTar) {
		this.baslangicTar = baslangicTar;
	}

	public Date getAyrilmaTar() {
		return ayrilmaTar;
	}

	public void setAyrilmaTar(Date ayrilmaTar) {
		this.ayrilmaTar = ayrilmaTar;
	}

	public String getAyrilmaNeden() {
		return ayrilmaNeden;
	}

	public void setAyrilmaNeden(String ayrilmaNeden) {
		this.ayrilmaNeden = ayrilmaNeden;
	}

	public Date getGuncellemeTarihi() {
		return guncellemeTarihi;
	}

	public void setGuncellemeTarihi(Date guncellemeTarihi) {
		this.guncellemeTarihi = guncellemeTarihi;
	}

	public Long getGuncelleyenID() {
		return guncelleyenID;
	}

	public void setGuncelleyenID(Long guncelleyenID) {
		this.guncelleyenID = guncelleyenID;
	}

	public Date getOlusturmaTarihi() {
		return olusturmaTarihi;
	}

	public void setOlusturmaTarihi(Date olusturmaTarihi) {
		this.olusturmaTarihi = olusturmaTarihi;
	}

	public Long getOlusturanID() {
		return olusturanID;
	}

	public void setOlusturanID(Long olusturanID) {
		this.olusturanID = olusturanID;
	}
	
	
	
	
}
