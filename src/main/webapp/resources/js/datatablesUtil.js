var form;
var selected;
/*var i18n = [];
var edit_title;


$(function i18nInit() {
    edit_title ='<fmt:message key="stake.edit"/>';
    i18n = [ "common.update","common.delete","common.deleted","common.saved","common.enabled","common.disabled","common.failed" ];
    for (i = 0; i < i18n.length; i++){
        i18n[i] = '<fmt:message key="' + i18n[i] + '"/>';
    }
    debugger;
});*/

function makeEditable() {
    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    debugger;
    $('#modalTitle').html(add_title);
    form.find(":input").val("");
    //$("#id").val("");
    //$('#editRow').modal();
    addModal();
}

function updateRow(id) {
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            console.log(key, value);
            form.find("input[name='" + key + "']").val(value);
        });
        updateModal(id);
    });
}

function updateModal(id){
    $.when( $.ajax(ajaxHorsesUrl), $.ajax(ajaxUrl + id) ).done(function( r1, r2 ) {
        var horse;
        var horses = r1[0];
        var stake = r2[0];
        var current = stake.stakeValue;
        var available = stake.user.wallet.cash;
        var select = stake.horse.name;
        debugger;
        $('#modalTitle').html(edit_title);
        $('#value').html(
            '<input class="form-control" id="stakeValue" name="stakeValue" type="number" step="0.01" min="0" max="' + available + '" value="' + current + '">'
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
            '<option value="' + horses[6] + '">' + horses[6] + '</option>' +
            '<option value="' + horses[7] + '">' + horses[7] + '</option>' +
            '<option value="' + horses[8] + '">' + horses[8] + '</option>' +
            '<option value="' + horses[9] + '">' + horses[9] + '</option>' +
            '</select>'
        );
        //<%=(horseName==select ? selected="selected" : "");%>
        $('#editRow').modal({backdrop: true});
    });
}

/*
$("#editRow").on('show.bs.modal', function () {
    alert('The modal is about to be shown.');
});
*/

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('common.deleted');
        }
    });
}

function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
    $.ajax({
        url: ajaxUrl + id,
        type: 'POST',
        data: 'enabled=' + enabled,
        success: function () {
            chkbox.closest('tr').fadeTo(300, enabled ? 1 : 0.3);
            successNoty(enabled ? 'common.enabled' : 'common.disabled');
        }
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function save() {
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('common.saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    noty({
        text: i18n[key],
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: i18n['common.failed'] + ': ' + jqXHR.statusText + "<br>" + jqXHR.responseJSON,
        type: 'error',
        layout: 'bottomRight'
    });
}

function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-primary" onclick="updateRow(' + row.id + ');">'+i18n['common.update']+'</a>';
    }
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ');">'+i18n['common.delete']+'</a>';
    }
}
function addModal(id){
    $.when( $.ajax(ajaxHorsesUrl), $.ajax(restUserUrl) ).done(function( r1, r2 ) {
        var horse;
        var horses = r1[0];
        var user = r2[0];
        var current = 0.0;
        var available = user.wallet.cash;
        debugger;
        $('#modalTitle').html(edit_title);
        $('#value').html(
            '<input class="form-control" id="stakeValue" name="stakeValue" type="number" step="0.01" min="0" max="' + available + '" value="' + available + '">'
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
            '<option value="' + horses[6] + '">' + horses[6] + '</option>' +
            '<option value="' + horses[7] + '">' + horses[7] + '</option>' +
            '<option value="' + horses[8] + '">' + horses[8] + '</option>' +
            '<option value="' + horses[9] + '">' + horses[9] + '</option>' +
            '</select>'
        );
        //<%=(horseName==select ? selected="selected" : "");%>
        $('#editRow').modal({backdrop: true});
    });
}