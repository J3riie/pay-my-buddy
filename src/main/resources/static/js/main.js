function onSendMoney() {
  var connection = $('option[selected]').val()
  var amount = $("#amount").val()
  //ajax call
}

function onOpenConnectionForm() {
    $("#modalError").hide()
    var myModal = new bootstrap.Modal(document.getElementById('connectionFormModal'), {backdrop: 'static'})
    myModal.show()
}

function onAddConnection() {
    const email = $("#newFriend").val()
    if (validateEmail(email)) {
        //ajax call
        $("#newFriend").val('')
        $("#connectionFormModal").modal('hide')
    } else {
        $("#modalError").show()
    }
    
}

function validateEmail(input) {
  var validRegex = /^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$/

  return input.match(validRegex);
}