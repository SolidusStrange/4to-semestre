const formularioJS = document.getElementById("formulario");

formularioJS.addEventListener("submit", function(event){
    event.preventDefault();

    console.log("Boton funcionando");


    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const pais = document.getElementById("pais").value.trim();

    // Verificar si esta vacio
    if (nombre === "" ||
        apellido === "" || 
        telefono === "" || 
        correo === "" ||
        pais === "") {
        
            alert("Campo no puede ser vacio")
            return;
    }

    // Si el pais es chile le agrega +569
    if(pais === "chile")
        {"+569" + pais
        }

    // Evalua que no pueda poner el mismo nombre en apellido o viceversa
    if (nombre.toLowerCase() === apellido.toLowerCase()
    ){
        alert("No puede ser igual")
    }

    // Creamos el arreglo con esas variables
    const usuario = {
        nombre: nombre,
        apellido: apellido,
        telefono: telefono,
        correo: correo,
        pais: pais,
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

})