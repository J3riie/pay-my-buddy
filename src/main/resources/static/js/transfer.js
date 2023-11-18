var userConnections;
$(document).ready(function() {
    console.log("on load");
    searchConnections();
})

function onOpenConnectionForm() {
    $("#modalError").hide()
    var myModal = new bootstrap.Modal(document.getElementById('connectionFormModal'), {backdrop: 'static'})
    myModal.show()
}

// handle submission
function onAddConnection() {
    console.log("onAddConnection")
    
    const email = $("#newFriend").val()
    if (validateEmail(email)) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        var connectionForm = {
            email: email
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
                } else {
                    console.log('Cannot create connection')
                    $("#connectionFailure").show()
                    hideAfterTimeout("connectionFailure")
                }
            },
            error: function(err) {
                console.error(err)
            }
        })
    } else {
        $("#modalError").show()
        hideAfterTimeout("modalError")
    }
}

function validateEmail(input) {
  var validRegex = /^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$/

  return input.match(validRegex);
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
            userConnections = res
            $("#connectionSelection").autocomplete({
                autoFocus: true,
                minLength: 2,
                source: userConnections
            });
        },
        error: function(err) {
            console.error(err)
        }
    })
}

function onSendMoney() {
    console.log("onSendMoney")
    
    const connection = $("#connectionSelection").val()
    const amount = $("#amount").val()
    
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var sendMoneyForm = {
        connection: connection,
        amount: amount
    }

    console.log(sendMoneyForm)
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/sendMoney",
        beforeSend: function(req) {
            req.setRequestHeader(header, token);
        },
        data: JSON.stringify(sendMoneyForm),
        dataType: 'json',
        success: function(res) {
            console.log(res)
            console.log('Money sent successfully')
            console.log(res.message)
            $("#connectionSelection").val('')
            $("#amount").val('')
        },
        error: function(err) {
            console.error(err)
        }
    })
}

function hideAfterTimeout(id) {
    setTimeout(() => {  $("#" + id).hide() }, 5000)
}
