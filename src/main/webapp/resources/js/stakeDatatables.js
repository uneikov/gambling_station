const ajaxUrl = 'ajax/profile/stakes/';
const ajaxHorsesUrl = 'ajax/profile/horses/names/';
const ajaxWalletsUrl = 'ajax/profile/wallets/';

function checkStatus() {
    $.get(ajaxRacesUrl + 'run', function (status) {
        $('#addButton')[0].disabled = status == 'disabled';
    });
}

function checkAddStatus() {
    $.when($.ajax(ajaxWalletsUrl + 'cash'), $.ajax(ajaxRacesUrl + 'run')).done(function (w, s) {
        var cash = w[0];
        var status = s[0];
        if (cash < 1) {
            $('#walletInfo').modal({backdrop: true});
        } else {
            if (status === 'enabled') {
                add();
            }else {
                $('#raceInfo').modal({backdrop: true});
            }
        }
    });
}

function fillWallet() {
    $.ajax({
        url: ajaxWalletsUrl + 'add',
        type: 'PUT',
        success: function () {
            successNoty('common.success');
            $('#walletInfo').modal('hide');
        }
    });
}

const moneyGlyph =
    '<div class="input-group">' +
    '<span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>' +
    '<input class="form-control" id="value" name="stakeValue" type="number" step="0.01" min="1" max="';

const horseGlyph =
    '<div class="input-group">' +
    '<span class="input-group-addon">' +
    '<i class="glyphicon glyphicon-menu-hamburger"></i></span>' +
    '<select class="form-control" id="value" name="horseName" required>' +
    '<option value="" selected="selected">Select a horse</option>';

function addModal() {
    $.when($.ajax(ajaxHorsesUrl), $.ajax(ajaxWalletsUrl + 'cash')).done(function (r1, r2) {
        var horses = r1[0];
        var available = r2[0];
        $('#value').html(moneyGlyph + available + '" value="' + available + '" required>' + '</div>');
        $('#horse').html(makeDropDown(horses, horseGlyph, "", '</div>'));
        $('#editRow').modal({backdrop: true});
    });
}

function updateModal(id) {
    $.when($.ajax(ajaxHorsesUrl), $.ajax(ajaxUrl + id), $.ajax(ajaxWalletsUrl + 'cash')).done(function (r1, r2, r3) {
        var horses = r1[0];
        var stake = r2[0];
        var available = r3[0];
        var current = stake.stakeValue;
        var updated = current + available;
        $('#value').html(moneyGlyph + updated + '" value="' + current + '">');
        $('#horse').html(makeDropDown(horses, horseGlyph, stake.horse.name, "", '</div>'));
        $('#editRow').modal({backdrop: true});
    });
}

function makeDropDown(horses, head, selected, tail) {
    horses.forEach(function (name) {
        var select = name === selected ? ' selected="selected"' : '';
        head += '<option value="' + name + '"' + select + '>' + name + '</option>';
    });
    return head + tail;
}

function checkForm() {
    var formGroup = $('.form-group.has-feedback');
    var glyphicon = formGroup.find('.form-control-feedback');
    var formValid = true;
    [].forEach.call(form.find(":input#value"), function (value, i) {
        if (value.checkValidity()) {
            $(formGroup[i]).addClass('has-success').removeClass('has-error');
            $(glyphicon[i]).addClass('glyphicon-ok').removeClass('glyphicon-remove');
        } else {
            $(formGroup[i]).addClass('has-error').removeClass('has-success');
            $(glyphicon[i]).addClass('glyphicon-remove').removeClass('glyphicon-ok');
            formValid = false;
        }
    });
    if (formValid) save();
}

function updateTable() {
    //http://api.jquery.com/serialize/... see Note:
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: updateTableByData
    });
}

var datatableApi;

$(function () {
    datatableApi = $("#stakestable").DataTable({
        ajax: {
            url: ajaxUrl,
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
                data: "dateTime",
                render: function (date, type, row) {
                    if (type == 'display') {
                        return date.replace('T', ' ').substr(0, 16);
                    }
                    return date;
                }
            },
            {
                data: "stakeValue"
            },
            {
                data: "horse.name"
            },
            {
                data: "horse.wins"
            },
            {
                data: "amount",
                render: $.fn.dataTable.render.number(' ', '.', 2)
            },
            {
                data: "editable",
                orderable: false,
                defaultContent: "",
                render: function (data, type, row) {
                    if (type == 'display') {
                        if (!data) {
                            return '<span class="glyphicon glyphicon-lock" style="color: black"/>';
                        } else {
                            return renderEditBtn(data, type, row);
                        }
                    }
                }
            },
            {
                data: "editable",
                orderable: false,
                defaultContent: "",
                render: function (data, type, row) {
                    if (type == 'display') {
                        if (!data) {
                            return '<span class="glyphicon glyphicon-lock" style="color: black"/>';
                        } else {
                            return renderDeleteBtn(data, type, row);
                        }
                    }
                }
            }
        ],
        order: [[0, 'desc']],
        createdRow: function (row, data) {
            if (!data.editable) {
                $(row).css("opacity", 0.5);
            }
            $(row).addClass(data.wins ? 'success' : 'failure');
        },
        initComplete: makeEditable
    });

    $.datetimepicker.setLocale(localeCode);

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
    });
});