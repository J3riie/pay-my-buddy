// show modal
function onAdd() {
    console.log('onAdd')
    var myModal = new bootstrap.Modal(document.getElementById('connectionFormModal'), {backdrop: 'static'})
    myModal.show()
}

// handle submission
function onAddConnection(e) {
    console.log("onAddConnection")
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var connectionForm = {
        email: $("#email").val()
    }

    console.log(connectionForm)
    console.log('header name ' + header + ' value ' + token)

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/connections",
        beforeSend: function(req) {
            req.setRequestHeader(header, token);
        },
        data: JSON.stringify(connectionForm),
        dataType: 'json',
        success: function(res) {
            console.log(res)
            if (res.statusCode === 201) {
                console.log('Connection created successfully')
                console.log(res.message)
            } else {
                console.log('Cannot create connection')
            }
        },
        error: function(err) {
            console.error(err)
        }
    })
}
