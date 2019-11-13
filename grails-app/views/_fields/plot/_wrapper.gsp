<%@ page import="groovy.json.JsonBuilder" %>
<!-- This is used for editing. For show _widget.gsp is used -->

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">${value.header}</h6>
    </div>
    <div class="card-body">
        <div id="${value.id}_">
            <div id="${value.id}">
            </div>
            <script type="text/javascript">
                function launch_${value.id}(){
                    var selectorOptions = {
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
                    var query = window.atob('${value.influxQuery().encodeAsBase64()}')
                    console.log(query);
                    var panelDef = window.atob('${new groovy.json.JsonBuilder(value.panelDef).toPrettyString().encodeAsBase64()}');
                    console.log(panelDef);
                    var panel = JSON.parse(panelDef)

                    const loadData_${value.id} = () => {
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
                        let data = [];
                        parsedResponse.results.forEach(function(oneResult){
                                if ( oneResult.series ){
                                    oneResult.series.forEach(function(oneSeries){
                                            let timeData = oneSeries.values.map(obj => new Date(obj[0]))
                                            for (var idx=1; idx < oneSeries.columns.length; idx++){
                                                let valueData= oneSeries.values.map(obj => obj[idx])
                                                let colmn = panel.columns[oneSeries.columns[idx]]
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



                        const layout = panel.layout
                        layout.xaxis.rangeselector = selectorOptions
                        /**
                         For reference please look here
                         https://plot.ly/javascript/reference/#scatter-type

                         */
                        let plot = Plotly.newPlot('${value.id}', data, layout, {responsive: true, displaylogo: false});
                        return plot;
                    })
                    .catch( error => console.log(error) );
                    }
                    $(window).on('load',function(){ setTimeout(1000,loadData_${value.id}())});

                }
                setTimeout(launch_${value.id},100)

            </script>
        </div>
        <hr>
        ${value.footer}
    </div>
</div>

