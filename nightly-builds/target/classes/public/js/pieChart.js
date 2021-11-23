function draw(dataFromMap, i, title) {
            google.charts.load('current', {'packages':['corechart']});
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Result');
                data.addColumn('number', 'Number of Builds');
                data.addRows(dataFromMap);

//                var options = {'title':title, 'width':400, 'height':300};
                var options = {'title': title, pieSliceText: 'value',theme: 'material',colors: ['#35bd04', '#000000', '#999999','#cc110b','#ffd110']  };

                var chart = new google.visualization.PieChart(document.getElementById('chart_div' + i));
                chart.draw(data, options);

            }
        }