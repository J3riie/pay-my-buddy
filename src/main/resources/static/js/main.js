function onOpenConnectionForm() {
    $("#modalError").hide()
    var myModal = new bootstrap.Modal(document.getElementById('connectionFormModal'), {backdrop: 'static'})
    myModal.show()
}

// handle submission
function onAddConnection(e) {
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
                    } else {
                        console.log('Cannot create connection')
                    }
                },
                error: function(err) {
                    console.error(err)
                }
            })
    } else {
        $("#modalError").show()
    }

}

function validateEmail(input) {
  var validRegex = /^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$/

  return input.match(validRegex);
}


function onSendMoney() {
  var connection = $('option[selected]').val()
  var amount = $("#amount").val()
  //ajax call
}
