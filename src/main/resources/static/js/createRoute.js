var routeApiGet = Vue.resource('/api/v1/route/upcoming');
var routeApiPost = Vue.resource('/api/v1/route/');

Vue.component('route-form', {
    props: ['route'],
    data: function () {
        return {
            direction: 'warsaw',
            palletCount: 10,
            arrivalTime: '2019-06-05T12:21',
            status: 'WAIT',
            createRouteSuccessful: false
        }
    },

    template:
        '<div>' +
        '<div class="form-group row">' +
        '<div class="col-10">' +
        '<label for="inputDirection">Input direction</label>' +
        '<input class="form-control" type="text"  id="inputDirection" placeholder="inputDirection"  v-model="direction" required>' +
        '</div>' +
        '</div>' +

        '<div class="form-group row">' +
        '<div class="col-10">' +
        '<label for="inputPalletCount">Input pallet count</label>' +
        '<input type="number" class="form-control" id="inputPalletCount" v-model="palletCount" required>' +
        '</div>' +
        '</div>' +

        '<div class="form-group row">' +
        '<div class="col-10">' +
        '<label for="datetime-local">Input arrival date and time</label>' +
        '<input type="datetime-local" class="form-control" v-model="arrivalTime">' +
        '</div>' +
        '</div>' +

        '<div class="form-group row">' +
        '<div class="col-10">' +
        '<button id="createRouteButton" type="submit" class="btn btn-primary btn-sm" @click="create" >Create route</button>' +
        '</div> ' +
        '</div>' +

        '</div>'
    ,
    methods: {
        create: function () {
            var route = {
                direction: this.direction,
                palletCount: this.palletCount,
                arrivalTime: this.arrivalTime,
                status: this.status
            };

            if (this.direction.length > 0 && this.palletCount > 0 && this.palletCount < 21 && this.arrivalTime < "9999-09-09T21:09") {
                routeApiPost.save(route);
                alert("New route created")
            } else {
                alert("Failed, wrong parameters")
            }


        }

    }
});

var routeCreate = new Vue({
    el: '#routeCreate',
    template: '<route-form  />',
});

