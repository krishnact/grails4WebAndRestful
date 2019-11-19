package org.himalay.grafana

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.text.SimpleTemplateEngine
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Plot {
    public static Logger logger = LoggerFactory.getLogger(Plot.class)
    private Map panelDef_
    String header = 'Overwrite in plot json file';
    String footer = "Created on ${new Date()}";
    String id   = UUID.randomUUID().toString().replaceAll('-','')
    String dbServerURL = 'http://127.0.0.1:8086';
    File jsonDefFile ;
    Plot(String panelDefJsFilePath){
       this(new File(panelDefJsFilePath));
    }

    Plot(File panelDefJsFile){
        this.jsonDefFile = panelDefJsFile;
        JsonSlurper jsl = new JsonSlurper();
        panelDef_ =jsl.parse(panelDefJsFile)
        if ( panelDef_.header != null){
            header = panelDef_.header
        }

    }
    /**
     *
     * @param timeRange
     * @param interval
     * @return
     */
    String influxQuery(String timeRange = 'time >= now() - 1h', String interval = 'time(60s)', Map<String, Object> bindings= [:]){
        SimpleTemplateEngine ste = new SimpleTemplateEngine()
        bindings['__timeRange'] = timeRange
        bindings['__interval']  = interval

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
            selectQuery = ste.createTemplate(selectQuery).make(bindings);
        }

        return selectQuery;
    }



    void findLayout() {
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

}
