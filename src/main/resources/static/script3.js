$(function()
{
    if(self.bFormRequestServicePresent == true)
        alert('На странице уже есть форма заявки. Возможно, у форм одинаковые id.');
    self.bFormRequestServicePresent = true;
    $rq_form = $('#form_request_service');
    self.strTransportName = self.strTransportName || 'ajax_file_transport';
    if(!$('body').find('iframe[name='+self.strTransportName+']').length)
    {
        $('<iframe/>', {
            src: 'about:blank',
            name: self.strTransportName,
            width: 1,
            height: 1
        }).appendTo('body').hide();
    }
    $rq_form
        .attr('target', self.strTransportName)
        .append('<input type="hidden" name="ajax" value="true">');

    $rq_form.submit(function() {
        if(self.bIsServiceFormSending)
            return false;
        self.bIsServiceFormSending = true;
        $('#ajax_loader').show();
        var $form = $(this).serializeArray();
        $rq_form.find('.form_errorbox').html('');
        $rq_form.find('input[name="request_type"]').remove();
    });
});

function __ajaxFileTransportCallbackService(answ)
{
    answ = answ || {};
    self.bIsServiceFormSending = false;
    $('#ajax_loader').hide();
    $rq_form = $('#form_request_service');
    $errorBox = $rq_form.find('.form_errorbox');
    switch(answ.status)
    {
        case 'ok':
            $errorBox.html('');
            $rq_form.find('div.jq-file.changed').remove();
            $rq_form.get(0).reset();
            self.countfile = 0;
            var $file_downloaded = $rq_form.find('.file_loaded');
            $file_downloaded.text(plural(self.countfile, ['Загружен','Загружено']));
            $rq_form.find('.filecount').text(self.countfile);
            var $file_count = $rq_form.find('.file_count');
            $file_count.text(plural(self.countfile, ['файл','файла','файлов']));
            $('.overlay, .popup').fadeOut();
            $('.overlay, .done').fadeIn();
            popup_position();
            break;
        default:
            $errorBox.html(answ.msg);
    }
}