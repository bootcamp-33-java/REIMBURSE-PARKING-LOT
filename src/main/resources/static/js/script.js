/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    getContent();
    $('#selectAsset').change(getContent);

    function getContent() {
        var url = '/assetDetail?id=' + $('#selectAsset').val();
        $('#replace_div').load(url);
    }
})

$(function () {
    $("#dateIn").datetimepicker();
});

function addReport(requesterid,assetid,reportid){
    document.getElementById("requesterid").value = requesterid;
    document.getElementById("assetid").value = assetid;
    document.getElementById("reportid").value = reportid;
}

$(document).ready(function () {
    var t = $('#employeeTable').DataTable({
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }],
        "order": [[1, 'asc']]
    });

    t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();
});

function myFunction(id, name, dateIn, penaltyCost, asset) {
    if (id != '1') {
        document.getElementById("id").readOnly = true;
        document.getElementById("id").value = id;
        document.getElementById("name").value = name;
        document.getElementById("dateIn").value = dateIn;
        document.getElementById("penaltyCost").value = penaltyCost;
        document.getElementById("asset").value = asset;
    } else {
        document.getElementById("id").value = id;
    }

}

$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });

});

function sweetAlertDelete(id) {
    swal({
        title: 'Warning!',
        text: "Yakin ingin menghapus data?",
        icon: 'warning',
        buttons: true,
        dangerMode: true
    }).then((willDelete) => {
        if (willDelete) {
            window.location.href = "/delete?id=" + id;
        } else {
            swal("Cancel Delete!");
        }
    });
}

function sweetAlertInsert(status) {
//                alert(status);
    if (status == null) {

    } else if (status.includes("Berhasil")) {
        swal({
            icon: 'success',
            type: 'success',
            title: 'Good Job!',
            text: status,
        });
    } else if (status.includes("Gagal")) {
        swal({
            icon: 'error',
            type: 'error',
            title: 'Oopss...',
            text: status,
        });
    }
}

function getConfirm(id, request) {
    document.getElementById("confirm").value = id;
    document.getElementById("request").value = request;
}