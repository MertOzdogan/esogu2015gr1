package tr.com.mergentech.mergenerp.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tr.com.mergentech.mergenbase.entity.BaseEntity;

@Entity(name = "calisanlar")
public class User extends BaseEntity{
	
	private static final long serialVersionUID = -4131017637756081291L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long ID;
	
	@Column(name = "kimlikNo", nullable = false)
	private Long kimlikNo;
	
	@Column(name = "adi")
	private String adi;
	
	@Column(name = "soyadi")
	private String soyadi;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dogumTarihi")
	private Date dogumTarihi;

	@Column(name = "adres")
	private String adres;
	
	@Column(name = "telefon_is")
	private String telefonIs;
	
	@Column(name = "telefon_kisisel")
	private String telefonKisisel;
	
	@Column(name = "eposta_is")
	private String epostaIs;
	
	@Column(name = "eposta_kisisel")
	private String epostaKisisel;
	
	@Column(name = "yonetici_flag")
	private Boolean yoneticiFlag;
	
	private Set<pType> permissionTypes;
	
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="izin", joinColumns={@JoinColumn(name="EMPLOYEE_ID", referencedColumnName="ID")} , inverseJoinColumns={@JoinColumn(name="permID", referencedColumnName="ID")})
    
	
	
	
	
	public Long getKimlikNo() {
		return kimlikNo;
	}
	public Set<pType> getPermissionTypes() {
		return permissionTypes;
	}
	public void setPermissionTypes(Set<pType> permissionTypes) {
		this.permissionTypes = permissionTypes;
	}
	public void setKimlikNo(Long kimlikNo) {
		this.kimlikNo = kimlikNo;
	}
	public String getAdi() {
		return adi;
	}
	public void setAdi(String adi) {
		this.adi = adi;
	}
	public String getSoyadi() {
		return soyadi;
	}
	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}
	public Date getDogumTarihi() {
		return dogumTarihi;
	}
	public void setDogumTarihi(Date dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getTelefonIs() {
		return telefonIs;
	}
	public void setTelefonIs(String telefonIs) {
		this.telefonIs = telefonIs;
	}
	public String getTelefonKisisel() {
		return telefonKisisel;
	}
	public void setTelefonKisisel(String telefonKisisel) {
		this.telefonKisisel = telefonKisisel;
	}
	public String getEpostaIs() {
		return epostaIs;
	}
	public void setEpostaIs(String epostaIs) {
		this.epostaIs = epostaIs;
	}
	public String getEpostaKisisel() {
		return epostaKisisel;
	}
	public void setEpostaKisisel(String epostaKisisel) {
		this.epostaKisisel = epostaKisisel;
	}
	public Boolean getYoneticiFlag() {
		return yoneticiFlag;
	}
	public void setYoneticiFlag(Boolean yoneticiFlag) {
		this.yoneticiFlag = yoneticiFlag;
	}
	
	
	
}
