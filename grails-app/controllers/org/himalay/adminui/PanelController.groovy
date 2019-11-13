package org.himalay.adminui

import groovy.json.JsonBuilder
import org.himalay.grafana.Plot

class PanelController {

    def index() { }

    def panelScript(String id){
        Plot panel = new Plot("conf/resources/panels/${id}.json")
        render(view:"panelScript",  model: [panel:panel.panelDef, panelJSON: new JsonBuilder(panel.panelDef).toPrettyString().encodeAsBase64(), influxQuery: panel.influxQuery().encodeAsBase64()])
    }
}
