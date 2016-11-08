var ajaxUrl = 'ajax/profile/stakes/';
var ajaxHorsesUrl ='ajax/horses/names/';
var restUserUrl ='rest/profile/';
var datatableApi;

function rowColor(row) {
    return row.wins ? 'winned' : 'loosed';
}

function updateTable() {
    //debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: updateTableByData
    });
}

$(function () {
    datatableApi = $("#stakestable").DataTable({
        ajax: {
            url: ajaxUrl,
            dataSrc: ""
        },
        paging: true,
        info: false,
        columns: [
            {
                data: "dateTime",
                render: function (data, type, row) {
                    if (type == 'display') {
                        return '<span class="' + rowColor(row) +
                            '">'+data.substring(0,10)+ "  " + data.substring(11,16)+'</span>';
                    }
                    return data;
                }
            },
            {
                data: "stakeValue",
                render:  function (data, type, row) {
                    if (type == 'display') {
                        return '<span class="' + rowColor(row) + '">'+data+'</span>';
                    }
                    return data;
                }
            },
            {
                data: "horse.name",
                render:  function (data, type, row) {
                    if (type == 'display') {
                        return '<span class="' + rowColor(row) + '">'+data+'</span>';
                    }
                    return data;
                }
            },
            {
                data: "horse.wins",
                render:  function (data, type, row) {
                    if (type == 'display') {
                        return '<span class="' + rowColor(row) + '">'+data+'</span>';
                    }
                    return data;
                }
            },
            {
                data: "amount",
                render:  function (data, type, row) {
                    if (type == 'display') {
                        return '<span class="' + rowColor(row) + '">'+data+'</span>';
                    }
                    return data;
                }
            },
            { defaultContent: "Edit", orderable: false,  render: renderEditBtn},
            { defaultContent: "Delete", orderable: false, render: renderDeleteBtn}
        ],
        order: [[0, 'desc']],
        initComplete: makeEditable
    });
});