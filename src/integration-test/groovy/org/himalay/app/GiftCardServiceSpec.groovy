package org.himalay.app

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class GiftCardServiceSpec extends Specification {

    GiftCardService giftCardService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new GiftCard(...).save(flush: true, failOnError: true)
        //new GiftCard(...).save(flush: true, failOnError: true)
        //GiftCard giftCard = new GiftCard(...).save(flush: true, failOnError: true)
        //new GiftCard(...).save(flush: true, failOnError: true)
        //new GiftCard(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //giftCard.id
    }

    void "test get"() {
        setupData()

        expect:
        giftCardService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<GiftCard> giftCardList = giftCardService.list(max: 2, offset: 2)

        then:
        giftCardList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        giftCardService.count() == 5
    }

    void "test delete"() {
        Long giftCardId = setupData()

        expect:
        giftCardService.count() == 5

        when:
        giftCardService.delete(giftCardId)
        sessionFactory.currentSession.flush()

        then:
        giftCardService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        GiftCard giftCard = new GiftCard()
        giftCardService.save(giftCard)

        then:
        giftCard.id != null
    }
}
