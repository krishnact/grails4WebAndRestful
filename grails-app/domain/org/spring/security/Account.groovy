package org.spring.security

import org.himalay.app.Book
import org.himalay.app.GiftCard


class Account {
    public static Account SEED_ACCOUNT
    String accountId;

    static hasMany = [users: User, books: Book, giftCards: GiftCard]

    static constraints = {
        id column: 'accountId'
        accountId blank: false, unique: true
    }

    static Account createANewAccount(){
        Account retVal = new Account();
        retVal.assignId();
        return retVal;
    }
    static mapping = {
        version false
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Account account = (Account) o

        if (accountId != account.accountId) return false

        return true
    }

    int hashCode() {
        return accountId.hashCode()
    }

    private void assignId(){
        if (this.accountId == null){
            Thread.sleep(20)
            this.accountId = 'AA_' + System.currentTimeMillis()
        }
    }
}
