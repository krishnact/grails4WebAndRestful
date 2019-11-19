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
                    var query = window.atob('${value.influxQuery().encodeAsBase64()}')
                    console.log(query);
                    var panelDef = window.atob('${value.panelDefJson.encodeAsBase64()}');
                    //console.log(panelDef);
                    var panel = JSON.parse(panelDef)
                    let myPlot = new Plot(panel.plotType);
                    let data = [];
                    myPlot['loadData_'+panel.plotType](panel.columns, query, data,
                        function(){
                            const layout = panel.layout
                            layout.xaxis.rangeselector = myPlot.getPeriods();
                            /**
                             For reference please look here
                             https://plot.ly/javascript/reference/#scatter-type

                             */
                            let plot = Plotly.newPlot('${value.id}', data, layout, {responsive: true, displaylogo: false});
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

