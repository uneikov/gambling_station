const ajaxUrl = 'ajax/admin/races/';

var datatableApi;
var locale = $('#locale').val();

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#racestable").DataTable({
        ajax: {
            url: ajaxUrl + 'with',
            dataSrc: ""
        },
        paging: true,
        info: false,
        width: true,
        "language": {
            "search": i18n["common.search"]
        },
        columns: [
            {
                data: "start",
                render: function (date, type, row) {
                    return type === 'display' ? date.replace('T', ' ').substr(0, 16) : date;
                }
            },
            {
                data: "finish",
                defaultContent: "",
                render: function (date, type, row) {
                    return type === 'display' && date != undefined ? date.replace('T', ' ').substr(0, 16) : date;
                }
            },
            {
                data: "horses",
                render: function (data, type, row) {
                    var split = data.split(',');
                    var horses = split.reduce(function (prev, curr) {
                        return locale == 'en' ? prev.concat(curr.split(':')[0]) : prev.concat(curr.split(':')[1]);
                    }, []);
                    var view = horses.reduce(function (prev, curr) {
                        return prev + '<option value="' + curr + '">' + curr + '</option>'
                    }, '<option value="" selected="selected">---</horses></option>');
                    return type === 'display' ? '<div><select>' + view + '</select></div>' : data;
                }
            },
            {
                data: "winning",
                render: function ( data, type, row ) {
                    var split = data.split(':');
                    var horse = locale=='en' ? split[0] : split[1];
                    return type == 'display' ? horse : data;
                }
            },
            {
                data: "stakes",
                render: function ( data, type, row ) {
                    return type === 'display' ? data.length : data;
                }
            },
            {
                data: "stakes",
                render: function ( data, type, row ) {
                    var totalStakesValue = data.reduce(function(prev, curr) {
                        return prev + curr.stakeValue;
                    },0.0);
                    return type === 'display' ? totalStakesValue.toFixed(2) : data;
                }
            },
            {
                data: "stakes",
                render: function ( data, type, row ) {
                    var totalWins = data.reduce(function(prev, curr) {
                        return prev + curr.wins;
                    },0);
                    return type === 'display' ? totalWins : data;
                }
            },
            {
                data: "stakes",
                render: function ( data, type, row ) {
                    var totalAmount = data.reduce(function(prev, curr) {
                        return prev + curr.amount;
                    },0.0);
                    return type === 'display' ? totalAmount.toFixed(2) : data;
                }
            }
        ],
        order: [[0, 'desc']],
        initComplete: makeEditable
    });

    /*$.datetimepicker.setLocale(localeCode);

    var startDate = $('#startDate');
    var endDate = $('#endDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                maxDate: endDate.val() ? endDate.val() : false
            })
        }
    });
    endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                minDate: startDate.val() ? startDate.val() : false
            })
        }
    });

    $('#startTime, #endTime').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });*/
});