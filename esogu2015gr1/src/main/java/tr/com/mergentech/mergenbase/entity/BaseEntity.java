package tr.com.mergentech.mergenbase.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity implements Serializable{

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	@Column(name = "olusturanId", nullable = false)
	protected Long olusturanId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "olusturmaTarih", nullable = false)
	protected Date olusturmaTarih;

	@Column(name = "olusturanId", nullable = false)
	protected Long guncelleyenId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "guncellemeTarih", nullable = false)
	protected Date guncellemeTarih;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOlusturanId() {
		return olusturanId;
	}

	public void setOlusturanId(Long olusturanId) {
		this.olusturanId = olusturanId;
	}

	public Date getOlusturmaTarih() {
		return olusturmaTarih;
	}

	public void setOlusturmaTarih(Date olusturmaTarih) {
		this.olusturmaTarih = olusturmaTarih;
	}

	public Long getGuncelleyenId() {
		return guncelleyenId;
	}

	public void setGuncelleyenId(Long guncelleyenId) {
		this.guncelleyenId = guncelleyenId;
	}

	public Date getGuncellemeTarih() {
		return guncellemeTarih;
	}

	public void setGuncellemeTarih(Date guncellemeTarih) {
		this.guncellemeTarih = guncellemeTarih;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
