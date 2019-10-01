package example.grails

import grails.gorm.services.Service
import org.himalay.app.Book

//
//ervice(Announcement)
interface GormService {
    Book save(String message)
}
