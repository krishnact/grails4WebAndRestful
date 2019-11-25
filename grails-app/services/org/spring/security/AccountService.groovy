package org.spring.security

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Service(Account)
@Transactional
interface AccountService {

    Account get(Serializable id)

    List<Account> list(Map args)

    Long count()

    void delete(Serializable id)

    Account save(Account account)

}