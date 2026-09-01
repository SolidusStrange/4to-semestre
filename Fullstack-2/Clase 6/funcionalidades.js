const formularioJS = document.getElementById("formulario");

formularioJS.addEventListener("submit", function(event){
    event.preventDefault();

    console.log("Boton funcionando");


    const nombre = document.getElementById("nombre").value.trim();
    const apellido = document.getElementById("apellido").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const pais = document.getElementById("pais").value.trim();

    // Ver el resultado booleano de la funcion

    //if(validarInputVacio(nombre, apellido, telefono, correo, pais)){
    //    console.log(validarInputVacio(nombre, apellido, telefono, correo, pais))
    //    return 
    //}

    // Validacion nombre
    validarCantidadCaracteres(nombre, apellido);
    validarInputVacio(nombre, apellido, telefono, correo, pais);

    // Creamos el arreglo con esas variables
    const usuario = {
        nombre,
        apellido,
        telefono,
        correo,
        pais,
    }

    // Mostramos toda la informacion en la consola
    console.log(nombre)
    console.log(apellido)
    console.log(telefono)
    console.log(correo)
    console.log(pais)

    console.log("El arreglo es: ", usuario)

    // Lo guardamos en el LocalStorage con el nombre usuarioLocalStorage y lo transformamos a JSON
    localStorage.setItem("usuarioLocalStorage", JSON.stringify(usuario))

    // Abrimos la ventana tabla
    window.location = "tabla.html"

    console.log("Nombre tiene: " + nombre.length + " caracteres")
})

// Validaciones

function validarInputVacio(nombre, apellido, telefono, correo, pais){
    if (nombre === "" || apellido === "" || telefono === "" || correo === "" || pais === "") { 
        alert("Campo no puede ser vacio")
        return true;

    }else{
        return false;
    }
}

function validarCantidadCaracteres(nombre, apellido){
    if (nombre.length <= 3 || apellido.length <= 3){
        alert("Nombre debe tener más de 3 caracteres")
        return;
    }
}
