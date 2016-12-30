var ajaxUrl = 'ajax/profile/stakes/';
var ajaxHorsesUrl ='ajax/profile/horses/names/';
var ajaxWalletsUrl = 'ajax/profile/wallets/';

var datatableApi;

/*function rowColor(row) {
    return row.wins ? 'winned' : 'loosed';
}*/

function checkStatus() {
     $.get(ajaxRacesUrl + 'run', function (status) {
        $('#addButton')[0].disabled = status=='disabled';
    });
}

function checkAddStatus() {
    $.when($.ajax(ajaxWalletsUrl + 'cash'), $.ajax(ajaxRacesUrl + 'run')).done(function (w, s) {
        var cash=w[0];
        var status= s[0];
        if (cash<1) {
            $('#walletInfo').modal({backdrop: true});
        }
        if (status=='disabled') {
            alert('You can`t make stakes now. Race is running.');
            //document.getElementById("addButton").disabled = status=='disabled';
        }
        $('#addButton')[0].disabled = status=='disabled';
        if ( cash>=1 && status=='enabled') add();
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

function addModal(){
    $.when( $.ajax(ajaxHorsesUrl), $.ajax(ajaxWalletsUrl + 'cash') ).done(function( r1, r2 ) {
        var horses = r1[0];
        var available = r2[0];
        $('#value').html(
            '<div class="input-group">' +
            '<span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>' +
            '<input class="form-control" id="stakeValue" name="stakeValue" type="number" step="0.01" min="1" max="'
            + available + '" value="' + available + '" required>' +
            '</div>'
        );
        $('#horse').html
        (
            '<div class="input-group">' +
            '<span class="input-group-addon"><i class="glyphicon glyphicon-menu-hamburger"></i></span>' +
            '<select class="form-control" id="horseName" name="horseName" >' +
            '<option value="" selected="selected">Select a horse</option>' +
            '<option value="' + horses[0] + '">' + horses[0] + '</option>' +
            '<option value="' + horses[1] + '">' + horses[1] + '</option>' +
            '<option value="' + horses[2] + '">' + horses[2] + '</option>' +
            '<option value="' + horses[3] + '">' + horses[3] + '</option>' +
            '<option value="' + horses[4] + '">' + horses[4] + '</option>' +
            '<option value="' + horses[5] + '">' + horses[5] + '</option>' +
            '</select>' +
            '</div>'
        );
        //<%=(horseName==select ? selected="selected" : "");%>
        $('#editRow').modal({backdrop: true});
    });
}
function updateModal(id){
    debugger;
    $.when( $.ajax(ajaxHorsesUrl), $.ajax(ajaxUrl + id), $.ajax(ajaxWalletsUrl + 'cash') ).done(function( r1, r2, r3 ) {
        var horses = r1[0];
        var stake = r2[0];
        var available = r3[0];
        var current = stake.stakeValue;
        var updated = current + available;
        var select = stake.horse.name;
        $('#value').html(
            '<input class="form-control" id="stakeValue" name="stakeValue" type="number" step="0.01" min="1" max="'
            + updated + '" value="' + current + '">'
        );
        $('#horse').html(
            '<select class="form-control" id="horseName" name="horseName" >' +
            /* '<option value="" disabled="disabled" selected="selected">Please select a horse</option>' +*/
            '<option value="' + horses[0] + '" selected="selected">' + horses[0] + '</option>' +
            '<option value="' + horses[1] + '">' + horses[1] + '</option>' +
            '<option value="' + horses[2] + '">' + horses[2] + '</option>' +
            '<option value="' + horses[3] + '">' + horses[3] + '</option>' +
            '<option value="' + horses[4] + '">' + horses[4] + '</option>' +
            '<option value="' + horses[5] + '">' + horses[5] + '</option>' +
            '</select>'
        );
        //<%=(horseName==select ? selected="selected" : "");%>
        $('#editRow').modal({backdrop: true});
    });
}

function checkForm() {
    var valid_stake_value = $("#stakeValue")[0].checkValidity() == true;
    var valid_horse_name = $("#horseName")[0].value != "";
    var valid = [valid_stake_value, valid_horse_name];
    var formGroup = $('.form-group.has-feedback');
    var glyphicon = formGroup.find('.form-control-feedback');

    var formValid = true;
    for (i =0; i < valid.length; i++){
        if (valid[i]) {
            $(formGroup[i]).addClass('has-success').removeClass('has-error');
            $(glyphicon[i]).addClass('glyphicon-ok').removeClass('glyphicon-remove');
        }else {
            $(formGroup[i]).addClass('has-error').removeClass('has-success');
            $(glyphicon[i]).addClass('glyphicon-remove').removeClass('glyphicon-ok');
            formValid=false;
        }
    }
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
                render: $.fn.dataTable.render.number( ' ', '.', 2)
            },
            {
                data: "editable",
                orderable: false,
                defaultContent: "",
                render: function (data, type, row) {
                    if (type == 'display'){
                        if (!data) {
                            return '<span class="glyphicon glyphicon-lock" style="color: black"/>';
                        }else {
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
                    if (type == 'display'){
                        if (!data) {
                            return '<span class="glyphicon glyphicon-lock" style="color: black"/>';
                        }else {
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
            $(row).addClass(data.wins ? 'winned' : 'loosed');
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