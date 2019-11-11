package org.himalay.app

import org.spring.security.User;
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class Book {

    @ApiModelProperty(position = 1, required = true, value = "title of the book, must be provided")
    String title

    @ApiModelProperty(position = 2, required = false)
    String isbn

    String author

    static belongsTo = [user: User]

    static constraints = {
        title blank: false
        isbn nullable: true
        author nullable: true
    }


}