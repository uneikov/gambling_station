var form;

function makeEditable() {
    form = $('#detailsForm');

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

function add() {
    debugger;
    var form_title= this.form[0].title;
    $('#modalTitle').html(add_title);
    form.find(":input").val("");
    //$("#id").val("");
    if (form_title=='stake'){
        debugger;
        addModal();
    }else {
        debugger;
        $('#editRow').modal();
    }
}

/*
function wallet() {
    debugger;
    $('#walletTitle').html("Wallet");
    form.find(":input").val("");
    $('#walletForm').modal();
}

*/

// Не работает для horses ?
function updateRow(id) {
    debugger;
    var form_title= this.form[0].title;
    $('#modalTitle').html(edit_title);
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            console.log(key, value);
            form.find("input[name='" + key + "']").val(value);
        });
    });
    if (form_title=='stake'){
        debugger;
        updateModal(id);
    }else {
        debugger;
        $('#editRow').modal();
    }
}

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
    debugger;
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
    debugger;
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

