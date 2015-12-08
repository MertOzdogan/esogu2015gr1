package tr.com.mergentech.mergenerp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class rank {
	@Column(name = "ID", nullable = false)
	private Long ID;
	
	@Column(name = "unvanAdi")
	private String adi;
	
	@Column(name = "aktifPasif")
	private boolean aktifPasif;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "olusturmaTarih")
	private Date olusturmaTarih;
	
	@Column(name = "olusturanID")
	private Long olusturanID;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "guncellemeTarih")
	private Date guncellemeTarih;
	
	@Column(name = "guncellemeID")
	private Long guncellemeID;
}
