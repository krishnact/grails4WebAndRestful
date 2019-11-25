class Plot{
    plotType: string

    constructor (plotType: string){
        this.plotType = plotType
    }
    /**
     * Database URL
     * example http://192.168.11.207:8086/query?db=telegraf&epoch=ms
     */
    dbUrl   : string

    getPeriods() {
        let selectorOptions = {
            buttons: [{
                step: 'month',
                stepmode: 'backward',
                count: 1,
                label: '1m'
            }, {
                step: 'month',
                stepmode: 'backward',
                count: 6,
                label: '6m'
            }, {
                step: 'year',
                stepmode: 'todate',
                count: 1,
                label: 'YTD'
            }, {
                step: 'year',
                stepmode: 'backward',
                count: 1,
                label: '1y'
            }, {
                step: 'all',
            }],
        };
        return selectorOptions;
    }

    setDatabase(influxURL: string, dbName: string)
    {
        this.dbUrl = influxURL+  '/query?db='+ dbName +'&epoch=ms';

    }
    loadData(plotlyPlotDef: Object, panelDef: Object, query: string, data: Array<Object>, callback : Function){
        var _this = this;
        let dataTmp = [];
        let dataArray = plotlyPlotDef['data'];
        dataArray= Object.keys(dataArray).map(key => dataArray[key]);
        dataArray.forEach(function(oneData){
            let ccType = oneData['type'];
            let func =  _this['loadData_'+ccType];
            //func.call(_this,plotlyPlotDef, columns, query, data, callback)
        }
        )
        fetch(this.dbUrl + '&q='+ query)//,{ mode: 'no-cors'})
            .then( response => {
                if (response.status !== 200) {
                    console.log(response);
                }
                return response;
            })
            .then(response => response.json())
            .then(parsedResponse => {
                    const unpackData = (arr, key) => {
                        return arr.map(obj => obj[key])
                    }
                    parsedResponse.results.forEach(function(oneResult){
                        _this.processOneResult(oneResult, plotlyPlotDef, panelDef);//, data);
                        }
                    );

                    callback();
                }
            )


    }

    processOneResult(oneResult: Object, plotlyPlotDef: Object, panelDef: Object){//}, data: Array<Object>){
        if ( oneResult['series'] ){
            let row = 0;
            oneResult['series'].forEach(function(oneSeries){
                    let timeData = oneSeries.values.map(obj => new Date(obj[0]))
                    for (var idx=1; idx < oneSeries.columns.length; idx++){
                        let colName = oneSeries.columns[idx]
                        var colVal = oneSeries.values[0][idx];
                        let trace = panelDef['traces'][colName]
                        let tmpTrace = {
                            tags: oneSeries.tags,
                            label: Mustache.render(trace.label, {tags: oneSeries.tags, trace: trace, value: colVal}),
                            value : colVal
                        }

                        let diaplayVal  = Mustache.render(trace.value, {tags: oneSeries.tags, trace: tmpTrace})
                        let data = plotlyPlotDef['data'][idx-1]
                        data['labels'][row]= tmpTrace.label
                        data['values'][row]= diaplayVal
                    }
                row++;
                }
            )
        }
    }
    loadData_pie(plotlyPlotDef: Object, columns: Object, query: string, data: Array<Object>, callback : Function){
        let dataTmp = [];
        fetch(this.dbUrl + '&q='+ query)//,{ mode: 'no-cors'})
            .then( response => {
                if (response.status !== 200) {
                    console.log(response);
                }
                return response;
            })
            .then(response => response.json())
            .then(parsedResponse => {
                    const unpackData = (arr, key) => {
                        return arr.map(obj => obj[key])
                    }
                    parsedResponse.results.forEach(function(oneResult){
                            if ( oneResult.series ){
                                oneResult.series.forEach(function(oneSeries){
                                        let timeData = oneSeries.values.map(obj => new Date(obj[0]))
                                        for (var idx=1; idx < oneSeries.columns.length; idx++){
                                            let valueData= oneSeries.values.map(obj => obj[idx])
                                            let colmn = columns['traces'][idx]
                                            let displayName = Mustache.render(colmn.displayName, {tags: oneSeries.tags, column: colmn})
                                            let seriesData = {
                                                name: displayName,
                                                x: timeData,
                                                y: valueData
                                            }
                                            dataTmp.push(seriesData)
                                        }
                                    }
                                )
                            }else{
                                dataTmp.push({
                                        type: 'scatter',
                                        mode: 'lines',
                                        name: 'No Data Found',
                                        x: [0,1],
                                        y: [null,null],
                                        line: {color: '#17BECF'}
                                    }
                                )
                            }
                        }
                    );
                    let columnDef = columns[Object.getOwnPropertyNames(columns)[ 0]];
                    let propertiesToCopy = ['mode','marker','hole']
                    let entryToPush = { };
                    propertiesToCopy.forEach(function(prop){
                        entryToPush[prop] = columnDef[prop]
                    });
                    entryToPush['labels'] = dataTmp.map(function(val){return val.name});
                    entryToPush['values'] = dataTmp.map(function(val){return val.y[0]});
                    entryToPush['type'] = this.plotType
                    data.push(entryToPush)
                    callback();
                }
            )
    }

    /**
     * Puts data in the array
     * @param columns Panel columns definition that has information about columns
     * @param query InfluxDB query
     * @param data The array to put data into
     */
    loadData_line(columns: Object, query: string, data: Array<Object>, callback : Function){

            fetch('http://192.168.11.207:8086/query?db=telegraf&epoch=ms&q='+query)//,{ mode: 'no-cors'})
                .then( response => {
                    if (response.status !== 200) {
                        console.log(response);
                    }
                    return response;
                })
                .then(response => response.json())
                .then(parsedResponse => {
                    const unpackData = (arr, key) => {
                        return arr.map(obj => obj[key])
                    }
                    parsedResponse.results.forEach(function(oneResult){
                            if ( oneResult.series ){
                                oneResult.series.forEach(function(oneSeries){
                                        let timeData = oneSeries.values.map(obj => new Date(obj[0]))
                                        for (var idx=1; idx < oneSeries.columns.length; idx++){
                                            let valueData= oneSeries.values.map(obj => obj[idx])
                                            let colmn = columns[oneSeries.columns[idx]]
                                            let displayName = Mustache.render(colmn.displayName, {tags: oneSeries.tags, column: colmn})
                                            let seriesData = {
                                                type: colmn.type,
                                                mode: colmn.mode,
                                                name: displayName,
                                                x: timeData,
                                                y: valueData
                                            }
                                            data.push(seriesData)
                                        }
                                    }
                                )
                            }else{
                                data.push({
                                        type: 'scatter',
                                        mode: 'lines',
                                        name: 'No Data Found',
                                        x: [0,1],
                                        y: [null,null],
                                        line: {color: '#17BECF'}
                                    }
                                )
                            }
                        }
                    );
                    callback();
                    }
                )
    }
}