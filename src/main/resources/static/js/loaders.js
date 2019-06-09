var loaderApi = Vue.resource('/api/v1/loader/');
var loaderApiPatch = ('/api/v1/loader');

Vue.component('loader-row', {
    props: ['loader', 'loaders'],
    data: function () {
        return {
            status: ""
        }
    },
    template:
        '<tr>' +
        '<td>{{loader.id}}</td>' +
        '<td>{{loader.status}}</td>' +
        '<td>{{loader.identityNumber}}</td>' +
        '<td>' +
        '<select  class="form-control"  v-model="status" >' +
        '<option  value="AVAILABLE" >AVAILABLE</option>' +
        '<option value="INACTIVE">INACTIVE</option>' +
        '<option value="BROKEN">BROKEN</option>' +
        '</select>' +
        '  <button class="btn-primary" @click="patch(loader.id,status)">Press to change loader Status</button>' +
        '</td>' +
        '</tr>',
    methods: {

        patch: function (loaderId, status) {
            $.ajax(loaderApiPatch + '/' + loaderId, {

                method: 'PATCH',
                data: ({
                    op: 'replace',
                    path: 'status',
                    value: status
                })
            });

            for (var i = 0; i < this.loaders.length; i++) {
                if (this.loaders[i].id === loaderId) {
                    this.loaders.splice(i, 1);
                    i--;
                }
            }

        }
    }
});
Vue.component('loaders-list', {
    data: function () {
        return {
            loaders: [],
            status: "AVAILABLE"
        }
    },
    mounted: function () {
        this.get();
    },
    template:
        '<div>' +
        '<label>Choose status of loaders</label>' +
        '<select  class="form-control"  v-model="status" @change="get()" >' +
        '<option  value="AVAILABLE" >AVAILABLE</option>' +
        '<option value="INACTIVE">INACTIVE</option>' +
        '<option value="BROKEN">BROKEN</option>' +
        '</select>' +


        '<table class="table table-striped">' +

        '<thead>' +
        '<tr>' +
        '<th scope="col">Loader id</th>' +
        '<th scope="col">Status</th>' +
        '<th scope="col">Identity number</th>' +
        '<th scope="col">Actions</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +

        '<loader-row v-for="loader in loaders" :key="loader.id" :loader="loader" :loaders="loaders"/>' +
        '</tbody>' +
        '</table>' +

        '</div>',

    methods: {
        get: function () {

            this.loaders = [];
            loaderApi.get({
                status: this.status
            }).then(result =>
                result.json().then(data =>
                    data.content.forEach(loader => this.loaders.push(loader))
                ))
        }
    }
});

var loader = new Vue({
    el: '#loader',
    template: '<loaders-list />',
    data: {
        status: "",
        loaders: [],
    }
});
