package tr.com.mergentech.mergenerp.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import tr.com.mergentech.mergenbase.entity.BaseEntity;
@Entity(name = "izinTipi")
public class pType extends BaseEntity {
	private static final long serialVersionUID = -4131017637756081291L;
	

	
	
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long ID;
	
	
	@Column(name = "izinadi")
	private String izinadi;
	
	@Column(name = "aciklama")
	private String aciklama;
	
	
	@Column(name = "izinflag")
	private int izinflag;
	
	
	
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

	public String getIzinadi() {
		return izinadi;
	}

	public void setIzinadi(String izinadi) {
		this.izinadi = izinadi;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public int getIzinflag() {
		return izinflag;
	}

	public void setIzinflag(int izinflag) {
		this.izinflag = izinflag;
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
