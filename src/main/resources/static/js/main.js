function onAddConnection() {
    console.log('onAddConnection...')
    var myModal = new bootstrap.Modal(document.getElementById('connectionFormModal'), {backdrop: 'static'});
    myModal.show();
}

function showAlert() {
    alert("HELLO")
}