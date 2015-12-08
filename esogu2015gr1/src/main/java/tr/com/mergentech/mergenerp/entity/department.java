package tr.com.mergentech.mergenerp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tr.com.mergentech.mergenbase.entity.BaseEntity;


@Entity(name = "departman")
public class department extends BaseEntity {
	private static final long serialVersionUID = -4131017637756081291L;
	@Column(name = "ID", nullable = false)
	private Long ID;
	
	@Column(name = "deptAdi")
	private String deptAdi;
	@Column(name = "aktifPasif")
	private boolean aktifPasif;
	
	@Column(name = "olusturanID")
	private Long olusturanID;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "olusturmaTar")
	private Date olusturmaTar;
	
	@Column(name = "guncellemeID")
	private Long guncellemeID;


	@Temporal(TemporalType.DATE)
	@Column(name = "guncellemeTar")
	private Date guncellemeTar;
	
}
