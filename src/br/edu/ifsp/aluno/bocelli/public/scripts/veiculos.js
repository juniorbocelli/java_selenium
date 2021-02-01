var veiculos;

$(document).ready(function() {
    // Define mecânicos
    if(sessionStorage.getItem('veiculos') !== null) {
        veiculos = JSON.parse(sessionStorage.getItem('veiculos'));
    } else {
        veiculos = [];
    }

    // Monta tabela
    carregarTabela();

    // Eventos ao fechar modal
    $('.modal').on('hidden.bs.modal', function (event) {
        $(this).find('.btn-primary').off();
    })

    // Evento para editar
    $('#conteudoTabela').on('click', 'button[data-index_editar]', function() {
        window.location.href = `novo-veiculo.html?a=e&id=${$(this).attr('data-index_editar')}`;
    });

    // Evento para excluir
    $('#conteudoTabela').on('click', 'button[data-index_excluir]', function() {
        mostrarModal('Atenção', '<p>Deseja excluir o conteúdo?</p>', $(this).attr('data-index_editar'));
    });
});

function carregarTabela() {
    $('#conteudoTabela').empty();
    for(let i = 0; i < veiculos.length; i++) {
        let lineItem = $(`<tr data-index="${i}"></tr>`);
        lineItem.append(`<td>${veiculos[i].placaLetras} ${veiculos[i].placaNumeros}</td>`);
        lineItem.append(`<td>${veiculos[i].tipo}</td>`);
        lineItem.append(`<td>${veiculos[i].modelo}</td>`);
        lineItem.append(`<td><button type="button" class="btn btn-sm btn-danger" data-index_excluir="${i}">Excluir</button><button type="button" class="btn btn-sm btn-info ml-1" data-index_editar="${i}">Editar</button></td>`);

        $('#conteudoTabela').append(lineItem);
    }
}

function mostrarModal(titulo, conteudo, index) {
    let modal = $('.modal');
    let tituloM = modal.find('.modal-title');
    let conteudoM = modal.find('.modal-body');
    let botaoSalvar = modal.find('.btn-primary');

    tituloM.text(titulo);
    conteudoM.html(conteudo);

    botaoSalvar.click(function() {
        exclui(index);
    });

    modal.modal('show');
}

function exclui(index) {
    veiculos.splice(index, 1);
    $('.modal').modal('hide');
    carregarTabela();
}