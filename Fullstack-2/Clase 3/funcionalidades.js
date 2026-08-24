const formularioJS = document.getElementById("formulario");

formularioJS.addEventListener("submit", function(event){
    event.preventDefault();

    console.log("Boton funcionando");


    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const pais = document.getElementById("pais").value.trim();

    if (nombre === "" || apellido === "" || telefono === "" || correo === "" ||pais === "") {
        alert("Campo no puede ser vacio")
        return;
    }

    if (apellido.toLowerCase())

    /*switch () {
        case 0:
            nombre === "";
            break;
        case 1:
            break;
    
        default:
            break;
    }
    */

    console.log(nombre)
    console.log(apellido)
    console.log(telefono)
    console.log(correo)

    if (pais == "chile") {
        console.log("Nacional")
    } else {
        console.log("Extranjero")
    }


})