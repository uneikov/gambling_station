var form;
var ajaxRacesUrl = 'ajax/profile/races/';

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
    var form_title= this.form[0].title;
    $('#modalTitle').html(add_title);
    form.find(":input").val("");
    if (form_title=='stake'){
        addModal();
    }else {
        $('#editRow').modal();
    }
}

function updateRow(id) {
    var form_title= this.form[0].title;
    $('#modalTitle').html(edit_title);
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
    });
    if (form_title=='stake'){
        updateModal(id);
    }
    if (form_title=='horse' || form_title=='user'){
        $.get(ajaxRacesUrl + 'can', function (editable) {
            if (editable=='editable') {
                $('#editRow').modal();
            }else {
                notEditableNoty('common.not_editable');
                updateTable();
            }
        });
    }
}

function checkDelete(id) {
    var form_title= this.form[0].title;
    if (form_title=='horse' || form_title=='user'){
        $.get(ajaxRacesUrl + 'can', function (editable) {
            if (editable=='editable') {
                deleteRow(id);
            }else {
                notEditableNoty('common.not_editable');
                updateTable();
            }
        });
    }
    if (form_title=='stake'){
        deleteRow(id);
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
    var errorInfo = $.parseJSON(jqXHR.responseText);
    failedNote = noty({
        text: i18n['common.failed'] + ': ' + jqXHR.statusText + "<br>"+ errorInfo.cause + "<br>" + errorInfo.details.join("<br>"),
        type: 'error',
        layout: 'bottomRight'
    });
}

function notEditableNoty(key) {
    closeNoty();
    failedNote = noty({
        text: i18n[key],
        type: 'error',
        layout: 'bottomRight',
        timeout: true
    });
}

function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-primary" onclick="updateRow(' + row.id + ');">'+i18n['common.update']+'</a>';
    }
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-danger" onclick="checkDelete(' + row.id + ');">'+i18n['common.delete']+'</a>';
    }
}

