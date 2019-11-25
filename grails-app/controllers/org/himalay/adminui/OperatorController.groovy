package org.himalay.adminui

import org.himalay.grafana.Bean
import org.himalay.grafana.Plot

class OperatorController {

    def index() { }

    def dashboard(){
        //String id = 'Panel1'
        File plots = new File('conf/resources/panels/')
//        def files = ['Doughnut.json','Panel1.json'].collect{
//            new File(plots, it)
//        }
        def files = plots.listFiles().findAll{
            it.name.endsWith('.json') //and it.name.startsWith('D')
        }
        def panels = files.collectEntries{
            [it.name, new Bean<Plot>(new Plot(it))]
        }


        respond "dashboard", model: [
                panels: panels,
                //panelJSON: new JsonBuilder(panel.panelDef).toPrettyString().encodeAsBase64(),
                //influxQuery: panel.influxQuery().encodeAsBase64()
        ]
    }

    def dashboard_good(){
        //String id = 'Panel1'
        File plots = new File('conf/resources/panels/')
        def panels = plots.listFiles().findAll{
            it.name.endsWith('.json')
        }.collect{
            new Bean<Plot>(new Plot(it))
        }


        respond "dashboard", model: [
                                panels: panels,
                                //panelJSON: new JsonBuilder(panel.panelDef).toPrettyString().encodeAsBase64(),
                                //influxQuery: panel.influxQuery().encodeAsBase64()
                        ]
    }
}


