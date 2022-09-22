if (typeof jQuery !== 'undefined') {
    (function ($) {
        $('#spinner').ajaxStart(function () {
            $(this).fadeIn();
        }).ajaxStop(function () {
                $(this).fadeOut();
            });
    })(jQuery);
}

function gerarSenha(campo) {
    var senha = "Senha gerada";
    $.ajax({
        type: 'get',
        encoding: 'utf-8',
        url: '/advnet/admin/gerarSenha?data='+new Date(),
        dataType: 'html',
        success: function(data){
            $("#"+campo).val(data);
        }
    });
};