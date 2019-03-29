/*
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@TableGenerator(name = "colSeq", allocationSize = 1000)
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "colSeq")
	private Integer id;

	private String firstName;
	private String middleName;
	private String lastName;

	@Embedded
	private Address address;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> phones;
	@ElementCollection(fetch = FetchType.EAGER)
	private Collection<String> emails;
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> nickNames;

	@Temporal(TemporalType.DATE)
	private Date birthDate;
	@Temporal(TemporalType.DATE)
	private Date joinDate;
	@Temporal(TemporalType.DATE)
	private Date lastLoginDate;

	@Basic
	private int loginCount;

	// Constructors:

	public Person() {
		firstName = Randomizer.randomFirstName();
		middleName = Randomizer.randomMiddleName();
		lastName = Randomizer.randomLastName();

		phones = new ArrayList<String>(4);
		for (int i = Randomizer.randomInt(1, 3); i-- > 0;) {
			phones.add(Randomizer.randomPhone());
		}
		emails = new ArrayList<String>(4);
		for (int i = Randomizer.randomInt(1, 3); i-- > 0;) {
			emails.add(Randomizer.randomEmail());
		}
		nickNames = new HashSet<String>(4);
		for (int i = Randomizer.randomInt(1, 3); i-- > 0;) {
			nickNames.add(Randomizer.randomFirstName());
		}
		Date[] dates = Randomizer.randomDates(3);
		birthDate = dates[0];
		joinDate = dates[1];
		lastLoginDate = dates[2];
		loginCount = Randomizer.randomInt(1, 100);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(64);
		sb.append(firstName);
		if (middleName != null) {
			sb.append(' ').append(middleName);
		}
		sb.append(' ').append(lastName);
		return sb.toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public Collection<String> getEmails() {
		return emails;
	}

	public void setEmails(Collection<String> emails) {
		this.emails = emails;
	}

	public Set<String> getNickNames() {
		return nickNames;
	}

	public void setNickNames(Set<String> nickNames) {
		this.nickNames = nickNames;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

}
