function validaDocumento(valor) {
    doc = limpacampo(valor);
    if (doc.length == 11) {
        return validaCPF(doc);
    } else {
        return validaCNPJ(doc);
    }
}

function validaCNPJ(valor) {
    CNPJ = valor;
    erro = new String;
    var nonNumbers = /\D/;
    if (nonNumbers.test(CNPJ))
        erro += "A verificação de CNPJ/CPF suporta apenas números! \n\n";
    var a = [];
    var b = new Number;
    var c = [ 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 ];
    for (i = 0; i < 12; i++) {
        a[i] = CNPJ.charAt(i);
        b += a[i] * c[i + 1];
    }
    if ((x = b % 11) < 2) {
        a[12] = 0
    } else {
        a[12] = 11 - x
    }
    b = 0;
    for (y = 0; y < 13; y++) {
        b += (a[y] * c[y]);
    }
    if ((x = b % 11) < 2) {
        a[13] = 0;
    } else {
        a[13] = 11 - x;
    }
    if ((CNPJ.charAt(12) != a[12]) || (CNPJ.charAt(13) != a[13])) {
        erro += "CNPJ/CPF inválido! Informe outro.";
    }
    if (erro.length > 0) {
        alert(erro);
        return false;
    }
    return true;
}

function validaCPF(cpf) {
    var i;
    s = cpf;
    var c = s.substr(0, 9);
    var dv = s.substr(9, 2);
    var d1 = 0;
    for (i = 0; i < 9; i++) {
        d1 += c.charAt(i) * (10 - i);
    }
    if (d1 == 0) {
        alert("CNPJ/CPF Invalido! Informe outro.");
        return false;
    }
    d1 = 11 - (d1 % 11);
    if (d1 > 9)
        d1 = 0;
    if (dv.charAt(0) != d1) {
        alert("CNPJ/CPF Invalido! Informe outro.");
        return false;
    }
    d1 *= 2;
    for (i = 0; i < 9; i++) {
        d1 += c.charAt(i) * (11 - i);
    }
    d1 = 11 - (d1 % 11);
    if (d1 > 9)
        d1 = 0;
    if (dv.charAt(1) != d1) {
        alert("CNPJ/CPF Invalido! Informe outro.");
        return false;
    }
    return true;
}

function formataCep(cep) {
    if (cep.length == 8) {
        aux = cep.substring(0, 2) + ".";
        aux = aux + cep.substring(2, 5) + "-";
        aux = aux + cep.substring(5, 8);
        return aux;
    }
}

function limpacampo(S) { // Deixa so os digitos no numero
    var Digitos = "0123456789";
    var temp = "";
    var digito = "";
    for (var i = 0; i < S.length; i++) {
        digito = S.charAt(i);
        if (Digitos.indexOf(digito) >= 0) {
            temp = temp + digito;
        }
    }
    return temp
}

function formatadocumento(documento) {
    var doc = documento;

    if (doc.length == 11) {
        aux = doc.substring(0, 3);
        aux = aux + "." + doc.substring(3, 6);
        aux = aux + "." + doc.substring(6, 9);
        aux = aux + "-" + doc.substring(9, 11);
        return aux;
    } else if (doc.length == 14) {
        aux = doc.substring(0, 2);
        aux = aux + "." + doc.substring(2, 5);
        aux = aux + "." + doc.substring(5, 8);
        aux = aux + "/" + doc.substring(8, 12);
        aux = aux + "-" + doc.substring(12, 14);
        return aux;
    } else {
        alert('CNPJ/CPF invalido! Informe outro.');
        return "erro";
    }
}

function formataTelefone(fone) {
    if (fone.length > 0) {
        fone = limpacampo(fone)
        if (fone.length == 10) {
            var aux = "(" + fone.substring(0, 2) + ") ";
            aux = aux + fone.substring(2, 6) + "-";
            aux = aux + fone.substring(6, 10);
            return aux;
        } else if (fone.length == 11) {
            var aux = "(" + fone.substring(0, 2) + ") ";
            aux = aux + fone.substring(2, 7) + "-";
            aux = aux + fone.substring(7, 11);
            return aux;
        } else {
            alert("Telefone inválido. O telefone precisa ter 10 ou 12 números");
            return "";
        }
    }
}

function validarData(campo) {
    var expReg = /^(([0-2]\d|[3][0-1])\/([0]\d|[1][0-2])\/[1-2][0-9]\d{2})$/;
    var msgErro = 'Formato invalido de data.';
    if ((campo.value.match(expReg)) && (campo.value != '')) {
        var dia = campo.value.substring(0, 2);
        var mes = campo.value.substring(3, 5);
        var ano = campo.value.substring(6, 10);
        if (mes == 4 || mes == 6 || mes == 9 || mes == 11 && dia > 30) {
            alert("Dia incorreto !!! O mes especificado contem no maximo 30 dias.");
            return false;
        } else {
            if (ano % 4 != 0 && mes == 2 && dia > 28) {
                alert("Data incorreta!! O mes especificado contem no maximo 28 dias.");
                return false;
            } else {
                if (ano % 4 == 0 && mes == 2 && dia > 29) {
                    alert("Data incorreta!! O mes especificado contem no maximo 29 dias.");
                    return false;
                } else {
                    return true;
                }
            }
        }
    } else {
        alert(msgErro);
        campo.focus();
        return false;

    }

}
