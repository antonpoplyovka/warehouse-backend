var routeApiGet = Vue.resource('/api/v1/route/upcoming');
var routeApiPost = Vue.resource('/api/v1/route/');

Vue.component('route-row', {
    props: ['route'],
    template:
        '<tr><td>{{route.direction}}</td><td>{{route.arrivalTime}}</td><td>{{route.palletCount}}</td></tr>'
});
Vue.component('routs-list', {
    props: ['routs'],
    template:
        '<div>' +
        '<table class="table table-striped">' +
        '    <thead>' +
        '    <tr>' +
        '        <th scope="col">Direction</th>' +
        '        <th scope="col">Arrival time</th>' +
        '        <th scope="col">Pallet count</th>' +
        '    </tr>' +
        '    </thead>' +
        '    <tbody>' +
        '<route-row v-for="route in routs" :key="route.id" :route="route"/>' +
        '    </tbody>' +
        '</table>' +

        '</div>',
    created: function () {
        routeApiGet.get().then(result =>
            result.json().then(data =>
                data.forEach(route => this.routs.push(route)))
        )


        console.log(this.routs);
        this.routs.slice().sort(function (a, b) {
            return a.arrivalTime - b.arrivalTime;
        });
        console.log(this.routs);
    }
});
var route = new Vue({
    el: '#route',
    template: '<routs-list :routs="routs" />',
    data: {
        routs: []
    }
});
