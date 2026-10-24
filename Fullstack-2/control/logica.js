
let deuda = 12000;
let saldoAFavor = 0;
const abono = 1000;

const btnAbonar = document.getElementById("btnAbonar");

btnAbonar.addEventListener("click", function () {

    if (deuda > 0) {
        deuda -= abono;
    }else{
        saldoAFavor += abono;
        deuda = 0;
    }

    let estado;

    if (deuda === 12000) {
        estado = "Pendiente";
    } else if (deuda > 0) {
        estado = "Abonado";
    } else if (saldoAFavor > 0) {
        estado = "Saldo a favor";
    } else {
        estado = "Pagado";
    }

    console.log("Estado:", estado);
    console.log("Deuda restante: $" + deuda);
    console.log("Saldo a favor: $" + saldoAFavor);
    console.log("----------------------");
});
