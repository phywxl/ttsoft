package ttsoft.osgi.test.resteasy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name = "contact")
public class Contact {
	private Long id;
	private String name;
	private String email;
	private String telephone;
	@XmlTransient
	private List<Contact> contactChildren;

	public Contact() {
		this.contactChildren = new ArrayList<Contact>();
	}

	public Contact get() {
		return this;
	}

	@XmlAttribute(name = "contactId")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Contact> getContactChildren() {
		return contactChildren;
	}

	public void setContactChildren(List<Contact> contactChildren) {
		this.contactChildren = contactChildren;
	}

	@XmlTransient
	public Contacts getContacts() {
		Contacts contacts = new Contacts();
		contacts.setContacts(getContactChildren());
		return contacts;
	}

}
