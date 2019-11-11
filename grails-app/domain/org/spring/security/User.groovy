package org.spring.security

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic
import org.himalay.app.GiftCard

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {
	private static final long serialVersionUID = 1

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Date lastUpdate = Constants.NULL_DATE;
	Date signupDate = Constants.NULL_DATE;

	Set<SecurityRole> getAuthorities() {
		(UserSecurityRole.findAllByUser(this) as List<UserSecurityRole>)*.securityRole as Set<SecurityRole>
	}
	static belongsTo = [account: Account]
	static constraints = {
		password blank: false, password: true
		username blank: false, unique: true
		account nullable: true
	}

	def beforeInsert() {

		if (signupDate == Constants.NULL_DATE) {
			signupDate = new Date()
		}
		if (this.account == null || this.account == Account.SEED_ACCOUNT ){
			this.account = Account.createANewAccount();
			account.save(true)
		}
	}

	def beforeUpdate() {
		lastUpdate   = new Date()
	}

	public User newUserAction(){
		if (this.account == null){
			this.account = Account.createANewAccount();
			account.save(true)
		}

		return this;
	}

	static mapping = {
		password column: '`password`'
	}
}
