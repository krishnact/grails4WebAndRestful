package org.himalay.adminui

import org.himalay.grafana.Bean
import org.himalay.grafana.Plot

class OperatorController {

    def index() { }

    def dashboard(){
        //String id = 'Panel1'
        List<Bean> panels = (1..2).collect { int id->
            String panelFileName = "Panel${id}"
            return new Bean<Plot>(new Plot("conf/resources/panels/${panelFileName}.json"));
        }
        respond "dashboard", model: [
                                panels: panels,
                                //panelJSON: new JsonBuilder(panel.panelDef).toPrettyString().encodeAsBase64(),
                                //influxQuery: panel.influxQuery().encodeAsBase64()
                        ]


        //respond 'dashboard', model:[:]
    }
}


