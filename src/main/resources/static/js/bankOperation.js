function onOpenBankTransactionForm() {
    var myModal = new bootstrap.Modal(document.getElementById('bankTransactionFormModal'), {backdrop: 'static'})
    myModal.show()
}

// handle submission
function onNewBankTransaction() {
    console.log("onNewBankTransaction")
    
    const type = $("input[name=operationType]:checked").val();
    const amount = $("#amount").val()
    const description = $("#description").val()
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var bankOperationForm = {
        type: type,
        amount: amount,
        description: description
    }

    console.log(bankOperationForm)

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/profile",
        beforeSend: function(req) {
            req.setRequestHeader(header, token);
        },
        data: JSON.stringify(bankOperationForm),
        dataType: 'json',
        success: function(res) {
            console.log(res)
            if (res.statusCode === 202) {
                console.log('Transaction success')
                console.log(res.message)
                $("#amount").val('')
                $("#description").val('')
                $("#bankTransactionFormModal").modal('hide')
                $("#transactionSuccess").show()
                hideAfterTimeout("transactionSuccess")
            }
        },
        error: function(err) {
            $("#errorInvalidAmount").show()
            hideAfterTimeout("errorInvalidAmount")
            console.error(err)
        }
    })
}

function hideAfterTimeout(id) {
    setTimeout(() => {  $("#" + id).hide() }, 5000)
}