var ajaxUrl = 'ajax/admin/horses/';

var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

function checkEditable(checkbox, id) {
    $.get(ajaxRacesUrl + 'can', function (editable) {
        if (editable=='editable') {
            enable(checkbox, id);
        }else {
            notEditableNoty('common.not_editable');
            updateTable();
        }
    });
}

$(function () {
    datatableApi = $('#horsestable').DataTable({
        ajax: {
            url: ajaxUrl,
            dataSrc: ""
        },
        paging: true,
        info: true,
        width: true,
        "language": {
            "search": i18n["common.search"]
        },
        columns: [
            {
                data: "name"
            },
            {
                data: "ruName"
            },
            {
                data: "age"
            },
            {
                data: "wins"
            },
            {
                data: "ready",
                render: function (data, type, row) {
                    if (type === 'display') {
                        return '<input type="checkbox" ' + (data ? 'checked' : '') +
                            ' onclick="checkEditable($(this),' + row.id + ');"/>';
                    }
                    return data;
                }
            },
            {orderable: false, defaultContent: "", render: renderEditBtn},
            {orderable: false, defaultContent: "", render: renderDeleteBtn}
        ],
        order: [
            [
                0,
                "asc"
            ]
        ],
        createdRow: function (row, data, dataIndex) {
            if (!data.ready) {
                $(row).css("opacity", 0.5);
            }
        },
        initComplete: makeEditable
    });
});