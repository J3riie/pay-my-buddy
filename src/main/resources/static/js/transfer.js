$(document).ready(function() {
    console.log("on load");
    searchConnections();
})

function onOpenConnectionForm() {
    var myModal = new bootstrap.Modal(document.getElementById('connectionFormModal'), {backdrop: 'static'})
    myModal.show()
}

// handle submission
function onAddConnection() {
    console.log("onAddConnection")
    
    const emailOrUsername = $("#newFriend").val()
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var connectionForm = {
        emailOrUsername: emailOrUsername
    }

    console.log(connectionForm)

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
                $("#newFriend").val('')
                $("#connectionFormModal").modal('hide')
                $("#connectionSuccess").show()
                hideAfterTimeout("connectionSuccess")
                searchConnections()
            }
        },
        error: function(err) {
            if (err.responseJSON.statusCode === 409) {
                $("#errorUserAlreadyPresent").show()
                hideAfterTimeout("errorUserAlreadyPresent")
            } else {
                $("#errorUserNotExisting").show()
                hideAfterTimeout("errorUserNotExisting")
            }
            console.error(err)
        }
    })
}

function searchConnections() {
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/connections",
        beforeSend: function(req) {
            req.setRequestHeader(header, token);
        },
        success: function(res) {
            console.log(res)
            $("#friend").autocomplete({
                autoFocus: true,
                minLength: 2,
                source: res
            });
        },
        error: function(err) {
            console.error(err)
        }
    })
}

function onSendMoney() {
    console.log("onSendMoney")
    
    const friend = $("#friend").val()
    const amount = $("#amount").val()
    const description = $("#description").val()
    
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var sendMoneyForm = {
        friend: friend,
        amount: amount,
        description: description
    }

    console.log(sendMoneyForm)
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/transfer",
        beforeSend: function(req) {
            req.setRequestHeader(header, token);
        },
        data: JSON.stringify(sendMoneyForm),
        dataType: 'json',
        success: function(res) {
            console.log(res)
            console.log('Money sent successfully')
            console.log(res.message)
            refreshTransfer()
        },
        error: function(err) {
            $("#cannotSend").show()
            hideAfterTimeout("cannotSend")
            console.error(err)
        }
    })
}

function hideAfterTimeout(id) {
    setTimeout(() => {  $("#" + id).hide() }, 5000)
}

function refreshTransfer() {
    location.reload()
}
