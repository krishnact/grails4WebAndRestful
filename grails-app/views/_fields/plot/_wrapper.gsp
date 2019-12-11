<%@ page import="groovy.json.JsonBuilder" %>
<!-- This is used for editing. For show _widget.gsp is used -->

<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">
            ${value.header}
            <button type="button" class="btn btn-block btn-default" onclick="editThisPlot_${value.id}()">Edit this plot</button>
            <script>
                function editThisPlot_${value.id}(){
                    let data =  window['plotly_data_${value.id}'];
                    // Submit this data for saving

                    // Now open a new tab with React Plot Editor


                }
            </script>
        </h6>
    </div>
    <div class="card-body">
        <div id="${value.id}_">
            <div id="${value.id}">
            </div>
            <script type="text/javascript">
                function launch_${value.id}(){
                    var query = window.atob('${value.influxQuery().encodeAsBase64()}')
                    console.log(query);
                    var panelDef = window.atob('${value.panelDefJson.encodeAsBase64()}');
                    //console.log(panelDef);
                    var panel = JSON.parse(panelDef)
                    var plotlyPlotDef = JSON.parse(window.atob('${value.getPlotelyDefJson().encodeAsBase64()}'));
                    let myPlot = new Plot(plotlyPlotDef);
                    //myPlot.setDatabase('${value.dbServerURL}', 'telegraf')
                    myPlot.setDatabase('http://192.168.11.207:8086', 'telegraf')
                    let data = [];
                    //myPlot['loadData_'+panel.plotType](panel.columns, query, data,
                    myPlot['loadData'](plotlyPlotDef, panel, query, data,
                        function(){
                            const layout = panel.layout
                            //layout.xaxis.rang eselector = myPlot.getPeriods();
                            /**
                             For reference please look here
                             https://plot.ly/javascript/reference/#scatter-type

                             */
                            %{--let plot = Plotly.newPlot('${value.id}', {--}%
                            %{--    data: plotlyPlotDef.data,--}%
                            %{--    layout: plotlyPlotDef.layout,--}%
                            %{--    frames: [],--}%
                            %{--    config:{responsive: true, displaylogo: false}--}%
                            %{--});--}%

                            let plot = Plotly.newPlot('${value.id}',plotlyPlotDef);//,{responsive: true, displaylogo: false});
                            window['plotly_data_${value.id}'] = data

                        }
                    );
                   // $(window).on('load',function(){ setTimeout(1000,loadData_${value.id}())});

                }

                window.addEventListener('load', function(){ setTimeout(1000,launch_${value.id}())});


            </script>
        </div>
        <hr>
        ${value.footer}
    </div>
</div>

