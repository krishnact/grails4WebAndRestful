package org.himalay.app

import grails.gorm.services.Service

@Service(GiftCard)
interface GiftCardService {

    GiftCard get(Serializable id)

    List<GiftCard> list(Map args)

    Long count()

    void delete(Serializable id)

    GiftCard save(GiftCard giftCard)

}