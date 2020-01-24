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
}
