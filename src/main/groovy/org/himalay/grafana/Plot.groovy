package org.himalay.grafana

import groovy.json.JsonSlurper
import groovy.text.SimpleTemplateEngine

class Plot {
    Map panelDef
    String header = 'No name';
    String footer = "Created on ${new Date()}";
    String id   = UUID.randomUUID().toString().replaceAll('-','')
    String dbServerURL = 'http://127.0.0.1:8086';
    Plot(String panelDefJsFilePath){
        panelDef = new JsonSlurper().parse(new File(panelDefJsFilePath))
    }

    /**
     *
     * @param timeRange
     * @param interval
     * @return
     */
    String influxQuery(String timeRange = 'time >= now() - 1h', String interval = 'time(60s)'){
        SimpleTemplateEngine ste = new SimpleTemplateEngine()
        Map bindings = [__timeRange: timeRange, __interval: interval]

        String selectQuery = panelDef.selectQuery;
        if (selectQuery == null) {

            String columns = panelDef.columns.collect { key, colDef ->
                "${colDef.select} as \"${key}\""
            }.join(', ');
            String from = panelDef.from.split('\\.').collect { "\"${it}\"" }.join('.')
            String where = panelDef.where
            String groupBy = panelDef.groupBy.join(', ')
            String fill = panelDef.fill
            selectQuery = ste.createTemplate("Select ${columns} from ${from} where ${where} group By ${groupBy} ${fill}").make(bindings)
        }else{
            selectQuery = ste.createTemplate(selectQuery).make(bindings);
        }

        return selectQuery;
    }

}
