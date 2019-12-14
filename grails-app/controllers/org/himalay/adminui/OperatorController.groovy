package org.himalay.adminui


import org.himalay.grafana.Bean
import org.himalay.grafana.Plot
import org.springframework.beans.factory.annotation.Value

import javax.servlet.http.HttpServletRequest

class OperatorController {
    @Value('${app.plotlyPlotsCache}')
    private String plotlyPlotsCache;
    @Value('${app.plotlyPlotsMocks}')
    private String plotlyPlotsMocks;
    @Value('${app.plotlyEditor}')
    private String plotlyEditor;

    def index() { }

    def dashboard(){
        //String id = 'Panel1'
        File plots = new File('conf/resources/myPlotDefinitions')
        File plotlyPlotsMocksFolder = new File(plotlyPlotsMocks)
        File plotlyPlotsCacheFolder = new File(plotlyPlotsCache)
        String serverBase = this.getServerBaseURL(request);
        def files = plots.listFiles().findAll{
            it.name.endsWith('.json') //and it.name.startsWith('D')
        }
        def panels = files.collectEntries{
            Plot plot = new Plot(it,plotlyPlotsMocksFolder,plotlyPlotsCacheFolder)
            plot.serverBase = serverBase;
            plot.plotlyEditor = plotlyEditor;
            [it.name, new Bean<Plot>(plot)]
        }


        respond "dashboard", model: [
                panels: panels,
                serverBase: serverBase,
                plotlyEditor: plotlyEditor
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

    private String getServerBaseURL(HttpServletRequest request){
        return (request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath())
    }
}


