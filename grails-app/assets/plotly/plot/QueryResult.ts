/**
 * This class is not working yet.
 */
class QueryResult {
    queryResult: any

    constructor(queryResult: any) {
        this.queryResult = queryResult
    }

    forEachRow(callBack : Function){

    }

    extractTimeSerieses(timeSeries: Array<TimeSeriesDef>){

    }
}


class TimeSeriesDef{
    momentColumnName: string
    valueColumnName : string
    values : Array<any>
    moments: Array<Date>
}