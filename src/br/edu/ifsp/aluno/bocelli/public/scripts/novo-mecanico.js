const prototipoTelefone = `<div class="form-group">
                                <label>Telefone Extra</label>
                                <input type="text" class="form-control" data-role="telefoneExtra" name="telefone" pattern="\\([0-9]{2}\\) [0-9]{4}-[0-9]{4}" required>
                                <div class="invalid-feedback" data-for="telefone">
                                    Telefone extra inválido!
                                </div>
                                <button data-role="removeTelefone" type="button" class="btn btn-sm btn-danger">-</button>
                            </div>`;

const prototipoEmail = `<div class="form-group">
                            <label>E-mail Extra</label>
                            <input type="email" class="form-control" data-role="emailExtra" name="email" required>
                            <div class="invalid-feedback" data-for="email">
                                E-mail extra inválido!
                            </div>
                            <button data-role="removeEmail" type="button" class="btn btn-sm btn-danger">-</button>
                        </div>`;

var mecanicos;

var operacao = null;

$(document).ready(function() {
    // Pega lista de mecânicos
    if(sessionStorage.getItem('mecanicos') !== null) {
        mecanicos = JSON.parse(sessionStorage.getItem('mecanicos'));
    } else {
        mecanicos = [];
    }

    // Vê se a operação é de edição e modifica interface
    const url_string = window.location.href;
    const url_obj = new URL(url_string);
    if(url_obj.searchParams.get('a') == 'e') {
        let id = parseInt(url_obj.searchParams.get('id'))
        if(!isNaN(id)) {
            operacao = id;
            $('#titulo').text('Editar Mecânico');
            if(mecanicos[id] !== undefined) preencheCampos(mecanicos[id]);
        }
    }

    // Evento que insere telefone extra
    $('#novoTelefone').click(function() {
        $('#telefonesExtras').append(prototipoTelefone);
    });

    // Evento que remove telefone extra
    $('#telefonesExtras').on('click', 'button[data-role="removeTelefone"]', function() {
        $(this).parent().remove();
    });

    // Evento que insere email extra
    $('#novoEmail').click(function() {
        $('#emailsExtras').append(prototipoEmail);
    });

    // Evento que remove email extra
    $('#emailsExtras').on('click', 'button[data-role="removeEmail"]', function() {
        $(this).parent().remove();
    });

    // Envia form
    $('#sendForm').click(function() {
        // Faz verificação
        let formElement = document.getElementById('meuForm');
        formElement.classList.add('was-validated');
        if(!formElement.checkValidity()) return;

        // Verifica se há repetidos de acordo com a operação
        if(operacao === null) {
            if(quantosExistem($('#cpf').val()) === 0) {
                insereNovo();
                window.location.href = "mecanicos.html";
            } else {
                $('#avisoErro').text('CPF já cadastrado!');
                return;
            }
        } else {
            if(quantosExistem($('#cpf').val()) === 1) {
                mecanicos.splice(operacao, 1);
                insereNovo();
                window.location.href = "mecanicos.html";
            } else {
                $('#avisoErro').text('CPF já cadastrado!');
                return;
            }
        }
    });
});

function preencheCampos(mecanico) {
    $('#cpf').val(mecanico.cpf);
    $('#nome').val(mecanico.nome);
    $('#nascimento').val(mecanico.nascimento);

    if(mecanico.sexo === 'M') {
        $('#masculino').prop('checked', true);
    } else {
        $('#feminino').prop('checked', true);
    }

    $('#salario').val(mecanico.salario);

    // Telefones
    let telefonesLista = mecanico.telefones || [];
    for(let i = 0; i < telefonesLista.length; i++) {
        if(i === 0) {
            $('input[name="telefone"]').eq(0).val(telefonesLista[0]);
        } else {
            let newElement = $(prototipoTelefone);
            newElement.find('input').val(telefonesLista[i])
            $('#telefonesExtras').append(newElement);
        }
    }

    // E-mails
    let emailsLista = mecanico.emails || [];
    for(let i = 0; i < emailsLista.length; i++) {
        if(i === 0) {
            $('input[name="email"]').eq(0).val(emailsLista[0]);
        } else {
            let newElement = $(prototipoEmail);
            newElement.find('input').val(emailsLista[i])
            $('#emailsExtras').append(newElement);
        }
    }
}

function quantosExistem(cpf) {
    let iguais = mecanicos.filter(mecanico => mecanico.cpf === cpf);

    return iguais.length;
}

function insereNovo() {
    let novoMembro = {};

    novoMembro['cpf'] = $('#cpf').val();
    novoMembro['nome'] = $('#nome').val();
    novoMembro['nascimento'] = $('#nascimento').val();
    novoMembro['sexo'] = $('input[name="sexo"]:selected').val();
    novoMembro['salario'] = $('#salario').val();

    // Telefones
    novoMembro['telefones'] = []
    let telefoneLista = $('input[name="telefone"]');

    telefoneLista.each(function(){
        if($(this).val() != '') novoMembro['telefones'].push($(this).val());
    });

    // E-mails
    novoMembro['emails'] = []
    let emailsLista = $('input[name="email"]');

    emailsLista.each(function(){
        if($(this).val() != '') novoMembro['emails'].push($(this).val());
    });

    mecanicos.push(novoMembro);
    sessionStorage.setItem('mecanicos', JSON.stringify(mecanicos));
}