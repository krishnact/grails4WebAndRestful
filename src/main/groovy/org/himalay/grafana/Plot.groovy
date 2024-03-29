package org.himalay.grafana

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.text.GStringTemplateEngine
import groovy.text.SimpleTemplateEngine
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * This class represents a Plotly plot created by using a mock and data.
 */
class Plot {
    public static Logger logger = LoggerFactory.getLogger(Plot.class)
    private Map panelDef_
    private Map plotlyDef;
    String header = 'Overwrite "header" in plot json';
    String footer = "Created on ${new Date()}";
    String id   = UUID.randomUUID().toString().replaceAll('-','')
    String dbServerURL = 'http://127.0.0.1:8086';
    String plotName = null;
    String serverBase
    String plotlyEditor
    /**
     *
     * @param myPlotDefFile The JSON defining plot columns and SQL
     * @param mocksFolder The folder that contains panel definitions.
     * @param plotsCacheFolder The folder that contains panel cache.
     */
    Plot(File myPlotDefFile, File mocksFolder, File plotsCacheFolder){
        plotName = myPlotDefFile.name;
        JsonSlurper jsl = new JsonSlurper();
        panelDef_ =jsl.parse(myPlotDefFile)
        if ( panelDef_.header != null){
            header = panelDef_.header
        }

        def defaultValues = ['title':'','anotherDefaultProperty':''];
        defaultValues.each{String key, Object value->
            if (panelDef_[key] == null){
                panelDef_[key] = value;
            }
        }

        // Read the mock file
        File myMock = new File(plotsCacheFolder, myPlotDefFile.name)
        PlotlyChart pc = null;
        if (myMock.exists()){
            pc = new PlotlyChart(myMock );
        }else{
            pc = new PlotlyChart(new File(mocksFolder, panelDef_.baseMock) );
        }


        String plotlyDefAsJson = pc.chartDefAsJSON();
        GStringTemplateEngine ste = new GStringTemplateEngine();
        //plotlyDefAsJson = ste.createTemplate(plotlyDefAsJson).make([plot: this, panel: panelDef_])
        this.plotlyDef = jsl.parseText(plotlyDefAsJson)

    }


    Map defaultQueryCriteria(){
        return [startTime : 'now() - 1h', endTime : "now()", interval : '60s']
    }

    String influxQuery(){
        return influxQuery(defaultQueryCriteria())
    }
    /**
     *
     * @param timeRange
     * @param interval
     * @return
     */
    String influxQuery(Map<String, Object> bindings){
        SimpleTemplateEngine ste = new SimpleTemplateEngine()
        bindings['__timeRange'] = "time >= ${bindings.startTime} and time < ${bindings.endTime}"
        bindings['__interval']  = "time(${bindings.interval})"
        bindings['dashboardTime'] = bindings.startTime
        bindings['interval'] = bindings.interval
        String selectQuery = panelDef_.selectQuery;
        if (selectQuery == null) {

            String columns = panelDef_.columns.collect { key, colDef ->
                "${colDef.select} as \"${key}\""
            }.join(', ');
            String from = panelDef_.from.split('\\.').collect { "\"${it}\"" }.join('.')
            String where = panelDef_.where
            String groupBy = panelDef_.groupBy.join(', ')
            String fill = panelDef_.fill
            selectQuery = ste.createTemplate("Select ${columns} from ${from} where ${where} group By ${groupBy} ${fill}").make(bindings)
        }else{
            selectQuery = selectQuery.replaceAll(/:([\w_]+):/,'\\${$1}')
            selectQuery = ste.createTemplate(selectQuery).make(bindings);
        }

        return selectQuery;
    }


    void findLayout() {

    }
    void findLayout_() {

            SimpleTemplateEngine ste = new SimpleTemplateEngine();
            layoutJson = ste.createTemplate(layoutJson).make([plot: this, panel: panelDef_])
            panelDef_.layout = jsl.parseText(layoutJson)

    }

    void findLayout_old() {
        if (panelDef_.layout.toString().startsWith('file://')) {
            JsonSlurper jsl = new JsonSlurper();
            String fileName = panelDef_.layout.toString().substring(7);
            File templateFile = new File(this.jsonDefFile.parentFile, fileName);
            String regex = /\n(\s)+layout:[^\n]+/
            String layoutJson = templateFile.text.find(regex)

            layoutJson = layoutJson.replaceFirst(/(\s)+layout:/, '')
            SimpleTemplateEngine ste = new SimpleTemplateEngine();
            layoutJson = ste.createTemplate(layoutJson).make([plot: this, panel: panelDef_])
            panelDef_.layout = jsl.parseText(layoutJson)
        }
    }

    Map getPanelDef(){
        findLayout();
        return this.panelDef_
    }

    String getPanelDefJson(){
        findLayout();
        return new JsonBuilder(this.panelDef_).toPrettyString();
    }

    String getPlotelyDefJson(){
        String retVal = new JsonBuilder(this.plotlyDef).toPrettyString();
        return retVal;
    }
}
