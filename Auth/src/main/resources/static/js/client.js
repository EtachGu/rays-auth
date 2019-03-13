function addRedirectUri() {
    var inputElement = document.createElement("input");
    inputElement.name = "redirectUris";
    inputElement.type = "text";
    inputElement.classList.add("form-control");
    document.getElementById("redirectList").appendChild(inputElement);
}