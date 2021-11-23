function drawPieChartTest1() {
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'test');
        data.addColumn('number', 'numb');
        
        data.addRows([
            ['SUCCESS', 0],
            ['UNSTABLE', 4],
            ['FAILURE', 1]
          ]);

        var options = {
            title: 'Build: com.seeburger.portal.test.b2b-routing-pipeline-test',
            pieSliceText: 'value',
            theme: 'material',
            colors: ['#35bd04', '#ffd240', '#cc040b']
        };

        var chart = new google.visualization.PieChart(document.getElementById('pieChart1'));

        chart.draw(data, options);
    }
}