
<div class="fieldcontain">
    <label for="${property}">${label}</label>
    <g:datePicker name="${property}"></g:datePicker>
    <!-- TODO: The datePicker is ugly. Use bootstrap datetime picker

    https://eonasdan.github.io/bootstrap-datetimepicker/
    -->
    <!--div>
        <div class="row">
            <div class='col-sm-6'>
                <div class="form-group">
                    <div class='input-group date' id="${property}_">
                        <input type='text' class="form-control" id="${property}"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                $(function () {
                    $('#${property}_').datetimepicker();
                });
            </script>
        </div>
    </div-->

</div>