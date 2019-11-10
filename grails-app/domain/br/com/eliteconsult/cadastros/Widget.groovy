package br.com.eliteconsult.cadastros

class Widget {

    String name
    String template
    String title
    String icon
    Integer span

    static constraints = {
        name()
        template()
        title()
        icon()
        span()
    }

}
