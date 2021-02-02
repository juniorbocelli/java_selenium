const prototipoAcessorio = `<div class="form-group">
                                <label>Acessório Estra</label>
                                <input type="text" class="form-control" data-role="acessorioExtra" name="acessorio">
                                <button data-role="removeAcessorio" type="button" class="btn btn-sm btn-danger">-</button>
                            </div>`;

var veiculos;

var operacao = null;

$(document).ready(function() {
    // Estados e Cidades
    estadosCidades();

    // Marcas e Modelos
    marcasModelos();

    // Pega lista de veículos
    if(sessionStorage.getItem('veiculos') !== null) {
        veiculos = JSON.parse(sessionStorage.getItem('veiculos'));
    } else {
        veiculos = [];
    }

    // Vê se a operação é de edição e modifica interface
    const url_string = window.location.href;
    const url_obj = new URL(url_string);
    if(url_obj.searchParams.get('a') == 'e') {
        let id = parseInt(url_obj.searchParams.get('id'))
        if(!isNaN(id)) {
            operacao = id;
            $('#titulo').text('Editar Veículo');
            if(veiculos[id] !== undefined) preencheCampos(veiculos[id]);
        }
    }

    // Evento que insere email extra
    $('#novoAcessorio').click(function() {
        $('#acessoriosExtras').append(prototipoAcessorio);
    });

    // Evento que remove email extra
    $('#acessoriosExtras').on('click', 'button[data-role="removeAcessorio"]', function() {
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
            if(quantosExistem($('#placaLetras').val(), $('#placaNumeros').val(), $('#placaEstado').val(), $('#placaCidade').val()) === 0) {
                insereNovo();
                window.location.href = "veiculos.html";
            } else {
                $('#avisoErro').text('Placa já cadastrada!');
                return;
            }
        } else {
            if(quantosExistem($('#placaLetras').val(), $('#placaNumeros').val(), $('#placaEstado').val(), $('#placaCidade').val()) === 1) {
                veiculos.splice(operacao, 1);
                insereNovo();
                window.location.href = "veiculos.html";
            } else {
                $('#avisoErro').text('Placa já cadastrada!');
                return;
            }
        }
    });
});

function preencheCampos(veiculo) {
    $('#placaLetras').val(veiculo.placaLetras);
    $('#placaNumeros').val(veiculo.placaNumeros);
    $('#placaEstado').val(veiculo.placaEstado);
    $('#placaEstado').trigger('change');
    $('#placaCidade').val(veiculo.placaCidade);
    $('#tipo').val(veiculo.tipo);
    $('#marca').val(veiculo.marca);
    $('#marca').trigger('change');
    $('#modelo').val(veiculo.modelo);
    $('#ano').val(veiculo.ano);
    $('#portas').val(veiculo.portas);
    $('#lugares').val(veiculo.lugares);
    $('#combustivel').val(veiculo.combustivel);
    $('#cor').val(veiculo.cor);

    // Acessórios
    let acessoriosLista = veiculo.acessorios || [];
    for(let i = 0; i < acessoriosLista.length; i++) {
        if(i === 0) {
            $('input[name="acessorio"]').eq(0).val(acessoriosLista[0]);
        } else {
            let newElement = $(prototipoAcessorio);
            newElement.find('input').val(acessoriosLista[i])
            $('#acessoriosExtras').append(newElement);
        }
    }
}

function quantosExistem(placaLetras, placaNumeros, placaEstado, placaCidade) {
    let iguais = veiculos.filter(veiculo => {
        if(veiculo.placaLetras === placaLetras &&
            veiculo.placaNumeros === placaNumeros &&
            veiculo.placaEstado === placaEstado &&
            veiculo.placaCidade === placaCidade) return veiculo;
        
    });
    
    return iguais.length;
}

function insereNovo() {
    let novoMembro = {};

    novoMembro['placaLetras'] = $('#placaLetras').val().toUpperCase();
    novoMembro['placaNumeros'] = $('#placaNumeros').val();
    novoMembro['placaEstado'] = $('#placaEstado').val();
    novoMembro['placaCidade'] = $('#placaCidade').val();
    novoMembro['tipo'] = $('#tipo').val();
    novoMembro['marca'] = $('#marca').val();
    novoMembro['modelo'] = $('#modelo').val();
    novoMembro['ano'] = $('#ano').val();
    novoMembro['portas'] = $('#portas').val();
    novoMembro['lugares'] = $('#lugares').val();
    novoMembro['combustivel'] = $('#combustivel').val();
    novoMembro['cor'] = $('#cor').val();

    // Telefones
    novoMembro['acessorios'] = []
    let acessoriosLista = $('input[name="acessorio"]');

    acessoriosLista.each(function(){
        if($(this).val() != '') novoMembro['acessorios'].push($(this).val());
    });

    veiculos.push(novoMembro);
    sessionStorage.setItem('veiculos', JSON.stringify(veiculos));
}

function estadosCidades() {
    const statesAndCities = {
        SP: ['SAO CARLOS', 'IBATE', 'DESCALVADO'],
        RJ: ['PARATI', 'VALENCA', 'ITABORAI']
    }

    Object.keys(statesAndCities).forEach(element => {
        $('#placaEstado').append(`<option value="${element}">${element}</option>`);
    });

    $('#placaEstado').change(function() {
        $('#placaCidade').empty();
        $('#placaCidade').append(`<option value="">Selecione um estado</option>`);

        let element = $(this);
        if(element.val() != '') {
            let listOptions = statesAndCities[element.val()];
            for(let i = 0; i < listOptions.length; i++) {
                $('#placaCidade').append(`<option value="${listOptions[i]}">${listOptions[i]}</option>`);
            }
        }
    });
}

function marcasModelos() {
    const marcasModelos = {
        FIAT: ['UNO', 'PALIO', 'SIENA'],
        CHEVROLET: ['CRUZE', 'JOY', 'ONIX'],
        PEUGEOT: ['106', '207', '208']
    }

    Object.keys(marcasModelos).forEach(element => {
        $('#marca').append(`<option value="${element}">${element}</option>`);
    });

    $('#marca').change(function() {
        $('#modelo').empty();
        $('#modelo').append(`<option value="">Selecione um modelo</option>`);

        let element = $(this);
        if(element.val() != '') {
            let listOptions = marcasModelos[element.val()];
            for(let i = 0; i < listOptions.length; i++) {
                $('#modelo').append(`<option value="${listOptions[i]}">${listOptions[i]}</option>`);
            }
        }
    });
}