const formularioLogin = document.getElementById("formLogin");

const usuarioBDD = {
    nombre: "juanito",
    password: "123456",
}


formularioLogin.addEventListener("submit", function(event){
    event.preventDefault();

    const usuario = document.getElementById("usuario").value.trim();
    const password = document.getElementById("password").value.trim();

    // Array
    const usuarios = {
        usuario,
        password,
    }

    validarPasswordTipo(password);

    validarUsuarioVacio(usuario);
    validarPasswordVacio(password);

    if(validarUsuarioBDD(usuario, password)){
        alert("Conexión exitosa!")
    }else{
        alert("Usuario o contraseña incorrecta.")
    }
    }
)


// Validaciones
function validarUsuarioVacio(usuario){
    if(usuario == ""){
        alert("Usuario no puede estar vacio")
        return true;
    }
}


function validarPasswordVacio(password){
    if(password == ""){
        alert("Contraseña no puede estar vacia")
        return true;
    }
}

function validarUsuarioBDD(usuario, password){
    if (usuario === usuarioBDD.nombre &&
        password === usuarioBDD.password){
            return true;
    } else{
        return false;
    }
}

function validarPasswordTipo(password){
    if (password.length <= 8) {
        alert("La contraseña debe tener al menos 8 caracteres.")
        return true;
    }
    
    // for(const char of password){
    //    console.log(password);
}





