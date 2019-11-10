package base

import com.br.asgardtecnologia.base.Persistente

class Widget extends Persistente {

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
