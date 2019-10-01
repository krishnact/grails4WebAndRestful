package org.himalay.app

import io.swagger.annotations.ApiModelProperty

class GiftCard {

    @ApiModelProperty(position = 1, required = true, value = "Gift Card Issuer. Required")
    String issuer

    @ApiModelProperty(position = 1, required = true, value = "Gift Card Issuer Number. Required")
    String cardNumber

    @ApiModelProperty(position = 2, required = true)
    float amount

    String author

    static constraints = {
        title blank: false
        isbn nullable: true
        author nullable: true
    }
}
