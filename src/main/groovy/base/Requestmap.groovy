package base

import org.springframework.http.HttpMethod

class Requestmap {

    String url
    String configAttribute
    HttpMethod httpMethod

    static mapping = {
        cache true
        url index: 'url_Idx'
    }

    static constraints = {
        url blank: false, size: 0..100
        configAttribute blank: false
        httpMethod nullable: true, size: 0..10
    }
}
