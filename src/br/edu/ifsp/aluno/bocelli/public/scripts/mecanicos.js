var mecanicos;

$(document).ready(function() {
    // Define mecânicos
    if(sessionStorage.getItem('mecanicos') !== null) {
        mecanicos = JSON.parse(sessionStorage.getItem('mecanicos'));
    } else {
        mecanicos = [];
    }

    // Monta tabela
    carregarTabela();
    $('#conteudoTabela').empty();

    // Eventos ao fechar modal
    $('.modal').on('hidden.bs.modal', function (event) {
        $(this).find('.btn-primary').off();
    })

    // Evento para editar
    $('#conteudoTabela').on('click', 'button[data-index_editar]', function() {
        window.location.href = `novo-mecanico.html?a=e&id=${$(this).attr('data-index_editar')}`;
    });
});

function carregarTabela() {
    for(let i = 0; i < mecanicos.length; i++) {
        let lineItem = $(`<tr data-index="${i}"></tr>`);
        lineItem.append(`<td>${mecanicos[i].cpf}</td>`);
        lineItem.append(`<td>${mecanicos[i].nome}</td>`);
        lineItem.append(`<td>R$ ${mecanicos[i].salario}</td>`);
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